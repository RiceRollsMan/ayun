package com.example.ayun.controller;

import com.example.ayun.pojo.YunFile;
import com.example.ayun.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class FileController {
    @Autowired
    private FileService fileService;
    //
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    //
    @ResponseBody
    @GetMapping("/toShowFiles")
    public List<YunFile> toShowFiles(@RequestBody @RequestParam("presentPath") String presentPath){
        System.out.println(presentPath);
        List<YunFile> yunFiles = new ArrayList<>();
        yunFiles=fileService.showFiles(presentPath);
//        for(YunFile yunFile:yunFiles){
//            //yunFiles.add(yunFile);
//            System.out.println(yunFile.toString());
//        }
        System.out.println(yunFiles.size());
        return yunFiles;
    }

    @ResponseBody
    @PostMapping("/toUploadFile")
    public List<YunFile> toUpLoadFile(@RequestBody @RequestParam("uploadFile") MultipartFile uploadFile,
                                       @RequestBody @RequestParam("presentPath") String presentPath,
                                       @RequestBody @RequestParam("username") String username,
                                       HttpServletRequest req) {
        //服务器保存路径所有文件均保存在这
        System.out.println(presentPath);
        String storagePath = presentPath;

        //oldName获取到被上传文件的名字
        String oldName = uploadFile.getOriginalFilename();
        //通过oldName去获取到该文件的类型。
        String fileType = oldName.substring(oldName.lastIndexOf("."), oldName.length());

        String newName = UUID.randomUUID().toString() + fileType;

        //这个时候就利用springboot的工具类在服务器上进行存储了。
        String filePath = "";
        try {
            uploadFile.transferTo(new File(storagePath, newName));//transferto()方法，是springmvc封装的方法，把文件写入磁盘。
            filePath = storagePath + newName;//文件绝对路径
            System.out.println(filePath);
        } catch (IOException e) {
        }
        //调用service
        String userName = username;
        String fileName = oldName;
//        filePath
        String fileSize = "" + uploadFile.getSize();
//        fileType
        fileService.addFile(userName, fileName, filePath, fileSize, fileType);
        //这里会报错，因为暂时没有return值，且前面是form需要跳转哦。
        return toShowFiles(presentPath);
    }

//
        @ResponseBody
        @GetMapping("/toGetFolderPathById")
        public String getFolderPathById(@RequestBody @RequestParam("folderId") int folderId){
            return fileService.getFolderPathById(folderId)+'/';//注意这里添加‘/’哦哦哦哦哦哦哦哦哦，逻辑搞通就好啦！
        }
//
        @ResponseBody
        @GetMapping("/toMakeAFolder")
        public List<YunFile> toMakeFolder(@RequestParam("newFolderName")String newFolderName,
                                          @RequestParam("presentPath")String presentPath,
                                          @RequestParam("username") String username){/*注意，这里前端还没改哦*/
            System.out.println("xxx");

            String storagePath=presentPath;//当前位置
            String oldName=newFolderName;//文件夹的名字
            String newName= UUID.randomUUID().toString();

            File folder = new File(storagePath+newName);
            folder.mkdirs();

            //调用service
            String userName=username;
            String fileName=oldName;
            String filePath=storagePath+newName;
            fileService.makeAFolder(username,fileName,filePath);
            return toShowFiles(presentPath);
        }
        @ResponseBody
        @GetMapping("/toGetShareFile")
        public List<YunFile> getShareFile(@RequestParam("shareCode")int shareCode,/*zanshiint*/
                                          @RequestParam("presentPath")String presentPath,
                                          @RequestParam("username") String username) throws IOException {/*注意，这里前端还没改哦*/
            System.out.println("xxx");

            String storagePath = presentPath;//当前位置
            Integer id = shareCode;
            String shareFilePath = fileService.getFolderPathById(id);//nadaolujin
            List<YunFile> yunFiles = fileService.getFilesByPath(shareFilePath);// nadaole nage duixaingle

            /*nadaoqianbanbufen*/
            String filePart[] = shareFilePath.split("/");
            String fileFirstPath = "";
            for (int i = 0; i < filePart.length - 1; i++) {
                fileFirstPath = fileFirstPath + filePart[i] + "/";
            }
            System.out.println("fileFirstPath:"+fileFirstPath);

            int i = yunFiles.size();
            for (YunFile yunFile : yunFiles) {
                String userName = username;//yonghude
                String fileName = yunFile.getFileName();//beifenxiangde
                String filePath = storagePath + yunFile.getFilePath().split(fileFirstPath)[1];
//                System.out.println("yunFile.getFilePath().split(fileFirstPath)[1]"+yunFile.getFilePath().split(fileFirstPath)[1]);
                String fileSize = yunFile.getFileSize();
                String fileType = yunFile.getFileType();

                Integer isDir = yunFile.getIsDir();//nahaole
//                System.out.println("**********");
//                System.out.println("storage:"+storagePath);
//                System.out.println("filePath:"+filePath);
//                System.out.println("**********");
                fileService.addShareFile(userName, fileName, filePath, fileSize, fileType, isDir);

                if(isDir==1) {
                    File folder = new File(filePath);
                    folder.mkdirs();
                }

                if(isDir==0) {
                    //shixianwenjiankaobeile
//创建FileInputStream对象
                    FileInputStream fis = new FileInputStream(yunFile.getFilePath());
//创建FileOutputStream对象
                    FileOutputStream fos = new FileOutputStream(filePath);

                    int len = -1;   //读取的实际长度
                    byte[] buf = new byte[4];
//                每次读取4个字节
                    while ((len = fis.read(buf)) != -1) {
//                每次写入4个字节
                        fos.write(buf, 0, len);
                    }
                    fis.close();
                    fos.close();
                }
            }
            return toShowFiles(presentPath);
        }

    @RequestMapping("/deleteFile")
    @ResponseBody
    public Map<String,Object> deleteFile(@RequestBody @RequestParam("fileIds") Integer fileIds){
        HashMap<String, Object> map = new HashMap<>();

//        for (int i=0;i< fileIds.length;i++) {
            HashMap<String, Object> map1 = new HashMap<>();

            YunFile yunFile = fileService.queryFileById(fileIds);
            if(yunFile.getIsDir()==0)//bushiwenjianjia
                fileService.deleteFile(yunFile.getFilePath());
            else
                fileService.deleteFolder(yunFile.getFilePath());
        map.put("code", "200");
        map.put("message", "删除成功");
        map.put("data","");
        return map;
    }

    @RequestMapping("/renameFile")
    @ResponseBody
    public Map<String,Object> renameFile(@RequestBody @RequestParam("newFileName") String newFileName,
                                         @RequestBody @RequestParam("id") Integer id){
        HashMap<String, Object> map = new HashMap<>();

        YunFile yunFile = fileService.queryFileById(id);
        String newName="";
        if(yunFile.getIsDir()==0) {
            String fileType = yunFile.getFileType();
            newName=newFileName+fileType;
        }
        if(yunFile.getIsDir()==1){
            newName=newFileName;
        }
        fileService.renameFile(id,newName);

        map.put("code","200");
        map.put("message","修改成功");
        return map;
    }

    @RequestMapping("/findFile")
    @ResponseBody
    public List<YunFile> findFile(@RequestBody @RequestParam("presentPath") String presentPath,
                                  @RequestBody @RequestParam("fuzzyQueryFileName") String fuzzyQueryFileName) {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("fileName",fuzzyQueryFileName);
        maps.put("filePath",presentPath);
        List<YunFile> fileList=fileService.queryFilesByFileName(maps);
        return fileList;
    }

    @RequestMapping("/restoreFile")
    @ResponseBody
    public void restoreFile(@RequestBody @RequestParam("id") Integer id) {
        YunFile yunFile=fileService.queryFileByIdAll(id);
        System.out.println(yunFile);
        if(yunFile.getIsDir()==0)//bushiwenjianjia
            fileService.restoreFile(yunFile.getFilePath());
        else
            fileService.restoreFolder(yunFile.getFilePath());
    }

    @RequestMapping("/deleteForever")
    @ResponseBody
    public void deleteForever(@RequestBody @RequestParam("id") Integer id) {
        YunFile yunFile=fileService.queryFileByIdAll(id);
        fileService.deleteForever(yunFile.getFilePath());
    }
}
