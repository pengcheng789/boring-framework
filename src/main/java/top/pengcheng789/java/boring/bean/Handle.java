package top.pengcheng789.java.boring.bean;

import java.lang.reflect.Method;

/**
 * Package the controller class and method of 'Action'.
 *
 * @author pen
 */
public class Handle {

    private Class<?> controllerClass;
    private Method method;

    public Handle(Class<?> cls, Method method) {
        this.controllerClass = cls;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
