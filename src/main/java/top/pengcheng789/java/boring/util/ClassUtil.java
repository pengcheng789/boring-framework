package top.pengcheng789.java.boring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Do some operations of class.
 *
 * @author pen
 */
public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    private static final String PROTOCOL_FILE = "file";
    private static final String PROTOCOL_JAR = "jar";
    private static final String SUFFIX_CLASS = ".class";

    /**
     * Get the class loader.
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * Load class.
     */
    public static Class<?> loadClass(String className, boolean isInitializes) {

        LOGGER.info("Loading class \'" + className + "\' ...");

        Class<?> cls;
        try {
            cls = Class.forName(className, isInitializes, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("Load \'" + className + "\' failure!");
            throw new RuntimeException(e);
        }

        LOGGER.info("Loaded class \'" + className + "\'.");

        return cls;
    }

    /**
     * Load class (it will be initialize the class.)
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     * Get the class set which in the base package name.
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        Enumeration<URL> urls;

        try {
            urls = getClassLoader()
                    .getResources(packageName.replaceAll("\\.", File.separator));
        } catch (IOException e) {
            LOGGER.error("Process \'" + packageName + "\' failure!");
            throw new RuntimeException(e);
        }

        LOGGER.info("Processing \'" + packageName + "\' ...");

        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String protocol = url.getProtocol();

            if (protocol.equals(PROTOCOL_FILE)) {
                LOGGER.info("Processing protocol of file ...");
                String filePath = url.getPath().replaceAll("%20", " ");
                handleFile(classSet, filePath, packageName);
                LOGGER.info("Processed protocol of file.");
            } else if (protocol.equals(PROTOCOL_JAR)) {
                LOGGER.info("Processing protocol of jar ...");
                handleJar(classSet, url);
                LOGGER.info("Processed protocol of jar.");
            }
        }

        LOGGER.info("Processed \'" + packageName + "\'.");

        return classSet;
    }

    /**
     * handle normal file or directory.
     */
    private static void handleFile(Set<Class<?>> classSet, String filePath, String packageName) {
        File[] files = new File(filePath).listFiles(file -> file.isFile()
                && file.getName().endsWith(SUFFIX_CLASS) || file.isDirectory());

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String className = packageName + "."
                        + fileName.substring(0, fileName.lastIndexOf("."));

                LOGGER.info("Processing \'" + className + "\' ...");
                addClass(classSet, className);
                LOGGER.info("Processed \'" + className + "\'.");
            } else {
                String subFilePath = filePath + File.separator + file.getName();
                String subPackageName = packageName + "." + file.getName();
                handleFile(classSet, subFilePath, subPackageName);
            }
        }
    }

    /**
     * handle jar file.
     */
    private static void handleJar(Set<Class<?>> classSet, URL jarUrl) {
        LOGGER.info("Processing \'" + jarUrl.getPath() + "\' ...");

        try {
            JarURLConnection connection = (JarURLConnection) jarUrl.openConnection();
            JarFile jarFile = connection.getJarFile();

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                if (entryName.endsWith(SUFFIX_CLASS)) {
                    addClass(classSet, entryName.substring(0, entryName.lastIndexOf("."))
                            .replaceAll(File.separator, "."));
                }
            }
        } catch (IOException e) {
            LOGGER.error("Process \'" + jarUrl.getPath() + "\' failure!");
            throw new RuntimeException(e);
        }

        LOGGER.info("Processed \'" + jarUrl.getPath() + "\'.");
    }

    /**
     * Load class and put in the class set.
     */
    private static void addClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className);
        classSet.add(cls);
        LOGGER.info("Add \'" + className + "\' to class set.");
    }
}
