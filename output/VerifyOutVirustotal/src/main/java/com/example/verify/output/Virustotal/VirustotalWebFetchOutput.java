/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.output.Virustotal;

import com.example.verify.api.output.SafetyLevel;
import com.example.verify.api.output.WebFetchOutputInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.pf4j.Extension;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Extension
public class VirustotalWebFetchOutput implements WebFetchOutputInterface {
    @Override
    public PreWebFetchResult preWebFetch(PreWebFetchParam Param) {
        String apiKey = OutVirustotalPlugin.getApiKey();
        if (apiKey == null) {
            System.out.println("VirustotalWebFetchOutput::preWebFetch: API Key is null");
            return new PreWebFetchResult(SafetyLevel.Unknown);
        }
        RestClient client = RestClient.builder()
                .baseUrl("https://www.virustotal.com")
                .defaultHeader("x-apikey", apiKey)
                .build();

        String host = URI.create(Param.getUrl()).getHost();
        System.out.println("VerifyOutVirustotal:查询的RUL: " + Param.getUrl()+";此URL的Host: " + host);

        String json = client.get()
                .uri("/api/v3/domains/{domain}",
                        host)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);

        /*
        //把返回的Json输出到文件的代码
        ObjectMapper Mapper = JsonMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();
        JsonNode jsonNode = Mapper.readTree(json);
        Mapper.writeValue(new java.io.File("vt-response.json"), jsonNode);
         */

        AnalysisStats analysisStats = parseAnalysisStats(json);
        System.out.println("VerifyOutVirustotal:返回的数据: "+analysisStats);

        if(analysisStats!=null) {
            if(analysisStats.isSafe()){
                return new PreWebFetchResult(SafetyLevel.Safe);
            }else{
                return new PreWebFetchResult(SafetyLevel.unsafe);
            }
        }
        return new PreWebFetchResult(SafetyLevel.Unknown);
    }

    public static AnalysisStats parseAnalysisStats(String json) {
        if(json==null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();

        JsonNode body = mapper.readTree(json);   // 整个 body
        JsonNode statsNode = body.path("data").path("attributes").path("last_analysis_stats");
        AnalysisStats analysisStats = mapper.treeToValue(statsNode, AnalysisStats.class);
        return analysisStats;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AnalysisStats{
        /**恶意*/
        private int malicious;
        /**可疑*/
        private int suspicious;
        /**未检出*/
        private int undetected;
        /**无害*/
        private int harmless;
        /**超时*/
        private int timeout;


        public int getMalicious() {
            return malicious;
        }

        public void setMalicious(int malicious) {
            this.malicious = malicious;
        }

        public int getSuspicious() {
            return suspicious;
        }

        public void setSuspicious(int suspicious) {
            this.suspicious = suspicious;
        }

        public int getUndetected() {
            return undetected;
        }

        public void setUndetected(int undetected) {
            this.undetected = undetected;
        }

        public int getHarmless() {
            return harmless;
        }

        public void setHarmless(int harmless) {
            this.harmless = harmless;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        boolean isSafe(){
            if(harmless<=0){
                //无害需要大于0，防止无害是0的情况
                return false;
            }
            if(malicious>0){
                //恶意大于0。就直接判断危险。
                return false;
            }
            if(suspicious>0){
                //可疑大于0。就直接判断危险。
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "malicious(恶意)=" + malicious + ", suspicious(可疑)=" + suspicious
                    + ", undetected(未检查)=" + undetected + ", harmless(安全)=" + harmless
                    + ", timeout(超时)=" + timeout+ ", 综合判断，是否安全？="+isSafe();
        }
    }
}
