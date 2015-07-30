package org.github.albertyang2007.productlib.mvc.listen;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class Listener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String webroot = servletContextEvent.getServletContext().getRealPath("/");
        

        System.setProperty("webappHome", webroot);  
        // read parameter from web.xml file to set log4j property  
      
        
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	
    }
}