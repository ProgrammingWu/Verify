/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.plugin.input.cc;

import com.example.verify.api.app.PluginContext;
import com.example.verify.api.input.WebFetchInputInterface;
import com.example.verify.api.output.WebFetchOutputInterface;
import org.pf4j.Extension;

@Extension
public class CCWebFetchInput implements WebFetchInputInterface {
    @Override
    public String preWebFetch(String body) {
        System.out.println("开始执行CC实现的WebFetchInputInterface");

        tools.jackson.databind.JsonNode bodyNode = new tools.jackson.databind.ObjectMapper().readTree(body);
        tools.jackson.databind.JsonNode urlNode= bodyNode.path("tool_input").path("url");
        String url = urlNode.asString();
        //System.out.println("CCWebFetchInput::preWebFetch需要验证的url："+url);

        com.example.verify.api.output.WebFetchOutputInterface.PreWebFetchResult Result =
                InputCCPlugin.getInputCCPluginInstance().getContext().getAppSystem().launchPreWebFetch(
                    new WebFetchOutputInterface.PreWebFetchParam(url)
                );

        HookResult hookResult = createHookResult(Result);

        tools.jackson.databind.ObjectMapper objectMapper = new tools.jackson.databind.ObjectMapper();
        String hookResultJsonString = objectMapper.writeValueAsString(hookResult);

        System.out.println("hookResultJsonString: " + hookResultJsonString);

        return hookResultJsonString;
    }

    private static HookResult createHookResult(WebFetchOutputInterface.PreWebFetchResult result) {
        HookResult  hookResult = new HookResult();
        {
            HookResult.HookSpecificOutput hookSpecificOutput = new HookResult.HookSpecificOutput();

            String permissionDecision = "ask";
            String PermissionDecisionReason = null;

            switch (result.getSafetyLevel()) {
                case Unknown:
                    permissionDecision = "ask";
                    PermissionDecisionReason = "网址未知是否安全。";
                    break;
                case AbsolutelySafe:
                case Safe:
                    permissionDecision = "allow";
                    break;
                case unsafe:
                    permissionDecision = "deny";
                    break;

                default:
                    throw new IllegalStateException("错误，未处理值");
            }

            hookSpecificOutput.setPermissionDecision(permissionDecision);
            hookSpecificOutput.setPermissionDecisionReason(PermissionDecisionReason);

            hookResult.setHookSpecificOutput(hookSpecificOutput);
        }
        return hookResult;
    }
}
