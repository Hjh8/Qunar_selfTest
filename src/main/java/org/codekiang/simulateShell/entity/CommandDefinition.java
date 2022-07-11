package org.codekiang.simulateShell.entity;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommandDefinition {

    public String CommandName;

    public String option;

    public List<String> params = new ArrayList<>();

    public CommandDefinition(String s){
        // 以一个或多个空格分隔
        String[] split = s.split(" +");
        CommandName = split[0];
        for (int i=1; i < split.length; i++) {
            // 匹配选项
            if (split[i].matches("-[a-z]+")){
                option = split[i];
            }
            else {
                params.add(split[i]);
            }
        }
    }
}
