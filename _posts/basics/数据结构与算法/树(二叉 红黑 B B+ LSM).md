1. 二叉树、红黑树、B、B+、LSM树   
红黑树：三叉树中的两个元素父节点用红链表示   
2-3树到红黑树 https://blog.csdn.net/fei33423/article/details/79132930    
2-3-4树 https://www.cnblogs.com/nullzx/p/6128416.html    
B:N叉树   
B+:父节点只放索引不存数据的N叉树   
LSM树:N阶合并数，存储在内存和磁盘上，内存中数据量过大则与磁盘上的数合并，磁盘上过大则与上一级合并,多用于NoSql基于内存的数据库，对于内存，读速度非常快，比磁盘快1000倍，LSM相对于B+树会舍弃部分读速度，提高写速度   
https://www.cnblogs.com/rocky24/p/4798644.html   
