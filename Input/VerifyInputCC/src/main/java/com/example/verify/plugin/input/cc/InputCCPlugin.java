/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.plugin.input.cc;
//import org.pf4j.ExtensionPoint;
import com.example.verify.api.app.PluginContext;
import org.pf4j.Plugin;


public class InputCCPlugin extends Plugin
{
    private static InputCCPlugin instance;
    private PluginContext context;

    public InputCCPlugin(PluginContext context){
        if(instance!=null){
            throw new IllegalStateException("InputCCPlugin instance has already been created");
        }
        this.context = context;
        instance = this;
    };

    public static InputCCPlugin getInputCCPluginInstance(){
        if(instance==null){
            throw new IllegalStateException("InputCCPlugin instance has not been created");
        }
        return instance;
    }

    public PluginContext getContext() {
        if(context==null){
            throw new IllegalStateException("context has not been created");
        }
        return context;
    }
}
