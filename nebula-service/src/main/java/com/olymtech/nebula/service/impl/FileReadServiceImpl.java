package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.service.IFileReadService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwenji on 2015/11/5.
 */
@Service
public class FileReadServiceImpl implements IFileReadService {
    @Override
    public List<String> ReadFile(String path) throws IOException {
        List<String> filecontent=new ArrayList<>();
        File file=new File(path);
        BufferedReader reader = null;
        try {
//            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
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
        File file=new File(path);
        FileOutputStream fos=new FileOutputStream(file,false);
        String hh= "\r\n";
        content=content.replace("\n",hh);
        try {
            fos.write(content.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            fos.close();
        }
    }
}
