# 大学生自我规划平台接口文档-V1.0

## 1. 用户相关接口

### 1.1 注册

#### 1.1.1 基本信息

> 请求路径：/users/register
>
> 请求方式：POST
>
> 接口描述：该接口用于注册新用户



#### 1.1.2 请求参数

请求参数说明：

| 参数名称 | 说明   | 类型   | 是否必须 | 备注           |
| -------- | ------ | ------ | -------- | -------------- |
| username | 用户名 | string | 是       | 5~16位非空字符 |
| password | 密码   | string | 是       | 5~16位非空字符 |
| email    | 邮箱   | string | 是       | 格式校验       |
| phone    | 手机   | string | 是       | 格式校验       |
| name     | 姓名   | string | 否       | 姓名           |

⚠️ 说明：

- role 固定由系统设置，默认 0=学生。
- create_time、last_login 由后端生成，不允许前端传。

请求数据样例：

```json
{
  "username": "billy",
  "password": "abc123",
  "email": "billy@example.com",
  "phone": "13800001111",
  "name": "Billy"
}
```



#### 1.1.3 响应数据

响应数据类型：application/json

响应参数说明：

| 名称    | 类型   | 是否必须 | 默认值 | 备注                  | 其他信息 |
| ------- | ------ | -------- | ------ | --------------------- | -------- |
| code    | number | 必须     |        | 响应码, 0-成功,1-失败 |          |
| message | string | 非必须   |        | 提示信息              |          |
| data    | object | 非必须   |        | 返回的数据            |          |

响应数据案例（成功）：

```json
{
  "code": 0,
  "message": "注册成功",
  "data": {
    "id": 101,
    "username": "billy",
    "email": "billy@example.com",
    "phone": "13800001111",
    "name": "Billy",
    "role": 0,
    "create_time": "2025-10-01T15:30:00Z",
    "last_login": null
  }
}
```

响应数据案例（失败）：

```json
{
  "code": 1001,
  "message": "用户名已存在",
  "data": null
}
```



### 1.2 登录

#### 1.2.1 基本信息

> 请求路径：/users/login
>
> 请求方式：POST
>
> 接口描述：该接口用于用户登录，登录成功后返回 token（JWT），需要前端在后续请求中携带。



#### 1.2.2 请求参数

请求参数说明：

| **参数名称** | **说明** | **类型** | **是否必须** | **备注**       |
| ------------ | -------- | -------- | ------------ | -------------- |
| username     | 用户名   | string   | 是           | 5~16位非空字符 |
| password     | 密码     | string   | 是           | 5~16位非空字符 |

请求数据样例：

```json
{
  "username": "billy",
  "password": "abc123"
}
```



#### 1.2.3 响应数据

响应数据类型：application/json

响应参数说明：

| **名称** | **类型** | **是否必须** | **默认值**                                  | **备注** | **其他信息** |
| -------- | -------- | ------------ | ------------------------------------------- | -------- | ------------ |
| code     | number   | 必须         | 响应码, 0-成功,1-失败                       |          |              |
| message  | string   | 非必须       | 提示信息                                    |          |              |
| data     | string   | 非必须       | 登录成功返回的 token（JWT），有效期 12 小时 |          |              |

响应数据案例（成功）：

```json
{
  "code": 0,
  "message": "登录成功",
  "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

响应数据案例（失败）：

```json
{
  "code": 1002,
  "message": "用户名错误",
  "data": null
}
```

响应数据案例（失败）：

```json
{
  "code": 1003,
  "message": "密码错误",
  "data": null
}
```

⚠️ 说明：

- 登录成功后，后端会生成 **JWT token** 并返回，同时将 token 存储到 Redis（有效期 12 小时）。
- 前端在后续请求时，需要在请求头中携带：`Authorization: Bearer <token>`
