spittr 该名字来源于《Spring 实战》一书。

项目中接口设计使用 restful 风格，使用 token 鉴权，实现方案为 [jwt](https://jwt.io/introduction/)、redis 相结合，其中 jwt 使用 [jjwt](https://github.com/jwtk/jjwt)，通过 interceptor 拦截需要鉴权的方法（通过注解自定义的 Authorization 注解标注）。

API 接口文档在 [这里](./api.md)。