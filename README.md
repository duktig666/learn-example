# learn-example
## 仓库介绍

包含内容：

- Java相关技术栈学习的例子
- 一些解决方案的实现例子
- 一些技术学习的总结
- ……

## 文件夹结构

```
- distributed-programme	分布式微服务相关
	-- id	分布式式ID的研究
		--- snowflake	雪花算法研究
- downloader 多线程并发文件下载器（后续优化）
- java-core  Java相关api测试和实现
	-- java8  Java8的api例子
	-- proxytest 动态代理例子
	-- reflectiontest	反射例子
	-- thread 多线程例子
		--- collection 并发集合例子
			---- queue 阻塞队列的实现
- rabbitmq		RabbitMQ学习和测试
- zookeeper		ZooKeeper学习和测试
```

### java-core

#### 目的

Java核心API相关的一些实现。

> - java8  Java8的api例子
> -  proxytest 动态代理例子
>   - cglibproxy CGLB实现动态代理例子
>   - jdkproxy JDK代理例子
> - reflectiontest	反射例子
>   - servlettest 基于反射，实现**一个servlet处理多个接口请求**
> - thread 多线程例子
>   - collection 线程相关集合例子
>     - queue 阻塞队列的实现
>       - SimpleArrayBlockQueue.java  简单阻塞队列（synchronized + wait() + notifyAll()）
>       - ArrayBlockQueue.java  阻塞队列（ReentrantLock + Condition）



### downloader

#### downloader实现目的

**面试题：写个Java程序，多线程并发下载文件，支持断点续传。你的解决方案（客户端和服务端分别怎样实现）?**

一次面试中遇到了这个问题，于是乎就进行了研究。

```
- downloader
	-- down
	-- downbit
```

down和downbit分别是两种实现方式，从github中获得的源码，都有很多可优化的空间，但是实现了基本的需求。

##### down源码地址：[源码](https://github.com/niumoo/down-bit/tree/master/src/main/java/com/wdbyte/downbit)

- 记录了操作日志（会形成一个日志文件），控制台输出了线程信息

![image-20210807175608105](https://gitee.com/koala010/typora/raw/master/img/20210807175608.png)

##### downbit源码地址：[源码 ](https://github.com/yuanyb/Downloader) 

- 每秒实时更新下载速度
- 记录了下载时间
- 配置项更多

![image-20210807175910846](https://gitee.com/koala010/typora/raw/master/img/20210807175910.png)

#### 多线程并发下载（支持断点续传）相关资料

以下是收集的一些相关资料，留待之后优化。

- [撸了个多线程断点续传下载器，我从中学习到了这些知识（附开源地址）](https://zhuanlan.zhihu.com/p/165202244)      [源码](https://github.com/niumoo/down-bit/tree/master/src/main/java/com/wdbyte/downbit)  （分析和源码都可以，实测可用，但还有很多的优化空间。目前只有客户端，面试官当时要求服务端和客户端都有相应的实现）
- [Java实现多线程下载，支持断点续传](https://www.cnblogs.com/yuanyb/p/12296815.html)   [源码 ](https://github.com/yuanyb/Downloader)  （实测可用，也是和第一个一样，有优化空间）
- [在Java中使用多线程结合断点续传实现一个简单的文件下载器](https://blog.csdn.net/u010105970/article/details/51225850)  （一个比较简单的实现）
- [多线程下载文件（支持暂停、取消、断点续传）](https://blog.csdn.net/tianzhaoai/article/details/56673071)   （文件下载的暂停取消的思路值得参考）
- [用java实现文件的断点续传并发下载](https://www.cnblogs.com/songsu/p/11542236.html)  （多线程下载文件的要求，值得参考）
- [Http系列：断点续传与多线程下载](https://zhuanlan.zhihu.com/p/268826375)  （断点续传和多线程下载的思路，值得参考）

#### 优化空间

- 只有客户端实现，无服务端，也应提供个服务端的方案
- 日志、控制台记录内容还不够完善
- 目前不支持取消和暂停下载
- 目前是Java客户端，下载连接只能写死
- ……
