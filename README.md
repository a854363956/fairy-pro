# fairy-pro

##### 采用的技术集成

1. 项目管理 https://zube.io/a854363956/fairy-pro/w/development/kanban  
2. 持续集成 https://circleci.com/dashboard 
3. projectlombok 
4. spring-boot 
5. spring-data-jpa

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




##### 用户管理 

1. /api/user/login  用户登入接口
2. /api/user/logout 用户登出接口 
3. /api/user/addUser 添加用户 




