package rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import rpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.util.SerializingUtil;

@Sharable
public class RpcResponseEncoder extends MessageToByteEncoder<RpcResponse> {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponse msg, ByteBuf out) throws Exception {
        logger.info("response encoder");
        out.writeBytes(SerializingUtil.serialize(msg));
    }
}
