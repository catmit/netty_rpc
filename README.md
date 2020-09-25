# nett_rpc

## netty rpc 

# dev log

status | proposed date| question | todo | resolution date| actual solution| 
------------- |----------- | ------------- | --------|----|------|
O|  09.23.2020 | 用cyclicBarrier进行线程同步会阻塞NIO线程用 | 重写一个可复用的CountDownLatch控制线程同步 |   | 还是需要一个可复用的CountDownLatch来控制线程间的同步|
√|09.23.2020 | 当前channel复用的方式不当 | 仿照CachedThreadPool写一个CachedChannelPool控制channel的数量以及复用channel| 09.25.2020 | netty中有SimpleChannelPool与FixedChannelPool直接使用即可
O|  09.25.2020 | request 与 response不对应 | 全局的ConcurrentHashMap(key：requestId, value: callback)，NIO线程触发channelRead0回调，进而触发map的callback将response传值客户端|   | |
O|  09.25.2020 | 运算符new使用较多，而且大多数类都是单例，程序不优雅  | 用dagger2做IOC |   | |





