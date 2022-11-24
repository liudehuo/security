# security
打怪升级


## 认证：
### 登录：
#### 1、自定义登录接口
        调用ProviderManager的方法进行认证，如果认证通过生成jwt，把用户信息存入redis中
#### 2、自定义UserDetailService
        在这个实现类中去查询数据库
### 校验：
### 1、定义jwt认证过滤器
        获取token
        解析token获取userId
        从redis中获取用户信息
        存入SecurityContextHolder

