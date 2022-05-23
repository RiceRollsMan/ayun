package com.example.ayun.mapper;

import com.example.ayun.pojo.YunFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Mapper
@Repository
public interface FileMapper {
//    wenjianzhanshi
    public List<YunFile> showFiles(String presentPath);//通过parent路径找到该路径下的一级文件，而且返回整个YunFile文件。
    public List<YunFile> showNormalFiles(String presentPath);//state=1的
    public List<YunFile> showDeletedFiles(String presentPath);//state=0的
//    wenjianshangchun
    public void addFile(YunFile yunFile);//添加一个文件
//
    public String getFolderPathById(int id);//根据folder的id拿到他的路径
//
    public void makeAFolder(YunFile yunFile);//创建一个文件夹
//
    public List<YunFile> getFilesByPath(String filePath);//

//    shareAddFile
    public void addShareFile(YunFile yunFile);

//
    public YunFile queryFileById(Integer id);
    public YunFile queryFileByIdAll(Integer id);
//
/*
* state = 1 zhengchang
* state = -1 yongjiushanchu
* state = 0 zhudongshanchu
* state = 2 beidongshanchu*/
    public int deleteFile(String filePath, Date deleteDate);
    public int deleteFolder(String filePath, Date deleteDate);
    public void deleteForever(String filePath);
//
    public int renameFile(Integer id,String fileName);
//
    public List<YunFile> queryFilesByFileName(HashMap<String,Object> map);

//
    public void restoreFile(String filePath);
    public void restoreFolder(String filePath);

}
