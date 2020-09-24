# nett_rpc

## netty rpc 

# dev log

status | proposed date| question | todo | resolution date| actual solution| 
------------- |----------- | ------------- | --------|----|------|
O|  09.23.2020 | 用cyclicBarrier进行线程同步会阻塞NIO线程用 | 重写一个可复用的CountDownLatch控制线程同步 |   | 还是需要一个可复用的CountDownLatch来控制线程间的同步|
O|09.23.2020 | 当前channel复用的方式不当 | 仿照CachedThreadPool写一个CachedChannelPool控制channel的数量以及复用channel| | netty中有SimpleChannelPool与FixedChannelPool直接使用即可





