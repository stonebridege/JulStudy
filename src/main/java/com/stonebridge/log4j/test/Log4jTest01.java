package com.stonebridge.log4j.test;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

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


    public void test02() {

        /*
        配置文件的使用
        1.观察源码BasicConfigurator.configure();加载Logger、Appender、Layout
          可以得到两条信息：
          （1）创建了根节点的对象，Logger root = Logger.getRootLogger();
          （2）根节点添加了ConsoleAppender对象（表示默认日志默认打印到控制台，自定义的格式化输出）
        2.不使用BasicConfigurator.configure();加载Logger、Appender、Layout
          通过我们对于以上第一点的分析
          我们的配置文件需要提供Logger、Appender、Layout这3个组件信息（通过配置来代替以上的代码）

            2.我们这一次，不使用BasicConfigurator.configure();
                使用自定义的配置文件来实现功能
                通过我们对于以上第一点的分析
                我们的配置文件需要提供Logger、Appender、Layout这3个组件信息（通过配置来代替以上的代码）
                分析：
                Logger logger = Logger.getLogger(Log4jTest01.class);

                进入到getLogger方法，会看到代码：
                LogManager.getLogger(clazz.getName());
                LogManager：日志管理器

                点击LogManager，看看这个日志管理器中都实现了什么
                看到很多常量信息，他们代表的就是不同形式（后缀名不同）的配置文件
                我们最常使用到的肯定是log4j.properties属性文件（语法简单，使用方便）

                问题：log4j.properties的加载时机
                继续观察LogManager，找到其中的静态代码块static
                在static代码块中，我们找到
                Loader.getResource("log4j.properties");
                这行代码给我们最大的一个提示信息就是
                系统默认要从当前的类路径下找到log4j.properties
                对于我们当前的项目是maven工程，那么理应在resources路径下去找

                加载完毕后我们来观察配置文件是如何读取的？
                继续观察LogManager
                找到
                OptionConverter.selectAndConfigure(url, configuratorClassName, getLoggerRepository());
                作为属性文件的加载，执行相应的properties配置对象：configurator = new PropertyConfigurator();

                进入到PropertyConfigurator类中，观察到里面的常量信息
                这些常量信息就是我们在properties属性文件中的各种属性配置项
                其中，我们看到了如下两项信息，这两项信息是必须要进行配置的
                static final String ROOT_LOGGER_PREFIX = "log4j.rootLogger";
                static final String APPENDER_PREFIX = "log4j.appender.";

                通过代码：
                String prefix = "log4j.appender." + appenderName;
                我们需要自定义一个appendername，我们起名叫做console
                （起名字也需要见名知意，console那么我们到时候的配置应该配置的就是控制台输出）
                log4j.appender.console
                取值就是log4j中为我们提供的appender类
                例如：
                    log4j.appender.console=org.apache.log4j.ConsoleAppender


                在appender输出的过程中，还可以同时指定输出的格式
                通过代码：
                String layoutPrefix = prefix + ".layout";
                配置：
                log4j.appender.console.layout=org.apache.log4j.SimpleLayout

                通过log4j.rootLogger继续在类中搜索
                找到void configureRootCategory方法
                在这个方法中执行了this.parseCategory方法
                观察该方法：
                找打代码StringTokenizer st = new StringTokenizer(value, ",");
                表示要以逗号的方式来切割字符串，证明了log4j.rootLogger的取值，其中可以有多个值，使用逗号进行分隔

                通过代码：
                String levelStr = st.nextToken();
                表示切割后的第一个值是日志的级别

                通过代码：
                while(st.hasMoreTokens())
                表示接下来的值，是可以通过while循环遍历得到的
                第2~第n个值，就是我们配置的其他的信息，这个信息就是appenderName
                证明了我们配置的方式
                log4j.rootLogger=日志级别,appenderName1,appenderName2,appenderName3....
                表示可以同时在根节点上配置多个日志输出的途径

                通过我们自己的配置文件，就可以将原有的加载代码去除掉了
         */

        //BasicConfigurator.configure();

        Logger logger = Logger.getLogger(Log4jTest01.class);

        logger.fatal("fatal信息");
        logger.error("error信息");
        logger.warn("warn信息");
        logger.info("info信息");
        logger.debug("debug信息");
        logger.trace("trace信息");

    }

    public void test03() {

        /*

            通过Logger中的开关
            打开日志输出的详细信息
            查看LogManager类中的方法
            getLoggerRepository()
            找到代码LogLog.debug(msg, ex);表示LogLog会使用debug级别的输出为我们展现日志输出详细信息
            Logger是记录系统的日志，那么LogLog是用来记录Logger的日志

            进入到LogLog.debug(msg, ex);方法中
            通过代码：if (debugEnabled && !quietMode) {
            观察到if判断中的这两个开关都必须开启才行
            !quietMode是已经启动的状态，不需要我们去管
            debugEnabled默认是关闭的
            所以我们只需要设置debugEnabled为true就可以了
         */

        LogLog.setInternalDebugging(true);
        Logger logger = Logger.getLogger(Log4jTest01.class);
        logger.fatal("fatal信息");
        logger.error("error信息");
        logger.warn("warn信息");
        logger.info("info信息");
        logger.debug("debug信息");
        logger.trace("trace信息");
    }
}
