package org.codekiang.decryptText;

import com.google.common.base.Joiner;
import org.junit.Test;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class test {

    String sdxl_prop_path = "src/test/resources/sdxl_prop.txt";
    String sdxl_template_path = "src/test/resources/sdxl_template.txt";
    String sdxl_path = "src/test/resources/sdxl.txt";

    @Test
    public void testDecryptText(){
        File sdxl_prop = new File(sdxl_prop_path);
        if(!sdxl_prop.exists()){
            System.out.println("sdxl_prop is not exist!");
        }
        else {
            try {
                templateText(sdxl_template_path, sdxl_path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void templateText(String template_path, String sdxl_path) throws IOException {
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
        Joiner.on("\n").appendTo(writer, collect);
        writer.close();
    }

    public String getSortedDataByIndex(String type, int index) {
        Field field;
        String data = "";
        Class<TextData> textDataClass = TextData.class;
        try {
            field = textDataClass.getDeclaredField(type);
            Constructor<?> constructor = textDataClass.getConstructor(File.class);
            List<String> list = (List<String>) field.get(constructor.newInstance(new File(sdxl_prop_path)));
            data = list.get(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
