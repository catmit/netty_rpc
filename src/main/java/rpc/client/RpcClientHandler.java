package rpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.ChannelManager;
import rpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Semaphore;


public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RpcResponse rpcResponse;
    private ChannelHandlerContext ctx;
    private Semaphore semaphore = new Semaphore(1);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        logger.info("channel id is {}", ctx.channel().id());
        rpcResponse = msg;
        rpcResponse.setRequestId(msg.getRequestId());
        this.ctx = ctx;
        logger.info("channel read0  当前线程{} ", Thread.currentThread().getName());
        semaphore.release();
        logger.info("唤醒 get result");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("客户端建立连接");
    }

    public RpcResponse getRpcResponse() {
        try {
            semaphore.acquire();
            semaphore.release();
        } catch (InterruptedException e) {
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

    public Semaphore getSemaphore(){
        return semaphore;
    }
}
