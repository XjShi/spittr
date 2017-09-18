## 概览
spittr 该名字来源于《Spring 实战》一书，接口设计使用 restful 风格。

该项目使用的技术：

* Spring
* Spring MVC
* MyBatis 3.4
* Spring-test
* JUnit
* Redis
* MySql

## 配置
1. 数据库脚本：schema.sql 。
2. 根据需要更改 application.properties ：

	```
	# MySql 配置
	database.driver=com.mysql.jdbc.Driver
	database.username={mysql username}
	database.password={mysql password}
	database.url=jdbc:mysql://localhost:3306/spittr?useUnicode=true&characterEncoding=utf8
	# 上传文件配置
	uploadfile.path={upload file directory}
	```

	uploadfile.path 为上传文件的文件目录，该项需要配置为绝对路径。

## 部分实现说明
1. 用户鉴权没有通过 Spring-Security 实现，而是通过 interceptor 和自定义的 `Authorization` 注解实现。实现参考 `com.spittr.annotation.Authorization` 和 `com.spittr.interceptor.AuthorizationInterceptor` 。token 生成方案为 [jwt](https://jwt.io/introduction/)，jwt 的具体实现使用 [jjwt](https://github.com/jwtk/jjwt)。