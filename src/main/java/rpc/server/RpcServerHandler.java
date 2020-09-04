package rpc.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import rpc.model.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private int counter;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg){
        try {
            TaskPoll.execute(msg).thenAccept(rpcResponse -> ctx.writeAndFlush(rpcResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--------server  建立连接--------");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("-----server  断开连接----");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println("主机报错");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            String eventType = null;
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            switch (stateEvent.state()) {
                case READER_IDLE: {
                    eventType = "读空闲";
                    counter++;
                }
                break;
                case WRITER_IDLE: {
                    eventType = "写空闲";
                }
                break;
                case ALL_IDLE: {
                    eventType = "读写空闲";
                }
                break;
            }
            logger.info("超时事件  {}  {}次", eventType, counter);
            if (counter > 3) {
                logger.info("超时次数: {}, 服务端关闭连接", counter);
                ctx.channel().close();
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
