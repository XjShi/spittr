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
* JWT

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

## 联系我
<shixj.cs@foxmail.com>
