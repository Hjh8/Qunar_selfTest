package org.codekiang.validLines;

import org.junit.Test;
import java.io.*;

public class test {

    String filePath = "src/test/resources/StringUtils.java";
    String validFilePath = "src/test/java/org/codekiang/validLines/validLineCount.txt";
    long count = 0;
    long spaceCount = 0;
    long commentCount = 0;
    long commentsCount = 0;

    @Test
    public void testValidLies() {
        File file = new File(filePath);
        if(!file.exists()){
            System.out.println("file is not exist!");
        }
        else {
            try {
                getFileRows(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getFileRows(File file) throws IOException {
        BufferedReader countReader = new BufferedReader(new FileReader(file));
        count = countReader.lines().count();

        BufferedReader spaceCountReader = new BufferedReader(new FileReader(file));
        spaceCount = spaceCountReader.lines()
                .filter(e -> e.trim().length() == 0)
                .count();

        // 注释要注意 /* */ 多行注释每行没有 * 时的情况
        BufferedReader commentsCountReader = new BufferedReader(new FileReader(file));
        int commentsCount_temp = 0;
        String s;
        while ((s = commentsCountReader.readLine()) != null){
            s = s.trim();
            // 单行注释
            if(s.matches("//.*")){
                commentCount++;
                continue;
            }
            // 开始进入多行注释
            if(s.matches("/\\*.*")){
                commentsCount_temp = 1;
            }
            else if(commentsCount_temp != 0){
                commentsCount_temp++; // 计数多行注释
            }
            // 结束多行注释
            if(s.matches("\\*/")){
                commentsCount += commentsCount_temp;
                commentsCount_temp = 0;
            }
        }

        int validRows = (int) (count - spaceCount - commentsCount - commentCount);
        System.out.println("validRows: " + validRows);
        // 结果保存到文件
        saveValidFile(validRows);
    }

    public void saveValidFile(int validLines) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(validFilePath));
        fileWriter.write(validLines + "");
        fileWriter.close();
    }
}
