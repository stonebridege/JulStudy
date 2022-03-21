package com.stonebridge.log4j.test;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Log4jTest01 {
    public void test01() {
   /*

            Log4j入门案例
                注意加载初始化信息：BasicConfigurator.configure();

            日志级别说明：
                Log4j提供了8个级别的日志输出，分别为
                ALL 最低等级 用于打开所有级别的日志记录
                TRACE 程序推进下的追踪信息，这个追踪信息的日志级别非常低，一般情况下是不会使用的
                DEBUG 指出细粒度信息事件对调试应用程序是非常有帮助的，主要是配合开发，在开发过程中打印一些重要的运行信息
                INFO 消息的粗粒度级别运行信息
                WARN 表示警告，程序在运行过程中会出现的有可能会发生的隐形的错误
                        注意，有些信息不是错误，但是这个级别的输出目的就是为了给程序员以提示
                ERROR 系统的错误信息，发生的错误不影响系统的运行
                        一般情况下，如果不想输出太多的日志，则使用该级别即可
                FATAL 表示严重错误，它是那种一旦发生系统就不可能继续运行的严重错误
                        如果这种级别的错误出现了，表示程序可以停止运行了
                OFF 最高等级的级别，用户关闭所有的日志记录
                其中debug是我们在没有进行设置的情况下，默认的日志输出级别

         */

        //加载初始化配置
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(Log4jTest01.class);
        logger.fatal("fatal信息");
        logger.error("error信息");
        logger.warn("warn信息");
        logger.info("info信息");
        logger.debug("debug信息");
        logger.trace("trace信息");
    }
}
