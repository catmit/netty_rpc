package demo.client.echo;

import rpc.annotion.svc;

public class EchoService {

    @svc(svcName = "demo.server.echo.EchoImpl@reply")
    public String reply(String msg){
        return null;
    }
}
