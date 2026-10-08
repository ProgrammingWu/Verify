# 构建方法

## 准备VerifyAPI
VerifyAPI库是核心契约层。需要构建为本地的Maven仓库。供主程序和插件使用。

## 编译此库

在构建完VerifyAPI后，此项目就能编译了。

插件放在`plugins`文件夹内。这样能被此程序识别。

# 配置
> 配置完成后，需要重启主程序才能生效。

`config/plugin.json`文件，定义了怎么识别插件。

- pluginIDsFilter:启用的插件ID。配置上此字段后，将会只加载这些插件。
可以移除掉，移除掉后，会加载`plugins`文件夹内的所有插件。
- defaultUsedOutputPluginIDs：输出插件的调用顺序，如果不配置，将会按插件加载顺序调用（调用顺序不确定）。
建议保留此配置，并在新增输出插件时把插件ID添加到里面（未配置的插件不会使用）。
建议保留的原因时，`VerifyOutputLocal`作为本地的黑名单和白名单插件。拥有最优先最权威的的判决权。
它只要返回结果，其他插件就不需要调用了。

```
{
	"pluginIDsFilter":[
		"VerifyInputCC",
		"VerifyOutputLocal",
		"VerifyOutVirustotal"
	],
	"defaultUsedOutputPluginIDs":[
		"VerifyOutputLocal",
		"VerifyOutVirustotal"
	]
}
```

# 支持 claude code

需要VerifyInputCC,详见其README。



