package org.codekiang.simulateShell;

import org.codekiang.simulateShell.commands.Command;
import org.codekiang.simulateShell.entity.CommandDefinition;
import org.codekiang.simulateShell.pool.CommandsFactory;
import java.util.*;
import java.util.stream.Collectors;

public class test {

    /**
     * 根绝 | 分割，接着根据\s分割，放入类中，接着将类放进list
     * 遍历list，执行对应的类
     * cat src/test/java/org/codekiang/simulateShell/filename.txt | grep ab
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            List<CommandDefinition> commandList = parseCommand(s);
            Object res;
            try {
                res = resolve(commandList);
                System.out.println(res);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 解析用户输入的指令，利用 | 分割出每个指令
     * @param s 用户输入的指令
     * @return 每个指令的集合
     */
    private static List<CommandDefinition> parseCommand(String s) {
        return Arrays.stream(s.split("\\|"))
                .map(e -> new CommandDefinition(e.trim()))
                .collect(Collectors.toList());
    }


    /**
     * 根据指令list对每个指令依次解析
     * @param list 指令list
     * @return 用户输入的指令执行的结果
     * @throws Exception 异常
     */
    public static Object resolve(List<CommandDefinition> list) throws Exception {
        CommandsFactory factory = new CommandsFactory();
        Map<String, Command> commandMap = factory.commandMap;
        // 存放上条指令
        Object res = null;
        // 执行每个指令
        for (CommandDefinition cd : list){
            String name = cd.getCommandName();
            Command command = commandMap.get(name);
            if(command == null){
                return null;
            }
            res = command.getResult(res, cd.getOption(), cd.getParams());
        }
        return res;
    }

}
