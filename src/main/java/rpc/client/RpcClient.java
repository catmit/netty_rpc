package rpc.client;

import io.netty.channel.*;
import io.netty.channel.pool.SimpleChannelPool;
import rpc.model.RpcRequest;
import rpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.util.ChannelPool;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class RpcClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static ExecutorService executorService = Executors.newCachedThreadPool();
    private static final SimpleChannelPool pool = ChannelPool.getRpcServerPool();

    public RpcResponse send(RpcRequest rpcRequest){
        RpcResponse rpcResponse = null;
        CompletableFuture<RpcResponse> future = CompletableFuture.supplyAsync(new Supplier<RpcResponse>() {
            @Override
            public RpcResponse get() {
                Channel channel = null;
                try {
                    channel = pool.acquire().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                channel.writeAndFlush(rpcRequest);
                pool.release(channel);
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
