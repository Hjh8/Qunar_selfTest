package org.codekiang.decryptText;

import com.google.common.base.Joiner;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class testDecryptText {

    static String sdxl_prop_path = "src/main/resources/sdxl_prop.txt";

    static String sdxl_template_path = "src/main/resources/sdxl_template.txt";

    static String sdxl_path = "src/main/resources/sdxl.txt";

    static TextData textData;

    public static void main(String[] args) {
        File sdxl_prop = new File(sdxl_prop_path);
        if(!sdxl_prop.exists()){
            System.out.println("sdxl_prop is not exist!");
        }
        else {
            try {
                textData = new TextData(new File(sdxl_prop_path));
                templateText(sdxl_template_path, sdxl_path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void templateText(String template_path, String sdxl_path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(template_path)));
        FileWriter writer = new FileWriter(new File(sdxl_path));
        List<String> collect = reader.lines().map(e -> {
            String replaceStr = e;
            Pattern pattern = Pattern.compile("\\$([a-zA-Z]+)\\((\\d+)\\)");
            Matcher matcher = pattern.matcher(e);
            // 需要先执行find函数才能找到
            if(matcher.find()){
                String type = matcher.group(1);
                int index = Integer.parseInt(matcher.group(2));
                String data = getSortedDataByIndex(type, index);
                replaceStr = matcher.replaceAll(data);
            }
            return replaceStr;
        }).collect(Collectors.toList());
        // 写入文件
        Joiner.on("\n").appendTo(writer, collect);
        writer.close();
    }

    public static String getSortedDataByIndex(String type, int index) {
        return textData.map.get(type).get(index);
    }

}
