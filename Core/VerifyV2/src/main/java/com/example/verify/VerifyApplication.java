/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify;

import com.example.verify.api.app.AppInterface;
import com.example.verify.api.output.SafetyLevel;
import com.example.verify.api.output.WebFetchOutputInterface;
import com.example.verify.plugin.VerifyPluginManager;
import jakarta.annotation.PostConstruct;
import org.pf4j.PluginManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class VerifyApplication implements AppInterface {
    private static VerifyApplication verifyApplication = null;
    private PluginManager pm = null;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext=
                SpringApplication.run(VerifyApplication.class, args);

        System.out.println("获取到的VerifyApplication"+verifyApplication);

        System.out.println("插件输出开始");
        java.util.List<org.pf4j.PluginWrapper> Plugins = verifyApplication.getPluginManager().getPlugins();
        for (org.pf4j.PluginWrapper Plugin : Plugins) {
            System.out.println("已加载插件ID："+Plugin.getPluginId());
        }
        System.out.println("插件输出结束");

        //============创建测试内容===============
        /*
        VerifyPluginManager pm = verifyApplication.getPluginManagerTemplates(VerifyPluginManager.class);
        VerifyPluginConfig TempPluginConfig = pm.getPluginConfig();
        List<String> TempPluginIDs = new ArrayList<String>();
        TempPluginIDs.add("VerifyInputCC");
        TempPluginConfig.setPluginIDsFilter(TempPluginIDs);
        pm.saveConfig();

         */
    }

    public VerifyApplication(){

    }

    @PostConstruct
    public void init(){
        if(verifyApplication!=null){
            throw new IllegalStateException("内部状态错误:VerifyApplication的init被调用了两次");
        }
        verifyApplication = this;
        VerifyPluginManager temppm = new VerifyPluginManager(java.nio.file.Paths.get("plugins"));
        temppm.loadPluginsByConfig();
        pm = temppm;
        pm.startPlugins();//启动插件
    }


    /**因为使用的部分框架，是自己管理对象的。所以这个是给这些对象用的。*/
    public static VerifyApplication getVerifyApplication() {
        if(verifyApplication==null){
            throw new IllegalStateException("内部状态错误:verifyApplication没有创建。");
        }
        return verifyApplication;
    }

    public PluginManager getPluginManager() {
        if(pm==null){
            throw new IllegalStateException("内部状态错误:PluginManager没有创建。");
        }
        return pm;
    }

    public  <T extends PluginManager> T getPluginManagerTemplates(Class<T> type) {
        if(pm==null){
            throw new IllegalStateException("内部状态错误:PluginManager没有创建。");
        }
        if(type.isInstance(pm)){
            return type.cast(pm);
        }
        throw new IllegalStateException("getPluginManagerTemplates类型不匹配");
    }

    @Override
    public WebFetchOutputInterface.PreWebFetchResult launchPreWebFetch(WebFetchOutputInterface.PreWebFetchParam preWebFetchParam) {
        if(preWebFetchParam==null){
            throw new IllegalStateException("错误，设计上preWebFetchParam不应为Null");
        }

        VerifyPluginManager pluginManager = verifyApplication.getPluginManagerTemplates(VerifyPluginManager.class);
        List<String> outputPluginIDs = pluginManager.getOutputPluginIDs();
        List<WebFetchOutputInterface>  OutputInterfaces = new ArrayList<WebFetchOutputInterface>();
        for (String outputPluginID : outputPluginIDs) {
            List<WebFetchOutputInterface> TempOutputInterfaces= pluginManager.getExtensions(WebFetchOutputInterface.class,outputPluginID);
            OutputInterfaces.addAll(TempOutputInterfaces);
        }

        if(OutputInterfaces.isEmpty()){
            //throw new IllegalStateException("没有从插件中找到任何WebFetchOutputInterface扩展点");
            return new WebFetchOutputInterface.PreWebFetchResult(SafetyLevel.Unknown);
        }

        List<WebFetchOutputInterface.PreWebFetchResult> resultList =
                new ArrayList<WebFetchOutputInterface.PreWebFetchResult>();

        for(WebFetchOutputInterface webFetchOutputInterface:OutputInterfaces){
            if(webFetchOutputInterface==null){
                continue;
            }
            WebFetchOutputInterface.PreWebFetchResult Result =webFetchOutputInterface.preWebFetch(preWebFetchParam);
            if(Result.getSafetyLevel()==SafetyLevel.AbsolutelySafe){
                //在一个插件强制安全时，直接返回安全
                return Result;
            }
            if(Result.getSafetyLevel()==SafetyLevel.unsafe){
                //在一个插件报危险时，直接返回危险
                return Result;
            }
            //因为直到最后，也不确定有没有AbsolutelySafe或者unsafe。所以先储存结果。
            resultList.add(Result);
        }

        for(WebFetchOutputInterface.PreWebFetchResult result : resultList){
            if(result.getSafetyLevel()==SafetyLevel.Safe){
                //有safe时，就直接返回了。这样就忽略了所有Unknown
                return result;
            }
        }

        //当没有safe时，肯定都是Unknown。所以返回最后一个吧
        return resultList.getLast();
    }

    @Override
    public boolean matchHost(String pattern,String host){
        org.springframework.util.AntPathMatcher matcher = new org.springframework.util.AntPathMatcher();
        matcher.setCaseSensitive(false);
        return matcher.match(pattern,host);
    }

}
