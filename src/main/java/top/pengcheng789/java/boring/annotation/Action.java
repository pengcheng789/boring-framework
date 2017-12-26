package top.pengcheng789.java.boring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    enum RequestMethod { GET, POST }

    /**
     * Request method.
     */
    RequestMethod method();

    /**
     * Request path.
     */
    String path();
}
