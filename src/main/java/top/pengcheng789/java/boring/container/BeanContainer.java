package top.pengcheng789.java.boring.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.pengcheng789.java.boring.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Manage a map of class and instance.
 *
 * @author pen
 */
public class BeanContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanContainer.class);

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        LOGGER.info("Initializing a map of class and instance.");

        Set<Class<?>> classSet = ClassContainer.getClassSet();
        classSet.forEach(BeanContainer::addBeanMap);

        LOGGER.info("Initialized a map of class and instance.");
    }

    /**
     * Return the instance of class.
     */
    public static Object getBeanInstance(Class<?> cls) {
        return BEAN_MAP.get(cls);
    }

    /**
     * Instantiation a class and put in the 'BEAN_MAP' map.
     */
    private static void addBeanMap(Class<?> cls) {
        LOGGER.info("Adding \'" + cls.getCanonicalName() + "\' to the bean map.");
        BEAN_MAP.put(cls, ReflectionUtil.newInstance(cls));
        LOGGER.info("Added \'" + cls.getCanonicalName() + "\' to the bean map.");
    }
}
