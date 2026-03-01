package com.jayhello.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SLF4J + Logback 日志示例
 */
public class LogbackExample {
    
    private static final Logger logger = LoggerFactory.getLogger(LogbackExample.class);
    
    public void demonstrateLogs() {
        logger.trace("这是 TRACE 级别日志");
        logger.debug("这是 DEBUG 级别日志");
        logger.info("这是 INFO 级别日志");
        logger.warn("这是 WARN 级别日志");
        logger.error("这是 ERROR 级别日志");
    }
    
    public static void main(String[] args) {
        LogbackExample example = new LogbackExample();
        example.demonstrateLogs();
        
        // 带参数的日志
        String user = "张三";
        int age = 25;
        logger.info("用户信息: name={}, age={}", user, age);
        
        // 异常日志
        try {
            int result = 10 / 0;
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
    }
}
