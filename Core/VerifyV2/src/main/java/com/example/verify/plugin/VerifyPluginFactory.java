/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.plugin;

import com.example.verify.VerifyApplication;
import com.example.verify.api.app.PluginContext;
import org.pf4j.DefaultPluginFactory;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import java.lang.reflect.Constructor;

public class VerifyPluginFactory extends DefaultPluginFactory {
    @Override
    protected Plugin createInstance(Class<?> pluginClass, PluginWrapper pluginWrapper) {
        try {
            //=========初始化PluginContext============

            PluginContext ctx = new PluginContext(VerifyApplication.getVerifyApplication());

            Constructor<?> constructor = pluginClass.getConstructor(PluginContext.class);
            return (Plugin) constructor.newInstance(ctx);
        } catch (NoSuchMethodException e) {
            return createUsingNoParametersConstructor(pluginClass);
        } catch (Exception e) {
            throw new RuntimeException("创建插件失败: " + pluginClass.getName(), e);
        }
    }
}
