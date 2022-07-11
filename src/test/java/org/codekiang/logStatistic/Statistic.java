package org.codekiang.logStatistic;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Statistic {

    // 请求的总数
    public int allQueryCount = 0;

    // 存放每种请求类型的次数，key: 请求类型, value: 请求次数
    public Map<String, Integer> queryTypeNumMap = new HashMap<>(2);

    // 存放每个uri的访问次数
    public Map<String, Integer> apiFrequentMap = new HashMap<>();

    // 存放uri类型及其次数
    public Map<String, Set<String>> uriTypeMap = new HashMap<>(1000);

    /**
     * 按照 / 分割，第一个为请求方式，第二个为类别
     *  GET /twell/querytwellDetailForMobile.htm?arg1=var1&arg2=var2
     * @param file
     * @throws Exception
     */
    public void doLogStatistic(File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String s, queryType, uri;
        while ((s = bufferedReader.readLine()) != null){
            allQueryCount++;

            ArrayList<String> uriSplit = Lists.newArrayList(Splitter.on("/").limit(3).split(s));
            queryType = uriSplit.get(0).trim();
            queryTypeNumMap.put(queryType, queryTypeNumMap.getOrDefault(queryType, 0) + 1);

            uri = "/" + uriSplit.get(1);
            if(uriSplit.size() >= 3){
                uri += "/" + uriSplit.get(2);
            }
            apiFrequentMap.put(uri, apiFrequentMap.getOrDefault(uri, 0) + 1);

            Set<String> uriSet = uriTypeMap.getOrDefault(uriSplit.get(1), new HashSet<>());
            uriSet.add(uri);
            uriTypeMap.put(uriSplit.get(1), uriSet);
        }
    }

    public List<Map.Entry<String, Integer>> topKFrequent(int k) {
        List<Map.Entry<String,Integer>> list = new ArrayList<>(apiFrequentMap.entrySet());
        list.sort((c1, c2) -> -Integer.compare(c1.getValue(), c2.getValue()));
        return list.subList(0, k);
    }

}
