package com.example.ayun.service;

import com.example.ayun.pojo.YunFile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public interface FileService {
    public List<YunFile> showFiles(String presentPath);//这个由下面两个组合完成
    public List<YunFile> showNormalFiles(String presentPath);
    public List<YunFile> showDeletedFiles(String presentPath);

    public void addFile(String userName,
                        String fileName,
                        String filePath,
                        String fileSize,
                        String fileType);

    public String getFolderPathById(int id);

    public void makeAFolder(String userName,
                           String fileName,
                           String filePath);//

    public List<YunFile> getFilesByPath(String filePath);

    public void addShareFile(String userName,
                             String fileName,
                             String filePath,
                             String fileSize,
                             String fileType,
                             Integer isDir);

    public YunFile queryFileById(Integer id);
    public YunFile queryFileByIdAll(Integer id);

    public int deleteFile(String filePath);

    public int deleteFolder(String filePath);

    public void deleteForever(String filePath);

    public int renameFile(Integer id,String fileName);

    public List<YunFile> queryFilesByFileName(HashMap<String,Object> map);

    public void restoreFile(String filePath);
    public void restoreFolder(String filePath);
}
