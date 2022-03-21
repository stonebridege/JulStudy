package com.stonebridge.log4j.test;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Log4jTest01 {
    public void test01() {
        //加载初始化配置
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(Log4jTest01.class);
        logger.info("info信息");
    }
}
