package edu.uci.servlet;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by swanand on 2/27/2015.
 */
@WebServlet(name = "ResultServlet")
public class ResultServlet extends HttpServlet {

    private String message;

    public void init() throws ServletException
    {
        message = "Hello World";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /*Insert glue code to call query results*/
        try {

            JSONObject json = new JSONObject();
            String queryString = request.getQueryString();
            String qry = request.getParameter("qry");
            System.out.println(queryString + qry);
            json.put("data", qry);
            response.setContentType("application/json");
            response.getWriter().write(json.toJSONString());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
