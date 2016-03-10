package com.olymtech.nebula.file.analyze;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by liwenji on 2015/11/2.
 */

public interface IFileAnalyzeService {
    /**
     *
     * @param dirPath 目标目录的路径
     * @return
     */
    public List<String> getFileListByDirPath(String dirPath);

    public List<String> getFileListByRecursionDirPath(String dirPath);

    public Map<String,Boolean> getDirMapByDirPath(String dirPath);

    public Boolean copyFile(String srcFile, String destFile);

    Boolean rmFile(String key, String filename);

    Boolean copyFolder(File src, File dest) throws IOException;

    String getDirDiffList(String srcDir, String destDir);
}
