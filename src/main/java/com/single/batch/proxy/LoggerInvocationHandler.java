package com.single.batch.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author James
 */
public class LoggerInvocationHandler<T> implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerInvocationHandler.class);

    private Class<T> clazz;

    private Object realObject;

    public LoggerInvocationHandler(Class<T> clazz, Object realObject) {
        this.clazz = clazz;
        this.realObject = realObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        String clazzName = clazz.getName();
        String methodName = method.getName();
        LOGGER.debug("clazz {}, method name {} start time {}", clazzName, methodName, startTime);
        Object returnObj = method.invoke(realObject, args);
        long endTime = System.currentTimeMillis();
        LOGGER.debug("clazz {}, method name {} end time {}", clazzName, methodName, endTime);
        LOGGER.info("clazz {}, method name {} time {}", clazzName, methodName, (endTime - startTime));
        return returnObj;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Object getRealObject() {
        return realObject;
    }

    public void setRealObject(Object realObject) {
        this.realObject = realObject;
    }
}
