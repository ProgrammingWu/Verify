# 构建方法
## 准备VerifyAPI
VerifyAPI库是核心契约层。需要构建为本地的Maven仓库。供主程序和插件使用。

## 编译此库

在构建完VerifyAPI后，此项目就能编译了。

构建出的jar，就是插件了，自己无法运行。放到Verify的plugins文件夹，就能让Verify用此插件的功能了

# 配置
> 配置完成后，需要重启主程序才能生效。

> 警告：此配置拥有绝对的权威。配置上的域名都不会经过其他插件的检查。直接一票通过或一票否决。
请小心配置。

在主程序的`config/OutputLocalPlugin/config.json`文件内，配置域名黑名单和白名单。如下：

```
{
  "webFetchPermissions" : {
    "allow" : [ "*.example.com" ],
    "deny" : [ "*.example.com" ]
  }
}
```

allow字段是白名单。deny字段是黑名单。

黑名单将优先于白名单。也就是在黑名单和白名单同时配置相同的规则。那么将应用黑名单的规则。阻止掉域名。

支持通配符*




