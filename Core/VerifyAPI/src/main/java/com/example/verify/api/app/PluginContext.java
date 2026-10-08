/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.api.app;

public class PluginContext {
    private AppInterface appSystem;

    /**
     * 设计上，不允许inAppSystem为null。为null直接抛出错误（如果是C++，我就直接断言了）
     * */
    public PluginContext(AppInterface inAppSystem) {
        if(inAppSystem==null){
            throw new IllegalStateException("appSystem is null");
        }
        this.appSystem = inAppSystem;
    }

    /**
     * 获取AppSystem
     * 设计上，不允许inAppSystem为null。为null直接抛出错误（如果是C++，我就直接断言了）
     * */
    public AppInterface getAppSystem() {
        if(appSystem==null){
            throw new IllegalStateException("appSystem is null");
        }
        return appSystem;
    }

}
