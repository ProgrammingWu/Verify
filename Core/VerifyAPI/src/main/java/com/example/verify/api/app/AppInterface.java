/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.api.app;

import com.example.verify.api.output.WebFetchOutputInterface;
import org.pf4j.PluginManager;

/**
 * APP主程序实现的接口
 * */
public interface AppInterface {
    /**获取PluginManager*/
    PluginManager getPluginManager();

    /**正式发起WebFetch安全查询。
     * 将会调用Output插件。进行查询。
     * */
    WebFetchOutputInterface.PreWebFetchResult launchPreWebFetch(WebFetchOutputInterface.PreWebFetchParam Param);

    /**
     * 判断Host是否和pattern匹配。
     * 比如pattern为*.example.com。host为www.example.com。那么返回true
     * */
    boolean matchHost(String pattern,String host);
}
