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
        String[] split = s.split("\\s");
        CommandName = split[0];
        for (int i=1; i < split.length; i++) {
            if (split[i].matches("-[a-z]*")){
                option = split[i];
            }
            else {
                params.add(split[i]);
            }
        }
    }
}
