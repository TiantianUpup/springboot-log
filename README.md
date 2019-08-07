### 依赖
不需要额外添加与`logback`、`slf4j`相关的依赖，SpringBoot框架已内置

### 配置文件logback-spring.xml
分类收集日志的核心就是日志的配置文件了，logback框架会默认加载`classpath`下命名为`logback-spring`或`logback`的配置文件
```
<?xml version="1.0" encoding="utf-8"?>
<configuration>
    <property resource="logback.properties"/>
    <appender name="consoleLog" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>[%d{yyyy-MM-dd' 'HH:mm:ss.sss}] [%C] [%t] [%L] [%-5p] %m%n</pattern>
        </layout>
    </appender>
    <!--获取比info级别高(包括info级别)但除error级别的日志-->
    <appender name="fileInfoLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>DENY</onMatch>
            <onMismatch>ACCEPT</onMismatch>
        </filter>
        <encoder>
            <pattern>[%d{yyyy-MM-dd' 'HH:mm:ss.sss}] [%C] [%t] [%L] [%-5p] %m%n</pattern>
        </encoder>

        <!--滚动策略-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--路径-->
            <fileNamePattern>${LOG_INFO_HOME}//%d.log</fileNamePattern>
        </rollingPolicy>
    </appender>
    <appender name="fileErrorLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level>
        </filter>
        <encoder>
            <pattern>[%d{yyyy-MM-dd' 'HH:mm:ss.sss}] [%C] [%t] [%L] [%-5p] %m%n</pattern>
        </encoder>
        <!--滚动策略-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--路径-->
            <fileNamePattern>${LOG_ERROR_HOME}//%d.log</fileNamePattern>
        </rollingPolicy>
    </appender>
    <root level="info">
        <appender-ref ref="consoleLog" />
        <appender-ref ref="fileInfoLog" />
        <appender-ref ref="fileErrorLog" />
    </root>
</configuration>
```
部分标签说明
- `<root>`标签，必填标签，用来指定最基础的日志输出级别
  - <appender-ref>标签，添加`append`
- `<append>`标签，通过使用该标签指定日志的收集策略
  - `name`属性指定appender 命名
  - `class`属性指定输出策略，通常有两种，控制台输出和文件输出，文件输出就是将日志进行一个持久化
- `<filter>`标签，通过使用该标签指定过滤策略
  - `<level>`标签指定过滤的类型
- `<encoder>`标签，使用该标签下的`<pattern>`标签指定日志输出格式
- `<rollingPolicy>`标签指定收集策略，比如基于时间进行收集
  - ` <fileNamePattern>`标签指定生成日志保存地址

<hr>
![](https://upload-images.jianshu.io/upload_images/9358011-fbb7ca357fdfdf43.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
<br>
<br>

