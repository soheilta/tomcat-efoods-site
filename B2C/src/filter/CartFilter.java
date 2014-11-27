package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class CartFilter
 */
public class CartFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CartFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
    
    // get access time

    public void init(FilterConfig fConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, 
                FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = ((HttpServletRequest)request);
        HttpSession sess = req.getSession();
        String uri = req.getRequestURI().substring(req.getContextPath().length());
        ServletContext sc = request.getServletContext();
        long before, after, total;
        double avgTime;
        long cartTime;
        int cartCounter;
        if (sc.getAttribute("cartTime") == null) {
            cartTime = 0;
        } else {
            cartTime = (long)sc.getAttribute("cartTime");
        }
        if (sc.getAttribute("cartCounter") == null) {
            cartCounter = 0;
        } else {
            cartCounter = (int)sc.getAttribute("cartCounter");
        }
        
        chain.doFilter(request, response);
        before = (long) sess.getAttribute("startCartTime");
        after =  (long) System.currentTimeMillis();
        
        cartTime += (after - before);
        cartCounter += 1;
        avgTime = (double) cartTime / (double) cartCounter;
        
        sc.setAttribute("cartTime", cartTime);
        sc.setAttribute("cartCounter", cartCounter);
        sess.setAttribute("startCartTime", System.currentTimeMillis());
        
        sc.setAttribute("avgCartTime", avgTime);
    }
    

}
