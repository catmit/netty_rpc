package rpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.ChannelManager;
import rpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RpcResponse rpcResponse;
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    private ChannelHandlerContext ctx;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        logger.info("channel id is {}", ctx.channel().id());
        this.rpcResponse = msg;
        this.rpcResponse.setRequestId(msg.getRequestId());
        this.ctx = ctx;
        logger.info("channel read0  当前线程{} ", Thread.currentThread().getName());
        cyclicBarrier.await();
        logger.info("唤醒 get result");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("客户端建立连接");
    }

    public RpcResponse getResult() {
        try {
            this.cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        ChannelManager.saveChannel(this.ctx.channel());
        logger.info("----------解锁----------");
        return this.rpcResponse;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println("客户端报错");
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ChannelManager.delChannel(ctx.channel());
        logger.info("client 连接已关闭");
    }
}
