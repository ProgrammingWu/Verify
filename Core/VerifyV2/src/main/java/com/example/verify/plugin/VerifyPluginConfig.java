/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.plugin;

import java.util.List;

public class VerifyPluginConfig {
    /**启用的Plugin名字。配置上后，将会只加载这些名字的插件。
     * 如果不配置，那么加载全部插件*/
    private List<String> pluginIDsFilter;

    /**
     * 使用的输出插件。
     * 将会按配置的排序挨个调用。
     * 如果不配置，那么会按插件加载顺序调用（顺序可能不固定）
     * */
    private List<String> defaultUsedOutputPluginIDs;

    public List<String> getPluginIDsFilter() {
        return pluginIDsFilter;
    }

    /**
     * 此函数是为了测试的，正常情况下请不要使用。
     * 可能在不通知的情况下移除
     * @deprecated
     * */
    void setPluginIDsFilter(List<String> pluginIDsFilter) {
        this.pluginIDsFilter = pluginIDsFilter;
    }

    boolean pluginIDsFilterIsValid(){
        if(pluginIDsFilter != null){
            if(!pluginIDsFilter.isEmpty()){
                return true;
            }
        }
        return false;
    }

    public List<String> getDefaultUsedOutputPluginIDs() {
        return defaultUsedOutputPluginIDs;
    }

    /**
     * 此函数是为了测试的，正常情况下请不要使用。
     * 可能在不通知的情况下移除
     * @deprecated
     * */
    public void setDefaultUsedOutputPluginIDs(List<String> defaultUsedOutputPluginIDs) {
        this.defaultUsedOutputPluginIDs = defaultUsedOutputPluginIDs;
    }

    boolean defaultUsedOutputPluginIDsIsValid(){
        if(defaultUsedOutputPluginIDs != null){
            if(!defaultUsedOutputPluginIDs.isEmpty()){
                return true;
            }
        }
        return false;
    }

}
