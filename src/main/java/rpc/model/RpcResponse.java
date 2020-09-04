package rpc.model;

public class RpcResponse {

    private int requestId;
    private Object data;

    private Integer errorCode;
    private String errorMsg;

    public RpcResponse() {
    }

    public static RpcResponse errorResponse(int requestId, int errorCode, String errorMsg){
        RpcResponse resp =  new RpcResponse();
        resp.setRequestId(requestId);
        resp.setErrorCode(errorCode);
        resp.setErrorMsg(errorMsg);
        return resp;
    }

    public static RpcResponse successResponse(int requestId, Object data){
        RpcResponse resp =  new RpcResponse();
        resp.setRequestId(requestId);
        resp.setData(data);
        return resp;
    }

    public boolean isSuccess(){
        return errorCode == null;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
