**JAVA虚拟机基本结构**   
7个：堆、虚拟机栈、本地方法栈、方法区、程序计数器、运行时常量、直接内存   
HotSpot下：   
不区分虚拟机栈和本地方法栈，永久代(Permanent Generation)就是方法区，通过-XX:PermSize和-XX:MaxPermsize设置（不是堆里的老年代）   
1.6前常量池在永久代（方法区），1.7后移出   
Xmx（最大堆）+MaxPermSize（最大方法区）+程序计数器（忽略不计）+剩余内存=操作系统内存 ？猜测   
-XX:MaxDirectMemorySize（本地直接接内存）、运行时常量、虚拟机栈和本地方法栈都在剩余内存中争夺 ？猜测   

**方法区**   
局部变量表：函数参数、局部变量  
操作数栈：中间操作结果  
帧数据区：常量池解析、正常方法返回、异常处理  

**垃圾回收算法：**   
引用计数法  
标记清楚法  
复制算法（新生代，存活对象少，from、to相当于两个区域，一次只使用一个）  
标记压缩算法（老年代，存活对象多，复制清楚部分，才能产生连续的内存）  
分代算法  
分区算法  

**GC Root包括以下几种对象：**
* 虚拟机栈中引用的对象   
* 本地方法栈中JNI引用的对象   
* 方法区中类静态成员变量引用的对象   
* 方法区中常量引用的对象   
常见内存泄漏 https://blog.csdn.net/zhousenshan/article/details/52864277

_**垃圾收集器**_：    
**SerialGC**：串行回收期  
**新生代ParNewGC回收器**：工作在新生代，独占式，多线程回收，需要并发能力高的CPU  
**新生代ParallelGC回收器**：与ParNewGC差不多，特点：注重系统吞吐量，可以调吞吐量、停顿时间，另外可以自动调整新生代、eden和survivor比例、晋升老年代年龄、堆大小、吞吐量、停顿时间  
**老年代ParallelGC回收器**：JDK1.6，与新生代ParNewGC回收器使用的系统非常注重吞吐量  
**GMS回收器：  **
**G1回收器：**  详解 https://www.jianshu.com/p/aef0f4765098
![](/imgs/GC.jpg)

系列文章   
https://crowhawk.github.io/2017/08/15/jvm_3/   
java8
https://blog.csdn.net/weixin_42987339/article/details/81974739

**对象分配过程**   
尝试栈上分配  
尝试TLAB分配（本地线程分配缓冲Thread Local Allocation Buffer,每个线程自己的堆空间、较小、在Eden上）  
判断是否满足直接进入老年代  
最后在eden分配  

多线程内存分配 1、CAS加失败重试 2、先TLAB上分配，用完后同步锁定 （是否使用TLAB设定 -XX:+/-UseTLAB）   
内存分配后，将除了对象头都初始化为0，保证了某些类型字段能访问到其对应的0值
 
**晋升策略**   
TargetSurvivorRatio（survivor区的使用率，默认50）：达到该值进入老年区   
MaxTenuringThreshold（进入老年代阈值，默认15）：达到该值进入老年区   
