package rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import rpc.model.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.util.SerializingUtil;

public class RpcRequestEncoder extends MessageToByteEncoder<RpcRequest> {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcRequest msg, ByteBuf out) throws Exception {
        out.writeBytes(SerializingUtil.serialize(msg));
        logger.info("request encoder");
    }
}
