# fairy-pro

##### 采用的技术集成

1. 项目管理 https://zube.io/a854363956/fairy-pro/w/development/kanban  
2. 持续集成 https://circleci.com/dashboard 
3. projectlombok 
4. spring-boot 
5. spring-data-jpa
6. gradle 版本 5+
7. java 1.8 
8. mysql 8


#### 项目结构描述 

```bash
.
+-- _src/main/java  项目代码
+-- _src/main/resources 项目配置文件
+-- _src/test/java  单元测试文件
+-- _sqlscript 关于数据库的表结构设计文件,以及数据库dump
+-- build.gradle gradle构建配置文件
+-- gradlew gradle 构建配置文件
+-- gradlew.bat gradle构建配置文件
+-- LICENSE 许可
+-- README.md 项目说明文件
+-- settings.gradle gradle构建配置文件
```

##### 统一返回对象

com.fairy.models.dto.ResponseDto  

|字段名称  | 字段类型    | 备注 
|-----   |-----     |----
|status  |int       | 状态码
|message |string    | 返回的消息信息
|data    |any       | 返回的数据对象

状态码:  

|状态码     | 说明 
|-----   |----
|200     |数据请求成功
|500     |服务处理消息报错,错误信息为message中返回的信息

##### 统一的请求对象

com.fairy.models.dto.RequestDto

|字段名称  | 字段类型    | 备注 
|-----   |-----     |----
|token   |string    | 当前登入的令牌
|data    |any       | 返回的数据对象

> 通过token来校验当前数据是否是登入状态,在调用/api/user/login接口,如果登入成功则返回一个64位的token

##### 用户管理 

1. /api/user/login  用户登入接口
2. /api/user/logout 用户登出接口 
3. /api/user/addUser 添加用户 




