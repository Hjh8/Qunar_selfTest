package org.codekiang.simulateShell.pool;

import org.codekiang.simulateShell.commands.Command;
import java.io.File;
import java.util.*;

public class CommandsFactory {

    public Map<String, Command> commandMap = new HashMap<>();

    private String commandFilePath = "src/test/java/org/codekiang/simulateShell/commands";

    private String classpath = "org.codekiang.simulateShell.commands";

    /**
     * 加载所有的指令类，并将其放进HashMap中，key类名(即指令名)，value对象
     */
    public CommandsFactory() throws Exception {
        List<File> fileList = getFileList(commandFilePath);
        for (File file: fileList) {
            loadFile(file);
        }
    }

    public List<File> getFileList(String filePath) {
        List<File> fileList = new ArrayList<>();
        File dir = new File(filePath);
        File[] files = dir.listFiles();
        if(files != null) {
            for(File file: files) {
                if(file.isDirectory()) {
                    fileList.addAll(getFileList(file.getAbsolutePath()));
                }
                else {
                    String fileName = file.getName();
                    String fileType = fileName.substring(fileName.lastIndexOf('.'));
                    // 只放入java类
                    if (!Objects.equals(fileName, "Command.java") && Objects.equals(fileType, ".java")){
                        fileList.add(file);
                    }
                }
            }
        }
        return fileList;
    }

    public void loadFile(File file) throws Exception {
        String fileName = file.getName().replace(".java", "");
        Class<?> aClass = Class.forName(classpath + "." + fileName);
        commandMap.put(fileName, (Command) aClass.newInstance());
    }

}
