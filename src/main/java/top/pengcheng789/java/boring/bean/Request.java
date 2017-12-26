package top.pengcheng789.java.boring.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import top.pengcheng789.java.boring.annotation.Action;

/**
 * Package the request path and request method
 *
 * @author pen
 */
public class Request {
    private Action.RequestMethod requestMethod;
    private String requestPath;

    public Request(Action.RequestMethod requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;

    }

    public Action.RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(requestMethod).append(requestPath).toHashCode();
    }

    @Override
    public boolean equals(final Object obj){
        if(obj instanceof Request){
            final Request other = (Request) obj;
            return new EqualsBuilder()
                    .append(requestMethod, other.requestMethod)
                    .append(requestPath, other.requestPath)
                    .isEquals();
        } else{
            return false;
        }
    }
}
