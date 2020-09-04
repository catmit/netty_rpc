package rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import rpc.model.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.util.SerializingUtil;

import java.util.List;

public class RpcRequestDecoder extends ByteToMessageDecoder {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data, 0, in.readableBytes());
        System.out.println(data.getClass());
        out.add(SerializingUtil.deserialize(data, RpcRequest.class));
        logger.info("request decoder");
    }
}
