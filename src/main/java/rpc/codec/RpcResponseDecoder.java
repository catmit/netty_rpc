package rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import rpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.util.SerializingUtil;

import java.util.List;

public class RpcResponseDecoder extends ByteToMessageDecoder {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data, 0, in.readableBytes());
        out.add(SerializingUtil.deserialize(data, RpcResponse.class));

        logger.info("response decoder");

    }
}
