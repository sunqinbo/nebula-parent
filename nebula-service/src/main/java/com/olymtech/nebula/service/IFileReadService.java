package com.olymtech.nebula.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by liwenji on 2015/11/5.
 */
public interface IFileReadService {
    public List<String> ReadFile(String path) throws IOException;

    public void SaveFile(String path,String content) throws IOException;
}
