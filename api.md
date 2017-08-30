### 响应格式
```json
{
    "code": 0,
    "message": "",
    "data": 
}
```
#### 1. data 说明
data 对应的数据为数组或字典，如果响应失败则不返回该字段。
#### 2. code 说明
code | 说明
-- | --
-1 | 由于服务器原因，无法完成指定请求
0 | 操作成功
1 | 非法参数
4 | 资源不存在
5 | 资源已存在
6 | 操作了错误的资源，比如你要删除其他用户的 spittle
999 | 需要鉴权的接口，如果没有 `Authorization` 头，或者信息过期，则返回 6

### 用户鉴权
api | 参数 | 方法 | 说明
-- | -- | -- | --
/auth | username, password | POST | 用户登录
/auth/:username |   | DELETE | 退出登录

### Spitter
api | 参数 | 方法 | 说明
-- | -- | -- | --
/spitter/:param | param 可以是 用户名或者 id | GET | 获取用户信息
/spitter | username, password | POST | 注册新用户
/spitter/:username/disable |  | PATCH | 禁用用户
/spitter/:username/enable | | PATCH | 启用用户
/spitter/:username/atatar | avatar | POST | 修改用户头像

### Spittle
api | 参数 | 方法 | 说明
-- | -- | -- | --
/spittle | | GET | 获取 spittle 列表
/spittle/:username | | GET | 获取由用户名为 username 的用户发布的 spittle
/spittle | text, attachment(可选，代表附件数组的json字符串), enabled(可选) | POST | 发布spittle
/spittle/:id | | DELETE | 删除指定的 spittle
/spittle/:id/comment | | GET | 获取制定 spittle 的评论
/spittle/:id/comment | text(评论内容) | POST | 发表评论
/spittle/:id/comment/:commentId/ | | DELETE | 删除评论

### 上传下载
api | 说明
-- | --
/upload | 上传文件
/download/:filetype/:filename | filetype: 1代表图片，2代表音频，3代表视频

## 其他说明
* 用户名首尾不能有空格，不能为纯数字。