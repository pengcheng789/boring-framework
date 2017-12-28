package top.pengcheng789.java.boring;

import top.pengcheng789.java.boring.util.ClassUtil;

import java.util.Set;

/**
 * @author pen
 */
public class ContainerInitializer {

    public static void init() {
        String packageName = "top.pengcheng789.java.boring.container";
        Set<Class<?>> classSet = ClassUtil.getClassSet(packageName);
        classSet.forEach(cls -> ClassUtil.loadClass(cls.getName()));
    }
}
