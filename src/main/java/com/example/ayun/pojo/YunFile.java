package com.example.ayun.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*
 * 文件类的pojo
 * */
public class YunFile {
    private Integer id;//对应id（自增主键） PK 我们只需要将主键设置为null，0或者不设置该字段，数据库就会为我们自动生成一个主键值。so在插入式默认0就行了
    private String userName;//文件所有人
    private String fileName;//文件名称
    private String filePath;//文件路径
    private String fileSize;//文件大小
    private String fileType;//文件类型，例如txt
    private Integer state;//文件状态 1：正常 0：已删除
    private Integer isDir;//文件是否为文件夹 1：是文件夹 0：不是文件夹
    private Date addDate;//文件被添加日期，应该在插入式默认为new Date();
    private Date deleteDate;//文件被删除日期，应该在删除文件时默认为new Date();
    /*
     *非全参构造器1:用于addFile()使用
     * */
    public YunFile(Integer id,
                   String userName,
                   String fileName,
                   String filePath,
                   String fileSize,
                   String fileType,
                   Integer state,
                   Integer isDir,
                   Date addDate) {
        this.id=id;
        this.userName=userName;
        this.fileName=fileName;
        this.filePath=filePath;
        this.fileSize=fileSize;
        this.fileType=fileType;
        this.state=state;
        this.isDir=isDir;
        this.addDate=addDate;
    }
}
