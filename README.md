### springboot-log日志框架整合
#### 项目说明
该项目是springboot整合日志框架的demo工程，整合的日志框架包括log4j2、logback

#### 模块说明
- springboot-log4j2 => springboot整合log4j2日志框架
- sprongboot-logback => springboot整合logback日志框架
- springboot-trace => springboot整合log4j2结合MDC实现全链路traceId

#### 开发环境
- windows10 os
- maven
- jdk 1.8

#### Get Start[快速开始]
- git clone
```
git clone https://github.com/TiantianUpup/springboot-log.git
```
- 数据准备  
执行sql文件夹下的test.sql，执行之前先创建test数据库 
- 修改LOG_ERROR_HOME、LOG_INFO_HOME为你的本地日志保存地址
- 启动springboot-log4j2(端口号为8082)/springboot-logback(端口号为8080)
- 访问/api/test  GET
将看到不同级别的日志输出
- 访问/user/page POST  
RequestBody:
    ```
    {
        "userName":"ff"
    }
    ```
    将看到info级别的日志输出
    
#### 更新说明
- 2020/3/18 => 添加springboot-trace模块，log4j2结合MDC实现全链路traceId