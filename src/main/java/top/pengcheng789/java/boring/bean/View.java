package top.pengcheng789.java.boring.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pen
 */
public class View {

    private String path;
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<>();
    }

    public void setAttribute(String key, Object obj) {
        model.put(key, obj);
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
