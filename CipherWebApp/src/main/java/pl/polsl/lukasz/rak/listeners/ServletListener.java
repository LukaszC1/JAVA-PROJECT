package pl.polsl.lukasz.rak.listeners;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import pl.polsl.lukasz.rak.database.ManageDB;


/**
 * Servlet listener class implemented to establish the connection to the database only once for the run of the web app.
 * @author Łukasz Rak
 * @version FINAL-5
 */

@WebListener
public class ServletListener implements ServletContextListener  {

    /**
     * Function adding the db manager to the current context.
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	ServletContext context = servletContextEvent.getServletContext();
    	//create database connection from init parameters and set it to context
    	ManageDB dbManager = new ManageDB();
    	context.setAttribute("DBManager", dbManager);
    	//connect to the database
    }

    /**
     * Function closing the connection to the database when the context is destroyed.
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	ServletContext context = servletContextEvent.getServletContext();
    	ManageDB dbManager = (ManageDB) context.getAttribute("DBManager");
    	dbManager.closeConnection();
    	//disconnect from the database
    }
  }

   


