### log4j2异步配置
#### 异步配置步骤
- 添加依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <!-- 去掉springboot默认配置 -->
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
    <version>2.1.3.RELEASE</version>
</dependency>

<!-- log4j2 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
     <version>2.1.1.RELEASE</version>
</dependency>

<!--log4j2异步配置-->
<dependency>
   <groupId>com.lmax</groupId>
   <artifactId>disruptor</artifactId>
   <version>3.4.2</version>
</dependency>
```
- log4j2-spring.xml配置
```
<?xml version="1.0" encoding="UTF-8"?>

<Configuration >
    <properties>
        <property name="LOG_INFO_HOME">C://Users//hetiantian//Desktop//springboot-log//log4j2//info</property>
        <property name="LOG_ERROR_HOME">C://Users//hetiantian//Desktop//springboot-log//log4j2//error</property>
        <property name="PATTERN">%d [%t] %-5p [%c] - %m%n</property>
    </properties>

    <!-- 控制台输出 -->
    <Appenders>
        <Console name="CONSOLE" target="SYSTEM_OUT">
            <PatternLayout pattern="${PATTERN}"/>
        </Console>

        <!--RollingRandomAccessFile-->
        <RollingRandomAccessFile name="ERROR-LOG" filePattern="${LOG_ERROR_HOME}//%d.log">
            <PatternLayout pattern="${PATTERN}"/>

            <Policies>
                <TimeBasedTriggeringPolicy/>
            </Policies>
            <Filters>
                <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
        </RollingRandomAccessFile>

        <RollingRandomAccessFile name="INFO-LOG" filePattern="${LOG_INFO_HOME}//%d.log">
            <PatternLayout pattern="${PATTERN}"/>
            <Policies>
                <TimeBasedTriggeringPolicy />
            </Policies>
            <Filters>
                <!--只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
                <ThresholdFilter level="ERROR" onMatch="DENY" onMismatch="NEUTRAL"/>
                <ThresholdFilter level="WARN" onMatch="DENY" onMismatch="NEUTRAL"/>
                <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
        </RollingRandomAccessFile>
    </Appenders>


    <Loggers>
        <AsyncLogger name="com.h2t.study" additivity="false">
            <AppenderRef ref="ERROR-LOG"/>
            <AppenderRef ref="INFO-LOG"/>
        </AsyncLogger>

        <Root level="INFO">
            <!--<AppenderRef ref="RollingRandomAccessFile"/>-->
            <!--<AppenderRef ref="RollingRandomAccessFile2"/>-->
            <AppenderRef ref="CONSOLE"/>
        </Root>
    </Loggers>


</Configuration>
```

- 添加log4j2.component.properties配置文件【必须】
配置文件中添加如下内容
```
Log4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
```

