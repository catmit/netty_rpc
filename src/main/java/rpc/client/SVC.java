package rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.model.RpcRequest;
import rpc.model.RpcResponse;

public class SVC {
    private static final Logger logger = LoggerFactory.getLogger(SVC.class);

    public static RpcResponse call(String svcName, Object ...params){
        String[] clazzAndMethodName = svcName.split("@");
        RpcRequest rpcRequest = new RpcRequest(clazzAndMethodName[0], clazzAndMethodName[1], params);
        RpcClient rpcClient = new RpcClient();
        RpcResponse rpcResponse = rpcClient.send(rpcRequest);
        return rpcResponse;
    }
}
