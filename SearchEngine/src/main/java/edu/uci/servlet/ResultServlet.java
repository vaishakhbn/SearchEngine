package edu.uci.servlet;
import edu.uci.ranking.RetrieveSearchTerms;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by swanand on 2/27/2015.
 */
@WebServlet(name = "ResultServlet")
public class ResultServlet extends HttpServlet {

    private String message;
    private RetrieveSearchTerms retriever;
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        ServletContext context = config.getServletContext();
        retriever = (RetrieveSearchTerms)context.getAttribute("retriever");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    /*Insert glue code to call query results*/
        try {

            JSONObject json = new JSONObject();
            String queryString = request.getQueryString();
            String qry = request.getParameter("qry");
            String[] queryArr = qry.split(" ");
            List<String> results = retriever.retrieveResults(queryArr);
            System.out.println(queryString + qry);
            json.put("data", results);
            response.setContentType("application/json");
            response.getWriter().write(json.toJSONString());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
