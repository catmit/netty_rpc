package rpc;

import io.netty.channel.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.client.RpcClientHandler;
import rpc.codec.RpcRequestEncoder;
import rpc.codec.RpcResponseDecoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChannelManager {
    private final static ConcurrentLinkedQueue<Channel> pool = new ConcurrentLinkedQueue<>();
    private final static Logger logger = LoggerFactory.getLogger(ChannelManager.class);

    public static boolean isFull(){
        if (pool.size() > 300){
            throw new RuntimeException("创建失败  创建太多的channel");
        }
        return true;
    }

    public static Channel getChannel(){
        Channel channel;
        if(pool.isEmpty()){
            if(isFull()){
                return createChannel();
            }
        }
        channel = pool.poll();
        while (!channel.isActive()){
            logger.info("channel is not active");
            channel = getChannel();
        }
        logger.info("get channel id is {}", channel.id());
        return channel;
    }

    public static void saveChannel(Channel channel){
        logger.info("save channel id is {}", channel.id());
        pool.add(channel);
    }

    public static void delChannel(Channel channel){
        logger.info("del channel id is {}", channel.id());
        pool.remove(channel);
    }

    private static Channel createChannel(){
        EventLoopGroup group = new NioEventLoopGroup();
        RpcClientHandler rpcClientHandler = new RpcClientHandler();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("127.0.0.1", 6667))
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new RpcRequestEncoder())
                                .addLast(new RpcResponseDecoder())
                                .addLast("rpc", rpcClientHandler);
                    }
                });
        ChannelFuture future = null;
        Channel channel;
        try {
            future = b.connect().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel = future.channel();
        logger.info("create channel id is {} ", channel.id());
        return channel;
    }

}
