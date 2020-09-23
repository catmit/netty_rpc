# nett_rpc

## netty rpc 

# dev log

status | date | question | todo
------------- |----------- | ------------- | --------|
<center>O</center> |  09.23.2020 | 用cyclicBarrier进行线程同步会阻塞NIO线程用 | 使用可复用的CountDownLatch控制线程同步
<center>O</center>|09.23.2020 | 当前channel复用的方式不当 | 仿照CachedThreadPool写一个CachedChannelPool控制channel的数量以及复用channel





