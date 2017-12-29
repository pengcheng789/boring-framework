package top.pengcheng789.java.boring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * a tool to create a new instance or invoke method.
 *
 * @author pen
 */
public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * Create a new instance.
     */
    public static Object newInstance(Class<?> cls) {
        LOGGER.info("Creating a instance of \'" + cls.getCanonicalName() + "\' ...");

        Object instance;

        try {
            instance = cls.getConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException
            | InvocationTargetException e) {

            LOGGER.error("Create a instance of \'" + cls.getCanonicalName() + "\' failure!");
            throw new RuntimeException(e);
        }

        LOGGER.info("Created a instance of \'" + cls.getCanonicalName() + "\'.");
        return instance;
    }

    /**
     * Create a new instance according class name.
     */
    public static Object newInstance(String className) {
        Class<?> cls = ClassUtil.loadClass(className);
        return newInstance(cls);
    }

    /**
     * Invoke method.
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        LOGGER.info("Invoking \'" + method.getDeclaringClass() +  "." + method.getName()
                + "\' ...");

        Object result;

        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("Invoke \'" + method.getName() + "\' failure!");
            throw new RuntimeException(e);
        }

        LOGGER.info("Invoked \'" + method.getDeclaringClass() +  "." + method.getName() + "\'.");
        return result;
    }
}
