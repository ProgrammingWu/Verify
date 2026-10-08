/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.plugin.input.cc;

import com.fasterxml.jackson.annotation.JsonInclude;

public class HookResult {

    public HookSpecificOutput hookSpecificOutput ;

    public HookSpecificOutput getHookSpecificOutput() {
        return hookSpecificOutput;
    }

    public void setHookSpecificOutput(HookSpecificOutput hookSpecificOutput) {
        this.hookSpecificOutput = hookSpecificOutput;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class HookSpecificOutput{
        private String hookEventName = "PreToolUse";

        /**为这四个值之一 "allow" "deny""ask""defer"*/
        private String permissionDecision;

        /**显示给用户，不显示给claude的信息，可以写理由，比如允许拒绝等理由*/
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String permissionDecisionReason;

        public String getHookEventName() {
            return hookEventName;
        }

        public void setHookEventName(String hookEventName) {
            this.hookEventName = hookEventName;
        }

        public String getPermissionDecision() {
            return permissionDecision;
        }

        /**@param permissionDecision 为这四个值之一 "allow" "deny""ask""defer"*/
        public void setPermissionDecision(String permissionDecision) {
            this.permissionDecision = permissionDecision;
        }

        public String getPermissionDecisionReason() {
            return permissionDecisionReason;
        }

        /**显示给用户，不显示给claude的信息，可以写理由，比如允许拒绝等理由*/
        public void setPermissionDecisionReason(String permissionDecisionReason) {
            this.permissionDecisionReason = permissionDecisionReason;
        }
    }
}
