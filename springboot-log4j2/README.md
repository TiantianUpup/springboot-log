### 配置文件log4j2-spring.xml
#### 异步配置方式
- 添加依赖
```
 <dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.4.2</version>
 </dependency>
```
- log4j2.xml配置
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
