# pport-forwarding-
java 简单端口转发

为了方便本地使用远程阿里云redis,于是自己简单用java写一个socket的转发的工具。顺便研究reids协议，虽然跟这个工程没有关系。

命令：
java -jar PortNatTools.jar 本地监听端口 目标地址 目标端口
nohup java -jar ./PortNatTools.jar 本地监听端口 目标地址 目标端口 & （长期后台运行命令）

转发redis操作(windows):

1:修改host
ip地址 你自己的.redis.rds.aliyuncs.com
这样本地都不用改代码就可以执行运行了

2：
在一个阿里云使用我的转发工具。

nohup java -jar ./PortNatTools.jar 6379 dst_ip dst_port


如果需要修改协议内容，需要一个协议过滤层
