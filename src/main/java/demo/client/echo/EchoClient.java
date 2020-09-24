package demo.client.echo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.client.proxy.Proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class EchoClient {
    private static final Logger logger = LoggerFactory.getLogger(EchoClient.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        EchoService echoService = Proxy.createBean(EchoService.class);
//        testIdle(echoService);
        multiThread(echoService);
    }

    static void testIdle(EchoService echoService){
        int count = 0;
        while (count < 1000){
            count++;
            System.out.println(echoService.reply("hello"));
            System.out.println(count);
        }
    }

    static void multiThread(EchoService echoService){
        int count = 0;
        while (count < 20000) {
            count++;
            int finalCount = count;
            new Thread(() -> {
                logger.info("count is " + finalCount);
                logger.info(echoService.reply("hello world   " + finalCount));
            }).start();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void test(){
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        EchoService echoService = Proxy.createBean(EchoService.class);
        /*String msg = input.readLine();

        while (!"bye".equals(msg)){
            System.out.println(echoService.reply(msg));
            msg = input.readLine();
        }*/
        int counter = 0;
        int starTime;
        int endTime;
        /*while (counter < 1000){
            logger.info("调用{}次" , ++counter);
            System.out.println(echoService.reply("hello   " + counter));
        }*/
    }
}
