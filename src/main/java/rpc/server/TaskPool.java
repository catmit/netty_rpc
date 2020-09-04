package rpc.server;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.DI.RpcServerDI;
import rpc.model.RpcRequest;
import rpc.model.RpcResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

class TaskPoll{
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final Logger logger = LoggerFactory.getLogger(TaskPoll.class);

    public static CompletableFuture<RpcResponse> execute(RpcRequest rpcRequest){
        CompletableFuture<RpcResponse> future = CompletableFuture.supplyAsync(() -> {
            Object bean = RpcServerDI.getBean(rpcRequest.getClazzName());
            Class<?> clazz = bean.getClass();
            Object[] params = rpcRequest.getParams();
            Class<?>[] paramTypes = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                paramTypes[i] = params[i].getClass();
            }
            Method method = null;
            try {
                method = clazz.getMethod(rpcRequest.getMethodName(), paramTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            logger.info("调用类 {} 方法 {} ", clazz, method);
            Object result = null;
            try {
                result = method.invoke(bean, params);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return RpcResponse.successResponse(rpcRequest.getId(), result);
        }, executorService);
        return future;
    }

    public static void main(String[] args) throws Exception{
    }
}
