package rpc.DI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class RpcServerDI {
    private static final ConcurrentHashMap<String, Object> javaBeans = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(RpcServerDI.class);
    public static boolean addBean(String clazzName){
        try {
            Class<?> clazz = Class.forName(clazzName);
            javaBeans.put(clazzName, clazz.newInstance());
            logger.info("加载类： {}", clazz.getName());
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Object getBean(String clazzName){
        Object bean = javaBeans.get(clazzName);
        if(bean == null){
            synchronized (RpcServerDI.class){
                bean = javaBeans.get(clazzName);
               if(bean == null){
                   addBean(clazzName);
                   bean = getBean(clazzName);
               }
            }
        }
        return bean;
    }
}
