package org.codekiang.decryptText;

import lombok.Data;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class TextData {

    public Map<String, List<String>> map = new HashMap<>(4);

    public String natureOrder = "natureOrder";
    public String indexOrder = "indexOrder";
    public String charOrder = "charOrder";
    public String charOrderDESC = "charOrderDESC";

//    public List<String> natureOrder;
//    public List<String> indexOrder;
//    public List<String> charOrder;
//    public List<String> charOrderDESC;

    public TextData(File file) {
        try {
            initNatureOrder(new BufferedReader(new FileReader(file)));
            initIndexOrder(new BufferedReader(new FileReader(file)));
            initCharOrder(new BufferedReader(new FileReader(file)));
            initCharOrderDESC(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void initNatureOrder(BufferedReader bufferedReader){
        List<String> collect = bufferedReader.lines()
                .map(e -> e.split("\\s")[1])
                .collect(Collectors.toList());
        map.put(this.natureOrder, collect);
    }

    public void initIndexOrder(BufferedReader bufferedReader){
        List<String> collect = bufferedReader.lines()
                .sorted((e1, e2) -> {
                    int a = Integer.parseInt(e1.split("\\s")[0]);
                    int b = Integer.parseInt(e2.split("\\s")[0]);
                    return a - b;
                })
                .map(e-> e.split("\\s")[1])
                .collect(Collectors.toList());
        map.put(this.indexOrder, collect);
    }

    public void initCharOrder(BufferedReader bufferedReader){
        List<String> collect = bufferedReader.lines()
                .map(e-> e.split("\\s")[1])
                .sorted()
                .collect(Collectors.toList());
        map.put(this.charOrder, collect);
    }

    private void initCharOrderDESC(BufferedReader bufferedReader) {
        List<String> collect = bufferedReader.lines()
                .map(e-> e.split("\\s")[1])
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        map.put(this.charOrderDESC, collect);
    }
}
