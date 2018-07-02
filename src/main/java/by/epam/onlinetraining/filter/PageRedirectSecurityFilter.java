package by.epam.onlinetraining.filter;

import by.epam.onlinetraining.bundles.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(urlPatterns = {"/jsp/*"})
public class PageRedirectSecurityFilter implements Filter {
    private String indexPath;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = ConfigurationManager.getProperty("path.page.index");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        ServletContext servletContext = httpServletRequest.getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(indexPath);
        requestDispatcher.forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
