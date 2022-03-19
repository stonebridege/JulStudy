package stonebridge;

import org.junit.Test;

import java.io.IOException;
import java.util.logging.*;

public class JULTest {
    @Test
    public void test01() {
        Logger logger = Logger.getLogger("stonebridge.JULTest");
    /*
     对于日志的输出，有两种方式
     第一种方式：
        直接调用日志级别相关的方法，方法中传递日志输出信息
        假设现在我们要输出info级别的日志信息
     */
        logger.info("输出info信息1");
    /*
     第二种方式：
     调用通用的log方法，然后在里面通过Level类型来定义日志的级别参数，以及搭配日志输出信息的参数
     */
        logger.log(Level.INFO, "输出info信息2");
    /*
     第三种方式：日志信息采用字符串的拼接输出
     输出学生信息
        姓名
        年龄
     */
        String name = "zs";
        int age = 23;
        logger.log(Level.INFO, "学生的姓名为：" + name + "；年龄为：" + age);
    /*
    第四种方式：占位符的方式来进行操作输出复杂日志
        对于输出消息中，字符串的拼接弊端很多
        1.麻烦
        2.程序效率低
        3.可读性不强
        4.维护成本高
        我们应该使用动态生成数据的方式，生产日志
     */
        logger.log(Level.INFO, "学生的姓名：{0},年龄:{1}", new Object[]{name, age});
    }

    @Test
    public void test02() {

        /*

            日志的级别（通过源码查看，非常简单）

              SEVERE : 错误 --- 最高级的日志级别
              WARNING : 警告
              INFO : （默认级别）消息
              CONFIG : 配置
              FINE : 详细信息（少）
              FINER : 详细信息（中）
              FINEST : 详细信息 （多） --- 最低级的日志级别

            两个特殊的级别
               OFF 可用来关闭日志记录
               ALL 启用所有消息的日志记录

            对于日志的级别，我们重点关注的是new对象的时候的第二个参数
            是一个数值
            OFF Integer.MAX_VALUE 整型最大值

            SEVERE 1000
            WARNING 900

            ...
            ...

            FINEST 300


            ALL Integer.MIN_VALUE 整型最小值

            这个数值的意义在于，如果我们设置的日志的级别是INFO -- 800
            那么最终展现的日志信息，必须是数值大于800的所有的日志信息
            最终展现的就是
            SEVERE
            WARNING
            INFO


         */

        Logger logger = Logger.getLogger("stonebridge.JULTest");

        /*

            通过打印结果，我们看到了仅仅只是输出了info级别以及比info级别高的日志信息
            比info级别低的日志信息没有输出出来
            证明了info级别的日志信息，它是系统默认的日志级别
            在默认日志级别info的基础上，打印比它级别高的信息

         */

        /*

            如果仅仅只是通过以下形式来设置日志级别


         */
//        logger.setLevel(Level.FINE);

        logger.severe("severe信息");
        logger.warning("warning信息");
        logger.info("info信息");
        logger.config("config信息");
        logger.fine("fine信息");
        logger.finer("finer信息");
        logger.finest("finest信息");
    }

    @Test
    public void test03() {
        /*
            自定义日志的级别
         */
        //1.创建日志记录器对象
        Logger logger = Logger.getLogger("stonebridge.JULTest");
        //2.参数设置为false将默认的日志打印方式关闭掉。如果不进行设置，默认走父处理器的方式去进行操作
        logger.setUseParentHandlers(false);
        //3.处理器Handler。在此我们使用的是控制台日志处理器，取得处理器对象。默认就会把日志打印在控制台。
        ConsoleHandler handler = new ConsoleHandler();
        //4.创建日志格式化组件对象
        SimpleFormatter formatter = new SimpleFormatter();
        //5.在处理器中设置输出格式
        handler.setFormatter(formatter);
        //6.在记录器中添加处理器
        logger.addHandler(handler);

        //7.设置日志的打印级别
        //此处必须将日志记录器和处理器的设置为相同的级别，才会达到日志显示相应级别的效果
        //logger.setLevel(Level.CONFIG);
        //handler.setLevel(Level.CONFIG);

        logger.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);

