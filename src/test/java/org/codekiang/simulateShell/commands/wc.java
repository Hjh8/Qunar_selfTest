package org.codekiang.simulateShell.commands;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class wc implements Command<Object> {

    @Override
    public Object getResult(Object lastRes, String option, List<String> params) throws Exception {
        int res = 0;
        if(Objects.isNull(lastRes)){
            String filePath = params.get(0);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            if(Objects.equals(option, "-l")){
                res = (int) bufferedReader.lines().count();
            }
        }
        else {
            String[] split = ((String) lastRes).split("\n");
            if(Objects.equals(option, "-l")){
                res = (int) Stream.of(split).count();
            }
        }
        return res;
    }
}
