package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.service.IFileReadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwenji on 2015/11/5.
 */
@Service
public class FileReadServiceImpl implements IFileReadService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<String> ReadFile(String path) throws IOException {
        List<String> filecontent = new ArrayList<>();
        File file = new File(path);

        if(!file.isFile()){
            return filecontent;
        }

        FileInputStream fileread = new FileInputStream(file);
        BufferedReader reader = null;
        try {
//            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new InputStreamReader(fileread, "UTF-8"));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                filecontent.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return filecontent;
    }

    @Override
    public void SaveFile(String path, String content) throws IOException {
        File file = new File(path);
        FileOutputStream fos = new FileOutputStream(file, false);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        String hh = "\r\n";
        content = content.replace("\n", hh);
        try {
            osw.write(content);
            osw.flush();
        } catch (IOException e) {
            logger.error("SaveFile error:",e);
        } finally {
            fos.close();
            osw.close();
        }
    }

    @Override
    public boolean newDirOrFile(String type, String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            if("dir".equals(type)) {
                file.mkdir();
            }
            else {
                file.createNewFile();
            }
            return true;
        }

        return false;
    }
}
