/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.output.local;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class LocalPluginConfig {

    /**WebFetch权限*/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Permissions webFetchPermissions;

    public Permissions getWebFetchPermissions() {
        return webFetchPermissions;
    }

    public void setWebFetchPermissions(Permissions webFetchPermissions) {
        this.webFetchPermissions = webFetchPermissions;
    }

    public static class Permissions{
        /**允许*/
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<String> allow;
        /**禁止*/
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<String> deny;

        public List<String> getAllow() {
            return allow;
        }

        public void setAllow(List<String> allow) {
            this.allow = allow;
        }

        public List<String> getDeny() {
            return deny;
        }

        public void setDeny(List<String> deny) {
            this.deny = deny;
        }
    }
}
