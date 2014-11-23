package controller.view;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import controller.*;

//@WebServlet("/view")
public class View extends RoutingServlet {

    @Override
    protected void doRequest(String method, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        super.doRequest(method, req, res);

        ServletContext sc = getServletContext();
        String target     = (String)req.getAttribute("target");

        if (target == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            sc.getNamedDispatcher(target).forward(req, res);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("GET", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doRequest("POST", req, res);
    }

} // API