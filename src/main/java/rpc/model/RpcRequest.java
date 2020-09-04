package rpc.model;

import java.util.concurrent.atomic.AtomicInteger;

public class RpcRequest {

    private int id = 0;
    private String clazzName;
    private String methodName;
    private Object[] params;
    private static AtomicInteger counter = new AtomicInteger(0);

    public RpcRequest() {
    }

    public RpcRequest(String clazzName, String methodName, Object[] params) {
        this.clazzName = clazzName;
        this.methodName = methodName;
        this.params = params;
        incrementRequestId();
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private void incrementRequestId(){
        id = counter.addAndGet(1);
    }

    @Override
    public String toString() {
        return String.format("id: %d", getId());
    }
}
