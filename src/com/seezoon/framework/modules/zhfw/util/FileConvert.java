package com.seezoon.framework.modules.zhfw.util;

import java.io.*;

/**
 * Created by zengqy on 2018/7/10.
 */
public class FileConvert {
    public static  void main(String[] args) throws IOException {
        copy("GBK","UTF-8","E:\\ideawork\\zhglxt\\src\\com","F:\\test");
    }

    public static void copy(String sourceCharset,String destCharset,String sourcePath,String dirPath) throws IOException {
        File file = new File(sourcePath);
        //File destFile = new File(dirPath);
        String filename = file.getName();
        System.out.println(filename);

        if(file.isDirectory()){
            File newDestFile = new File(dirPath+"/"+filename);
            if(!newDestFile.exists()){
                newDestFile.mkdir();
            }

            File[] files = file.listFiles();
            for(File childFile:files){
                copy(sourceCharset,destCharset,childFile.getAbsolutePath(),newDestFile.getAbsolutePath());
            }
        }else{

            if(!filename.endsWith(".java") && !filename.endsWith(".js") && !filename.endsWith(".htm")){
                return;
            }
            InputStreamReader fis = new InputStreamReader(new FileInputStream(sourcePath),sourceCharset);
            BufferedReader bufferedReader = new BufferedReader(fis);

            FileOutputStream fos = new FileOutputStream(dirPath+"/"+filename);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos,destCharset);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            String line = null;
            while((line = bufferedReader.readLine()) !=null){
               // System.out.println(line);
               // String newString =  new String(line.getBytes(sourceCharset),destCharset);
                printWriter.println(line);
            }
            bufferedReader.close();
            printWriter.close();

        }
    }
}
