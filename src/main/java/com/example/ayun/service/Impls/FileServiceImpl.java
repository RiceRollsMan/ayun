package com.example.ayun.service.Impls;

import com.example.ayun.mapper.FileMapper;
import com.example.ayun.pojo.YunFile;
import com.example.ayun.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;
    @Override
    public List<YunFile> showFiles(String presentPath) {
        List<YunFile> allYunFiles =  fileMapper.showFiles(presentPath);//此时返回的是该路径下所有的文件，不仅限于一级
        //此时还要进行的是筛选就是筛选出只有一级目录的。
        /*
         * 1.进行分割 把presentPath后面的部分提出来*/
        List<YunFile> yunFiles = new ArrayList<>();
        for(YunFile yunFile:showNormalFiles(presentPath))
            yunFiles.add(yunFile);//先加正常的
        for(YunFile yunFile:showDeletedFiles(presentPath))
            yunFiles.add(yunFile);//再加已经被删了的，ok不
        return yunFiles;
    }
    @Override
    public List<YunFile> showNormalFiles(String presentPath) {
        List<YunFile> allYunFiles =  fileMapper.showNormalFiles(presentPath);//此时返回的是该路径下所有的文件，不仅限于一级
        //此时还要进行的是筛选就是筛选出只有一级目录的。
        /*
         * 1.进行分割 把presentPath后面的部分提出来*/
        List<YunFile> yunFiles = new ArrayList<>();
        for(YunFile yunFile:allYunFiles){
            if(yunFile.getFilePath().split("/").length-presentPath.split("/").length==1){//这个就是筛选到了满足一级条件的对象
//                System.out.println("nomal:"+yunFile);
                yunFiles.add(yunFile);
            }
        }
        return yunFiles;
    }
    @Override
    public List<YunFile> showDeletedFiles(String presentPath) {
        List<YunFile> allYunFiles =  fileMapper.showDeletedFiles(presentPath);//此时返回的是该路径下所有的文件，不仅限于一级
        //此时还要进行的是筛选就是筛选出只有一级目录的。
        /*
         * 1.进行分割 把presentPath后面的部分提出来*/
        List<YunFile> yunFiles = new ArrayList<>();
        for(YunFile yunFile:allYunFiles){
            if(yunFile.getFilePath().split("/").length-presentPath.split("/").length==1){//这个就是筛选到了满足一级条件的对象
//                System.out.println("deleted:"+yunFile);
                yunFiles.add(yunFile);
            }
        }
        return yunFiles;
    }

    @Override
    public void addFile(String userName, String fileName, String filePath, String fileSize, String fileType) {
        Integer id = 0;//默认为0嘛。具体原因请看pojo层。
        Integer state = 1;//上传文件的时候，文件状态默认为1(正常)。
        Integer isDir=0;//能上传的都不是文件夹。So 默认为0.
        Date addDate=new Date();//上传文件的时候，上传时间默认为当前时间
        //YunFile对象封装
        YunFile yunFile=new YunFile(id,//id默认为0，数据库实现主键自增长
                userName,
                fileName,
                filePath,
                fileSize,
                fileType,
                state,
                isDir,
                addDate
        );
        //调用mapper进行数据库的insert
        fileMapper.addFile(yunFile);
    }

    @Override
    public String getFolderPathById(int id) {
        return fileMapper.getFolderPathById(id);
    }

    @Override
    public void makeAFolder(String userName, String fileName, String filePath) {
        Integer id = 0;//默认为0嘛。具体原因请看pojo层。
        Integer state = 1;//上传文件的时候，文件状态默认为1(正常)。
        Integer isDir=1;//能上传的都不是文件夹。So 默认为1.
        Date addDate=new Date();//上传文件的时候，上传时间默认为当前时间
        String fileType="";
        String fileSize=""+0;
        //YunFile对象封装
        YunFile yunFile=new YunFile(id,//id默认为0，数据库实现主键自增长
                userName,
                fileName,
                filePath,
                fileSize,
                fileType,
                state,
                isDir,
                addDate);
        fileMapper.makeAFolder(yunFile);
    }

    @Override
    public List<YunFile> getFilesByPath(String filePath) {
        return fileMapper.getFilesByPath(filePath);
    }

    @Override
    public void addShareFile(String userName,
                             String fileName,
                             String filePath,
                             String fileSize,
                             String fileType,
                             Integer isDir) {
        Integer id=0;
        Integer state=1;
        Date addDate=new Date();
        YunFile yunFile=new YunFile(id,userName,fileName,filePath,fileSize,fileType,state,isDir,addDate);
        fileMapper.addShareFile(yunFile);
    }

    @Override
    public YunFile queryFileById(Integer id) {
        return fileMapper.queryFileById(id);
    }

    @Override
    public YunFile queryFileByIdAll(Integer id) {
        return fileMapper.queryFileByIdAll(id);
    }

    @Override
    public int deleteFile(String filePath) {
        Date deleteDate=new Date();
        return fileMapper.deleteFile(filePath,deleteDate);
    }

    @Override
    public int deleteFolder(String filePath) {
        Date deleteDate=new Date();
        return fileMapper.deleteFolder(filePath,deleteDate);
    }

    @Override
    public void deleteForever(String filePath){
        fileMapper.deleteForever(filePath);
    }

    @Override
    public int renameFile(Integer id, String fileName) {
        return fileMapper.renameFile(id,fileName);
    }

    @Override
    public List<YunFile> queryFilesByFileName(HashMap<String, Object> map) {
        return fileMapper.queryFilesByFileName(map);
    }

    @Override
    public void restoreFile(String filePath) {
        fileMapper.restoreFile(filePath);
    }

    @Override
    public void restoreFolder(String filePath) {
        fileMapper.restoreFolder(filePath);
    }

}
