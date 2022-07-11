package org.codekiang.validLines;

import java.io.*;
import java.util.Objects;

public class testValidLines {

    static String filePath = "src/main/resources/StringUtils.java";

    static String validFilePath = "src/main/java/org/codekiang/validLines/validLineCount.txt";

    static long count = 0;

    static long spaceCount = 0;

    static long commentCount = 0;

    static long commentsCount = 0;

    public static void main(String[] args) {
        File file = new File(filePath);
        if(!file.exists()){
            System.out.println("file is not exist!");
        }
        else {
            try {
                getFileRows(file);
                long validRows = (count - spaceCount - commentsCount - commentCount);
                System.out.println("validRows: " + validRows);
                // 结果保存到文件
                saveValidFile(validRows);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getFileRows(File file) throws IOException {
        count = getCount(new BufferedReader(new FileReader(file)));
        spaceCount = getSpaceCount(new BufferedReader(new FileReader(file)));
        long[] commentCountArray = getCommentCount(new BufferedReader(new FileReader(file)));
        commentCount = commentCountArray[0];
        commentsCount = commentCountArray[1];
    }

    public static long getCount(BufferedReader reader){
        return reader.lines().count();
    }

    public static long getSpaceCount(BufferedReader reader) {
        return reader.lines()
                .filter(e -> e.trim().length() == 0)
                .count();
    }

    /**
     * 获取注释（包括单行和多行注释）的行数
     * @param reader
     * @return 返回一个数组，第一个元素表示单行注释的行数，第二个元素表示多行注释的行数
     * @throws IOException
     */
    public static long[] getCommentCount(BufferedReader reader) throws IOException {
        long[] res = new long[2];
        // 注释要注意 /* */ 多行注释每行没有 * 时的情况
        int commentsCount_temp = 0;
        String s;
        while ((s = reader.readLine()) != null){
            s = s.trim();
            // 避免出现多行注释中出现空行的情况
            if(s.length() == 0){
                continue;
            }
            // 单行注释
            if(s.startsWith("//")){
                res[0]++;
                continue;
            }
            // 开始进入多行注释
            if(s.startsWith("/*")){
                commentsCount_temp = 1;
            }
            else if(commentsCount_temp != 0){
                commentsCount_temp++; // 计数多行注释
            }
            if(s.endsWith("*/")){
                // 结束多行注释
                res[1] += commentsCount_temp;
                commentsCount_temp = 0;
            }
        }
        return res;
    }

    public static void saveValidFile(long validLines) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(validFilePath));
        fileWriter.write(validLines + "");
        fileWriter.close();
    }
}
