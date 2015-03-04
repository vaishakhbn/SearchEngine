package edu.uci.servlet;

import edu.uci.ranking.RetrieveSearchTerms;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by swanand on 2/28/2015.
 */
public class ResultServletContext implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try{
            ServletContext servletContext = servletContextEvent.getServletContext();
            servletContext.setAttribute("retriever", new RetrieveSearchTerms());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
