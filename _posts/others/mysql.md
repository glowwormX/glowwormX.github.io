* linux安装mysql   
https://blog.csdn.net/qq_15783243/article/details/78565705   
https://www.cnblogs.com/xinjing-jingxin/p/8025805.html   

* .scripts出错
yum -y install numactl

* 找不到/tmp/mysql.sock、mariadb.log错误
vi /etc/my/cnf   
修改   
socket=/tmp/mysql.sock   
log-error=/var/log/mariadb/mariadb.log   
pid-file=/var/run/mariadb/mariadb.pid   


* root用户登录名为mysql的数据库  
./mysql -u root mysql -p   

* 创建用户user1密码123456，且可以外部链接   
grant all on *.* to user1 identified by '123456';    


* 基本操作  
https://www.cnblogs.com/fly1988happy/archive/2011/12/15/2288554.html
