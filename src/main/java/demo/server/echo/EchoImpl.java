package demo.server.echo;

import java.util.Random;


public class EchoImpl implements Echo {
    Random random = new Random();

    @Override
    public String reply(String msg) {
//        try {
//            Thread.sleep(random.nextInt(2)*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return "echo: " + msg;
    }
}
