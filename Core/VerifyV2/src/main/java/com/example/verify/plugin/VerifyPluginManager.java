/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.plugin;

import org.pf4j.*;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VerifyPluginManager extends DefaultPluginManager {

    private final static java.io.File CONFIG_FILE = new java.io.File("config/plugin.json");
    private final static VerifyPluginConfig pluginConfig = loadConfig();

    public VerifyPluginManager() {
        super();
    }

    public VerifyPluginManager(Path... pluginsRoots) {
        super(pluginsRoots);
    }

    public VerifyPluginManager(List<Path> pluginsRoots) {
        super(pluginsRoots);
    }

    @Override
    protected PluginFactory createPluginFactory() {
        return new VerifyPluginFactory();
    }

    public void loadPluginsByConfig(){
        if(pluginConfig.pluginIDsFilterIsValid()){
            System.out.println("发现加载插件过滤规则，正在应用");
            List<Path> pluginPaths = pluginRepository.getPluginPaths();
            List<String> pluginIDsFilter =pluginConfig.getPluginIDsFilter();
            for (Path pluginPath : pluginPaths) {
                String pluginID = getPluginIDFromPath(pluginPath);
                if(pluginIDsFilter.contains(pluginID)){
                    loadPlugin(pluginPath);
                }
            }

        }else{
            System.out.println("未发现加载插件过滤规则，加载全部插件");
            loadPlugins();
        }
    }

    /**从提供的path，获取插件ID*/
    String getPluginIDFromPath(Path pluginPath){
        PluginDescriptorFinder pluginDescriptorFinder = getPluginDescriptorFinder();

        PluginDescriptor pluginDescriptor = pluginDescriptorFinder.find(pluginPath);
        validatePluginDescriptor(pluginDescriptor);

        return pluginDescriptor.getPluginId();
    }

    public List<String> getOutputPluginIDs(){
        if(pluginConfig.defaultUsedOutputPluginIDsIsValid()){
            return pluginConfig.getDefaultUsedOutputPluginIDs();
        }
        //return getPlugins();
        java.util.List<org.pf4j.PluginWrapper> plugins = getPlugins(PluginState.STARTED);
        if(plugins.isEmpty()){
            throw new IllegalStateException("没有找到任何启动的插件。");
            //return null;
        }
        List<String> outputPluginIDs = new ArrayList<>();
        for(org.pf4j.PluginWrapper plugin : plugins){
            outputPluginIDs.add(plugin.getPluginId());
        }
        return outputPluginIDs;
    }

    protected static VerifyPluginConfig loadConfig(){
        if(CONFIG_FILE.exists()){
            System.out.println("已配置插件，从配置加载规则");
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(CONFIG_FILE,VerifyPluginConfig.class);
        }
        return new VerifyPluginConfig();
    };

    //=============以下仅为了测试，不要调用，可能之后会无通知的情况下移除===========
    /**
     * 此函数是为了测试的，正常情况下请不要使用。
     * 可能在不通知的情况下移除
     * @deprecated
     * */
    void saveConfig(){
        CONFIG_FILE.getParentFile().mkdirs();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(CONFIG_FILE,pluginConfig);
    }

    /**
     * 此函数是为了测试的，正常情况下请不要使用。
     * 可能在不通知的情况下移除
     * @deprecated
     * */
    static VerifyPluginConfig getPluginConfig(){
        return pluginConfig;
    }

    //=============以上仅为了测试，不要调用，可能之后会无通知的情况下移除===========
}
