/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.output.local;

import com.example.verify.api.app.AppInterface;
import com.example.verify.api.output.SafetyLevel;
import com.example.verify.api.output.WebFetchOutputInterface;
import org.pf4j.Extension;

import java.net.URI;
import java.util.List;

@Extension
public class LocalWebFetchOutput implements WebFetchOutputInterface {

    @Override
    public PreWebFetchResult preWebFetch(PreWebFetchParam Param) {
        String host = URI.create(Param.getUrl()).getHost();
        System.out.println("VerifyOutLocal: RUL: " + Param.getUrl()+";此URL的Host: " + host);

        if(isDeny(host)){
            //在黑名单就直接返回不安全
            System.out.println("VerifyOutLocal: "+host+"在黑名单内。不安全");
            return new PreWebFetchResult(SafetyLevel.unsafe);
        }

        if(isAllow(host)){
            //在白名单里，就直接强制通过
            System.out.println("VerifyOutLocal: "+host+"在白名单内。安全");
            return new PreWebFetchResult(SafetyLevel.AbsolutelySafe);
        }
        //返回不清楚是否安全
        return new PreWebFetchResult(SafetyLevel.Unknown);
    }

    private boolean matchConfigHost(List<String> patterns,String host){
        if(patterns==null || patterns.isEmpty()){
            return false;
        }
        AppInterface AppSystem = OutputLocalPlugin.getOutputLocalPluginInstance().getContext().getAppSystem();
        LocalPluginConfig config= OutputLocalPlugin.getOutputLocalPluginInstance().getConfig();
        for (String deny : patterns) {
            if(AppSystem.matchHost(deny, host)){
                return true;
            }
        }
        return false;
    }
    private boolean isDeny(String host){
        LocalPluginConfig config= OutputLocalPlugin.getOutputLocalPluginInstance().getConfig();
        List<String> denys = config.getWebFetchPermissions().getDeny();
        return matchConfigHost(denys,host);
    }

    private boolean isAllow(String host){
        LocalPluginConfig config= OutputLocalPlugin.getOutputLocalPluginInstance().getConfig();
        List<String> allows = config.getWebFetchPermissions().getAllow();
        return matchConfigHost(allows,host);
    }
}
