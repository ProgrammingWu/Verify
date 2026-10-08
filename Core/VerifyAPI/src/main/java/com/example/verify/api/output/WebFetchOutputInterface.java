/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.api.output;

import org.pf4j.ExtensionPoint;

public interface WebFetchOutputInterface extends ExtensionPoint {
    default PreWebFetchResult preWebFetch(PreWebFetchParam Param){
        return new PreWebFetchResult();
    } ;

    public class PreWebFetchParam{

        private String url;

        public PreWebFetchParam(String inURL) {
            this.url = inURL;
        }

        public String getUrl() {
            return url;
        }

    }

    public class PreWebFetchResult{
        private SafetyLevel  safetyLevel = SafetyLevel.Unknown;

        public PreWebFetchResult(){
            safetyLevel = SafetyLevel.Unknown;
        };

        public PreWebFetchResult(SafetyLevel safetyLevel) {
            this.safetyLevel = safetyLevel;
        }

        public SafetyLevel getSafetyLevel() {
            return safetyLevel;
        }
    }
}
