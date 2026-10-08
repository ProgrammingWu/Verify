# 使用方法
## 准备VerifyAPI
VerifyAPI库是核心契约层。需要构建为本地的Maven仓库。供主程序和插件使用。

## 编译此库

在构建完VerifyAPI后，此项目就能编译了。

构建出的jar，就是插件了，自己无法运行。放到Verify的plugins文件夹，就能让Verify用此插件的功能了


# claude code钩子配置：

在settings.local.json里，添加hooks。如下：

必须配置，否则此插件无法拦截Claude的WebFetch

```
{
  "hooks": {
    "PreToolUse": [
      {
        "matcher": "WebFetch",
        "hooks": [
          {
            "type": "http",
            "url": "http://localhost:8080/hooks/pre-web-fetch",
            "timeout": 30,
            "headers": {
              "Authorization": "Bearer $MY_TOKEN",
              "InputPluginID":"VerifyInputCC"
            },
            "allowedEnvVars": ["MY_TOKEN"]
          }
        ]
      }
    ]
  },
  "skipWebFetchPreflight": true
}

```