        logger.severe("severe信息");
        logger.warning("warning信息");
        logger.info("info信息");
        logger.config("config信息");
        logger.fine("fine信息");
        logger.finer("finer信息");
        logger.finest("finest信息");
    }

    @Test
    public void test04() throws IOException {
        /*
            将日志输出到具体的磁盘文件中
            这样做相当于是做了日志的持久化操作
         */
        Logger logger = Logger.getLogger("stonebridge.JULTest");
        logger.setUseParentHandlers(false);
        SimpleFormatter formatter = new SimpleFormatter();

        //文件日志处理器
        FileHandler fileHandler = new FileHandler("D:\\logs\\myLogTest.log");
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);

        //也可以同时在控制台和文件中进行打印
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        logger.addHandler(consoleHandler); //可以在记录器中同时添加多个处理器

        logger.setLevel(Level.ALL);
        fileHandler.setLevel(Level.CONFIG);
        consoleHandler.setLevel(Level.FINE);

        logger.severe("severe信息");
        logger.warning("warning信息");
        logger.info("info信息");
        logger.config("config信息");
        logger.fine("fine信息");
        logger.finer("finer信息");
        logger.finest("finest信息");
        /*

            总结：
                用户使用Logger来进行日志的记录，Logger可以持有多个处理器Handler
                （日志的记录使用的是Logger，日志的输出使用的是Handler）
                添加了哪些handler对象，就相当于需要根据所添加的handler
                将日志输出到指定的位置上，例如控制台、文件..

         */
    }

@Test
public void test05() {

    /*

     Logger之间的父子关系。JUL中Logger之间是存在"父子"关系的。值得注意的是，这种父子关系不是我们普遍认为的类之间的继承关系
     关系是通过树状结构存储的。

    JUL在初始化时会创建一个顶层RootLogger作为所有Logger的父Logger
    查看源码：
        owner.rootLogger = owner.new RootLogger();
        RootLogger是LogManager的内部类
        java.util.logging.LogManager$RootLogger
        默认的名称为 空串
    以上的RootLogger对象作为树状结构的根节点存在的
    将来自定义的父子关系通过路径来进行关联
    父子关系，同时也是节点之间的挂载关系
    owner.addLogger(owner.rootLogger);
    LoggerContext cx = getUserContext(); //LoggerContext一种用来保存节点的Map关系
    private LogNode               node; //节点

     */
    /*
        从下面创建的两个logger对象看来
        我们可以认为logger1是logger2的父亲
     */
    //父亲是RootLogger，名称默认是一个空的字符串；RootLogger可以被称之为所有logger对象的顶层logger
    Logger logger1 = Logger.getLogger("stonebridge");
    Logger logger2 = Logger.getLogger("stonebridge.JULTest");
    System.out.println(logger2.getParent() == logger1); //true

    System.out.println("logger1的父Logger引用为:" + logger1.getParent() + "; 名称为" + logger1.getName() + "; 父亲的名称为" + logger1.getParent().getName());
    System.out.println("logger2的父Logger引用为:" + logger2.getParent() + "; 名称为" + logger2.getName() + "; 父亲的名称为" + logger2.getParent().getName());
    /*
        父亲所做的设置，也能够同时作用于儿子
        对logger1做日志打印相关的设置，然后我们使用logger2进行日志的打印
     */
    //父亲做设置
    logger1.setUseParentHandlers(false);
    ConsoleHandler handler = new ConsoleHandler();
    SimpleFormatter formatter = new SimpleFormatter();
    handler.setFormatter(formatter);
    logger1.addHandler(handler);
    handler.setLevel(Level.ALL);
    logger1.setLevel(Level.ALL);

    //儿子做打印
    logger2.severe("severe信息");
    logger2.warning("warning信息");
    logger2.info("info信息");
    logger2.config("config信息");
    logger2.fine("fine信息");
    logger2.finer("finer信息");
    logger2.finest("finest信息");
}
}
