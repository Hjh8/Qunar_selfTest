package org.codekiang.simulateShell.commands;

import com.google.common.base.Joiner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class grep implements Command<String>{

    @Override
    public String getResult(Object lastRes, String option, List<String> params) throws Exception {
        List<String> res;
        String keyword = params.get(0);
        if(Objects.isNull(lastRes)){
            String filePath = params.get(1);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            res = bufferedReader.lines()
                    .filter(e -> e.matches(".*" + keyword + ".*"))
                    .collect(Collectors.toList());
        }
        else {
            String[] split = ((String) lastRes).split("\n");
            res = Stream.of(split)
                    .filter(e -> e.matches(".*" + keyword + ".*"))
                    .collect(Collectors.toList());
        }
        return Joiner.on("\n").join(res);
    }
}
