package org.codekiang.decryptText;

import lombok.Data;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TextData {

    public List<String> natureOrder;

    public List<String> indexOrder;

    public List<String> charOrder;

    public List<String> charOrderDESC;

    public TextData(File file) throws IOException {
        initNatureOrder(new BufferedReader(new FileReader(file)));
        initIndexOrder(new BufferedReader(new FileReader(file)));
        initCharOrder(new BufferedReader(new FileReader(file)));
        initCharOrderDESC(new BufferedReader(new FileReader(file)));
    }

    public void initNatureOrder(BufferedReader bufferedReader){
        this.natureOrder = bufferedReader.lines()
                .map(e-> e.split("\\s")[1])
                .collect(Collectors.toList());
    }

    public void initIndexOrder(BufferedReader bufferedReader){
        this.indexOrder = bufferedReader.lines()
                .sorted((e1, e2) -> {
                    int a = Integer.parseInt(e1.split("\\s")[0]);
                    int b = Integer.parseInt(e2.split("\\s")[0]);
                    return a - b;
                })
                .map(e-> e.split("\\s")[1])
                .collect(Collectors.toList());
    }

    public void initCharOrder(BufferedReader bufferedReader){
        this.charOrder = bufferedReader.lines()
                .map(e-> e.split("\\s")[1])
                .sorted()
                .collect(Collectors.toList());
    }

    private void initCharOrderDESC(BufferedReader bufferedReader) {
        this.charOrderDESC = bufferedReader.lines()
                .map(e-> e.split("\\s")[1])
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }
}
