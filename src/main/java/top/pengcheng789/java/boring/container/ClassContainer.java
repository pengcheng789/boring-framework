package top.pengcheng789.java.boring.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.pengcheng789.java.boring.annotation.Controller;
import top.pengcheng789.java.boring.annotation.Service;
import top.pengcheng789.java.boring.config.BoringConfig;
import top.pengcheng789.java.boring.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Manage a set which container all classes.
 *
 * @author pen
 */
public class ClassContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassContainer.class);

    private static final Set<Class<?>> CLASS_SET
            = ClassUtil.getClassSet(BoringConfig.getAppBasePackage());

    static {
        LOGGER.info("Loaded \'" + ClassContainer.class.getCanonicalName() + "\'.");
    }

    /**
     * Get set of loaded classes.
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * Get set of controller classes.
     */
    public static Set<Class<?>> getControllerSet() {
        LOGGER.info("Getting the controller set ...");

        Set<Class<?>> classSet = new HashSet<>();

        CLASS_SET.forEach(cls -> {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        });

        LOGGER.info("Got the controller set.");
        return classSet;
    }

    /**
     * Get set of service classes.
     */
    public static Set<Class<?>> getServiceSet() {
        LOGGER.info("Getting the service set ...");

        Set<Class<?>> classSet = new HashSet<>();

        CLASS_SET.forEach(cls -> {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        });

        LOGGER.info("Got the service set.");
        return classSet;
    }

}
