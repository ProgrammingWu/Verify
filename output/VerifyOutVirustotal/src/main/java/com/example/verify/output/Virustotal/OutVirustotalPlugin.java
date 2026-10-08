/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.output.Virustotal;

import com.example.verify.api.app.PluginContext;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import java.io.File;

public class OutVirustotalPlugin extends Plugin {
    private static String API_KEY_ENV_VAR_NAME = "VERIFY_VIRUSTOTAL_API_KEY";
    private static OutVirustotalPlugin instance;
    private PluginContext context;

    /**
     * 此构造函数仅为测试，请不要使用这个构造函数
     * @deprecated
     * */
    public OutVirustotalPlugin() {

    }

    public OutVirustotalPlugin(PluginContext context){
        if(instance!=null){
            throw new IllegalStateException("OutVirustotalPlugin instance has already been created");
        }
        this.context = context;
        instance = this;
    };

    public static OutVirustotalPlugin getOutVirustotalPluginInstance(){
        if(instance==null){
            throw new IllegalStateException("OutVirustotalPlugin instance has not been created");
        }
        return instance;
    }

    public PluginContext getContext() {
        if(context==null){
            throw new IllegalStateException("context has not been created");
        }
        return context;
    }

    /**
     * 获取APIKey。如果没有配置环境变量，将返回空指针
     * */
    public static String getApiKey() {
        return System.getenv("VERIFY_VIRUSTOTAL_API_KEY");
    }
}
