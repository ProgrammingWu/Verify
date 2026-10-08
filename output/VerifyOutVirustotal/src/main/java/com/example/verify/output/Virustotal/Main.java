/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.output.Virustotal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        /*
        String secret = OutVirustotalPlugin.getApiKey();
        System.out.println("secret: " + secret);
        */
        String json = null;
        try {
            json = Files.readString(Path.of("sample/vt-safe.json"));
            //json = Files.readString(Path.of("sample/vt-unsafe.json"));
            //json = Files.readString(Path.of("sample/vt-unsafe2.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        VirustotalWebFetchOutput.AnalysisStats analysisStats = VirustotalWebFetchOutput.parseAnalysisStats(json);
        System.out.println(analysisStats);
    }
}
