/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.output.local;


import java.util.ArrayList;

/**此类仅仅为了测试，请不要使用。
 * 可能在不经通知的情况下删除*/
public class Main {
    static void main() {
        OutputLocalPlugin plugin = new OutputLocalPlugin();
        LocalPluginConfig pluginConfig = plugin.getConfig();
        LocalPluginConfig.Permissions WebFetchPermissions = new LocalPluginConfig.Permissions();
        ArrayList<String> Allow = new ArrayList<>();
        Allow.add("允许的域名");

        WebFetchPermissions.setAllow(Allow);
        ArrayList<String> Deny = new ArrayList<>();
        Deny.add("禁止的域名");
        WebFetchPermissions.setDeny(Deny);

        pluginConfig.setWebFetchPermissions(WebFetchPermissions);

        plugin.saveConfig();
    }
}
