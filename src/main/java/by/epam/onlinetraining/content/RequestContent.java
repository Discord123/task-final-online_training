package by.epam.onlinetraining.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestContent {
    private static final String HEADER_REFERER = "referer";

    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;
    private String url;


    public RequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        url = null;
    }

    public void extractValues(HttpServletRequest request) {
        url = request.getHeader(HEADER_REFERER);

        Enumeration<String> requestAttributeNames = request.getAttributeNames();
        while (requestAttributeNames.hasMoreElements()) {
            String name = requestAttributeNames.nextElement();
            requestAttributes.put(name, request.getAttribute(name));
        }

        requestParameters = request.getParameterMap();

        HttpSession session = request.getSession();
        Enumeration<String> sessionAttributeNames = session.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String name = sessionAttributeNames.nextElement();
            sessionAttributes.put(name, session.getAttribute(name));
        }
    }

    public void insertAttributes(HttpServletRequest request) {
        for(Map.Entry<String, Object> requestAttribute : requestAttributes.entrySet()){
            String key = requestAttribute.getKey();
            Object value = requestAttribute.getValue();
            request.setAttribute(key, value);
        }
        for(Map.Entry<String, Object> sessionAttribute : sessionAttributes.entrySet()){
            HttpSession session = request.getSession();
            String key = sessionAttribute.getKey();
            Object value = sessionAttribute.getValue();
            session.setAttribute(key, value);
        }
    }

    public String getUrl() { return url; }

    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public void setRequestAttributes(String key, Object value) {
        this.requestAttributes.put(key, value);
    }

    public Map<String, String[]> getRequestParameters() {return requestParameters; }

    public String getSingleRequestParameter(String key){
        String parameter = null;
        if(requestParameters.containsKey(key)){
            String[] parameters = requestParameters.get(key);
            if(parameters.length !=0){
                parameter = parameters[0];
            }
        }
        return parameter;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(String key, Object value) {
        this.sessionAttributes.put(key, value);
    }

    public void cleanSession() {
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }

    @Override
    public String toString() {
        return "RequestContent{" +
                "requestAttributes=" + requestAttributes +
                ", requestParameters=" + requestParameters +
                ", sessionAttributes=" + sessionAttributes +
                ", url='" + url + '\'' +
                '}';
    }
}
