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
public class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

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

        logger.info("Loading class \'" + className + "\' ...");

        Class<?> cls;
        try {
            cls = Class.forName(className, isInitializes, getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error("Load \'" + className + "\' failure!");
            throw new RuntimeException(e);
        }

        logger.info("Loading class \'" + className + "\' completed.");

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
            logger.error("Process \'" + packageName + "\' failure!");
            throw new RuntimeException(e);
        }

        logger.info("Processing \'" + packageName + "\' ...");

        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String protocol = url.getProtocol();

            if (protocol.equals(PROTOCOL_FILE)) {
                logger.info("Processing protocol of file ...");
                String filePath = url.getPath().replaceAll("%20", " ");
                handleFile(classSet, filePath, packageName);
                logger.info("Processing protocol of file completed.");
            } else if (protocol.equals(PROTOCOL_JAR)) {
                logger.info("Processing protocol of jar ...");
                handleJar(classSet, url);
                logger.info("Processing protocol of jar completed.");
            }
        }

        logger.info("Processing \'" + packageName + "\' completed.");

        return classSet;
    }

    /**
     * handle normal file or directories.
     */
    private static void handleFile(Set<Class<?>> classSet, String filePath, String packageName) {
        File[] files = new File(filePath).listFiles(file -> file.isFile()
                && file.getName().endsWith(SUFFIX_CLASS) || file.isDirectory());

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String className = packageName + "."
                        + fileName.substring(0, fileName.lastIndexOf("."));

                logger.info("Processing \'" + className + "\' ...");
                addClass(classSet, className);
                logger.info("Processing \'" + className + "\' completed.");
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
        logger.info("Processing \'" + jarUrl.getPath() + "\' ...");

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
            logger.error("Processing \'" + jarUrl.getPath() + "\' failure!");
            throw new RuntimeException(e);
        }

        logger.info("Processing \'" + jarUrl.getPath() + "\' completed.");
    }

    /**
     * Load class and put in the class set.
     */
    private static void addClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className);
        classSet.add(cls);
        logger.info("Add \'" + className + "\' to class set.");
    }
}
