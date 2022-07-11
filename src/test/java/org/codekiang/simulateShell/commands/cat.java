package org.codekiang.simulateShell.commands;

import com.google.common.base.Joiner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class cat implements Command<String> {

    @Override
    public String getResult(Object lastRes, String option, List<String> params) throws Exception {
        String res = "";
        String filePath = params.get(0);
        if(Objects.isNull(lastRes)){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            List<String> collect = bufferedReader.lines().collect(Collectors.toList());
            res = Joiner.on("\n").join(collect);
        }
        return res;
    }

}
