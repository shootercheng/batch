package com.single.batch.proxy;

import com.single.batch.mapper.BatchLabelMapper;

import java.lang.reflect.Proxy;

/**
 * @author James
 */
public class ProxyFactory {

    @SuppressWarnings(value = "unchecked")
    public static <T> T proxyLogger(LoggerInvocationHandler<T> loggerInvocationHandler) {
        Class<T> clazz = loggerInvocationHandler.getClazz();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, loggerInvocationHandler);
    }
}
