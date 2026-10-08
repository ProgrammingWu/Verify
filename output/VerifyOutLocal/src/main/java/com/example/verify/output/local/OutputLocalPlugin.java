/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.output.local;

import com.example.verify.api.app.PluginContext;
import org.pf4j.Plugin;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.File;

public class OutputLocalPlugin extends Plugin {
    private static final File CONFIG_FILE = new File("config/OutputLocalPlugin/config.json");
    private static OutputLocalPlugin instance;
    private PluginContext context;
    private LocalPluginConfig config;

    /**
     * @deprecated 警告，此仅为测试函数。可能在不通知的情况下移除。请不要调用。
     * */
    public OutputLocalPlugin(){
        config = loadConifg();
    }

    public OutputLocalPlugin(PluginContext context){
        if(instance!=null){
            throw new IllegalStateException("OutputLocalPlugin instance has already been created");
        }
        this.context = context;
        instance = this;
        config = loadConifg();
    };

    public static OutputLocalPlugin getOutputLocalPluginInstance(){
        if(instance==null){
            throw new IllegalStateException("OutputLocalPlugin instance has not been created");
        }
        return instance;
    }

    public PluginContext getContext() {
        if(context==null){
            throw new IllegalStateException("context has not been created");
        }
        return context;
    }

    private LocalPluginConfig loadConifg(){
        if(CONFIG_FILE.exists()){
            return new ObjectMapper().readValue(CONFIG_FILE, LocalPluginConfig.class);
        }
        return new LocalPluginConfig();
    }

    //==================以下仅为测试===============
    /**
     * @deprecated 警告，此仅为测试函数。可能在不通知的情况下移除。请不要调用。
     * */
    public void saveConfig(){
        if(config==null){
            throw new IllegalStateException("config has not been created");
        }
        CONFIG_FILE.getParentFile().mkdirs();

       // ObjectMapper objectMapper = new ObjectMapper();

        ObjectMapper Mapper = JsonMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();
        Mapper.writeValue(CONFIG_FILE, config);
    }

    public LocalPluginConfig getConfig() {
        return config;
    }

    //==================以上仅为测试============

}
