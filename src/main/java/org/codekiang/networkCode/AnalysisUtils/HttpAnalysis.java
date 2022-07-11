package org.codekiang.networkCode.AnalysisUtils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class HttpAnalysis {

    static final int CODE_SUCCESS = 200;

    static String E1 = "[\u4e00-\u9fa5]"; // 中文

    static String E2 = "[a-zA-Z]"; // 英文

    static String E3 = "\\p{P}"; // 标点符号

    public static String getContextByUrl(String Url) {
        String content = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(Url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            // 判断响应状态码是否为200
            if (response.getStatusLine().getStatusCode() == CODE_SUCCESS) {
                // 获取返回数据
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    /**
     * 根据字符串统计总字符数(包括标点符号)、汉字数、英文字符数、标点符号数
     * @param content 要分析的内容
     * @return 返回一个解析对象AnalysisData
     */
    public static AnalysisData getAnalysisData(String content){
        AnalysisData analysisData = new AnalysisData();
        int chinaCount = 0, englishCount = 0, punctuationCount = 0;
        for(int i=0; i<content.length(); i++){
            String e = String.valueOf(content.charAt(i));
            if(e.matches(E1)){
                chinaCount++;
            }
            else if(e.matches(E2)){
                englishCount++;
            }
            else if (e.matches(E3)){
                punctuationCount++;
            }
        }

        analysisData.setTotal(content.length());
        analysisData.setChinaCount(chinaCount);
        analysisData.setEnglishCount(englishCount);
        analysisData.setPunctuationCount(punctuationCount);
        return analysisData;
    }
}
