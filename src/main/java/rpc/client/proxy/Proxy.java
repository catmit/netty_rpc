package rpc.client.proxy;

import rpc.annotion.svc;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import rpc.client.SVC;

import java.lang.reflect.Method;

public class Proxy {

    public static <T> T createBean(Class<?> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                svc annotation = method.getAnnotation(svc.class);
                if(annotation == null){
                     return proxy.invokeSuper(obj, args);
                }else {
                     return SVC.call(annotation.svcName(), args).getData();
                }
            }
        });
        return (T) enhancer.create();
    }
}
