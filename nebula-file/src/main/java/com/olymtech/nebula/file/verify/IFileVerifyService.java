package com.olymtech.nebula.file.verify;

import java.util.Map;

/**
 * Created by WYQ on 2015/10/30.
 */
public interface IFileVerifyService {
    Map<String,String> checkFilesMd5ByDir(String dir, String suffix);

//    String getFileMD5ByFilePath(String filePath);

//    String getFileModifyTimeByFilePath(String filePath);
}
