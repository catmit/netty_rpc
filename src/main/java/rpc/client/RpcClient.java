package rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import rpc.ChannelManager;
import rpc.model.RpcRequest;
import rpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class RpcClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static ExecutorService executorService = Executors.newCachedThreadPool();

    public RpcResponse send(RpcRequest rpcRequest){
        Channel channel = ChannelManager.getChannel();
        RpcResponse rpcResponse = null;
        CompletableFuture<RpcResponse> future = CompletableFuture.supplyAsync(new Supplier<RpcResponse>() {
            @Override
            public RpcResponse get() {
                channel.writeAndFlush(rpcRequest);
                RpcClientHandler rpcClientHandler = (RpcClientHandler) channel.pipeline().get("rpc");
                try {
                    rpcClientHandler.getSemaphore().acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return rpcClientHandler.getRpcResponse();
            }
        }, executorService);
        try {
            rpcResponse = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rpcResponse;
    }
}
