package controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import model.account.*;
import model.cart.*;
import model.catalog.*;
import model.checkout.*;
import model.common.*;

/**
 * Servlet implementation class CartAPI Cart API Endpoint.
 */
// @WebServlet("/api/checkout")
public class CheckoutAPI extends HttpServlet {

    private static final String JSP_FILE = "/WEB-INF/xmlres/APIResponse.jspx";
    private static final String XSL_FILE = "/WEB-INF/xmlres/PO.xslt";
    private static final String XSD_FILE = "/WEB-INF/xmlres/PO.xsd";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ServletContext sc       = getServletContext();
        HttpSession    sess     = req.getSession();
        String         action   = req.getParameter("action");
        String         number   = req.getParameter("number");
        String         quantity = req.getParameter("quantity");
        Catalog        catalog  = (Catalog)sc.getAttribute("catalog");
        Cart           cart     = (Cart)sess.getAttribute("cart");  
        StringWriter   sw       = new StringWriter();
        File           xslt     = new File(sc.getRealPath(XSL_FILE));
        File		   xsd		= new File(sc.getRealPath(XSD_FILE));
        Receipt		   receipt;
        Account		   account = (Account)sess.getAttribute("account");
        
        File userHistory = new File(sc.getRealPath(sc.getAttribute("userHistory").toString()));
        
        if (cart == null) {
            sess.setAttribute("cart", cart = new Cart());
        }
        
        if (account == null) {
        	sess.setAttribute("account", account = new Account());
        }

        try {
        	req.setAttribute( "data", CheckoutClerk.getClerk(userHistory).checkout(account, cart, xsd, xslt));
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher(JSP_FILE).forward(req, res);
    } // doGet

} // CartAPI
