package by.epam.onlinetraining.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class EncodingFilter implements Filter {
    private static final String ENCODING_TYPE = "UTF-8";

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING_TYPE);
        response.setCharacterEncoding(ENCODING_TYPE);

        filterChain.doFilter(request, response);
    }

    public void destroy() {
    }
}
