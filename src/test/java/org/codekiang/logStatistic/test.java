package org.codekiang.logStatistic;

import org.junit.Test;

import java.io.*;
import java.util.*;

public class test {

    String filePath = "src/test/resources/access.log";

    @Test
    public void testLogStatistic(){
        File file = new File(filePath);
        if(!file.exists()){
            System.out.println("file is not exist!");
        }
        else {
            try {
                logStatistic(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void logStatistic(File file) throws Exception {
        Statistic statistic = new Statistic();
        statistic.doLogStatistic(file);

        System.out.println("请求总量: " + statistic.allQueryCount);

        System.out.println("请求最频繁的10个 HTTP 接口，及其相应的请求数量:");
        List<Map.Entry<String, Integer>> entries = statistic.topKFrequent(10);
        entries.forEach(e-> System.out.println(e.getKey()+"： "+ e.getValue()));

        Map<String, Integer> queryTypeNumMap = statistic.queryTypeNumMap;
        System.out.println("POST的请求数量：" + queryTypeNumMap.get("POST"));
        System.out.println("GET的请求数量：" + queryTypeNumMap.get("GET"));

        Map<String, Set<String>> uriTypeMap = statistic.uriTypeMap;
        for(Map.Entry<String, Set<String>> m: uriTypeMap.entrySet()){
            System.out.println(m.getKey() + "类别");
            m.getValue().forEach(System.out::println);
        }

    }

}
