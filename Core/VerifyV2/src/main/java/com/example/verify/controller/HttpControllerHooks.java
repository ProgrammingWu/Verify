/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.controller;

import com.example.verify.VerifyApplication;
import com.example.verify.api.input.WebFetchInputInterface;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/hooks")
class HttpControllerHooks {
    @PostMapping("pre-web-fetch")
    public String PreWebFetch(@RequestHeader("InputPluginID") String inputPluginID, @RequestBody String body) {
        //System.out.println(body);
        org.pf4j.PluginManager tempManager =  VerifyApplication.getVerifyApplication().getPluginManager();

        java.util.List<WebFetchInputInterface> inputExtensions =
                tempManager.getExtensions(WebFetchInputInterface.class,inputPluginID);

        if(inputExtensions.isEmpty()){
            System.err.println("错误，没有找到对应的插件ID。ID为："+inputPluginID);
            return"";
        }
        if(inputExtensions.size()>1){
            throw new IllegalStateException("每个插件对应的Extensions实现，应该只有一个");
        }
        /*
        for (WebFetchInputInterface inputExtension : inputExtensions) {
            inputExtension.preWebFetch(body);
        }*/
        WebFetchInputInterface firstInputExtension = inputExtensions.getFirst();//.preWebFetch(body);
        if(firstInputExtension==null){
            throw new IllegalStateException("第一个WebFetchInputInterface为nullptr");
        }
        return firstInputExtension.preWebFetch(body);
    }
}
