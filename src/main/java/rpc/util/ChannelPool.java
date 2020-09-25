package rpc.util;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelHealthChecker;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.client.RpcClientHandler;
import rpc.codec.RpcRequestEncoder;
import rpc.codec.RpcResponseDecoder;

import java.net.InetSocketAddress;

public class ChannelPool {
    private static SimpleChannelPoolMap poolMap;
    private static InetSocketAddress rpcServerAddress = new InetSocketAddress("127.0.0.1", 6667);
    private static ChannelPool channelPool = new ChannelPool();

    public static SimpleChannelPool getRpcServerPool(){
        return poolMap.get(rpcServerAddress);
    }


    private ChannelPool() {
        Bootstrap b = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class);
        poolMap = new SimpleChannelPoolMap(b);
    }

    static class SimpleChannelPoolMap extends AbstractChannelPoolMap<InetSocketAddress, SimpleChannelPool> {
        private Bootstrap bootstrap;

        public SimpleChannelPoolMap(Bootstrap bootstrap) {
            this.bootstrap = bootstrap;
        }

        @Override
        protected SimpleChannelPool newPool(InetSocketAddress key) {
            return new SimpleChannelPool(
                    bootstrap.remoteAddress(key),
                    new RpcChannelPoolHandler(),
                    ChannelHealthChecker.ACTIVE,
                    true,
                    true);
        }
    }
    static class RpcChannelPoolHandler implements ChannelPoolHandler {
        private static final Logger logger = LoggerFactory.getLogger(RpcChannelPoolHandler.class);
        @Override
        public void channelReleased(Channel ch) throws Exception {
            logger.info("channelReleased", ch.id());
        }

        @Override
        public void channelAcquired(Channel ch) throws Exception {
            logger.info("channelAcquired", ch.id());

        }

        @Override
        public void channelCreated(Channel ch) throws Exception {
            ch.pipeline()
                    .addLast(new RpcRequestEncoder())
                    .addLast(new RpcResponseDecoder())
                    .addLast("rpc", new RpcClientHandler());
        }
    }
}
