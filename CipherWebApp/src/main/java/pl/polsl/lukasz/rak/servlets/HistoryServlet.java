/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.lukasz.rak.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.lukasz.rak.database.ManageDB;
import pl.polsl.lukasz.rak.entities.HistoryEntity;

/**
 * The servlet which prints the history of the current session to the user as well as adds a cookie with the amount of times the page was visited.
 * 
 * @author Łukasz Rak
 * @version FINAL-5
 * 
 */
@WebServlet(name = "HistoryServlet", urlPatterns = {"/History"})
public class HistoryServlet extends HttpServlet {
   
     /**The database manager.*/
    private ManageDB dbManager;
    
     /**Function which initializes the db manager.*/
    @Override
    public void init(){
         //the connection is established at the start of web application
        this.dbManager = (ManageDB)getServletContext().getAttribute("DBManager");
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            
            HttpSession session = request.getSession();
            ArrayList<String> encryptions = (ArrayList)session.getAttribute("enc");
            ArrayList<String> decryptions = (ArrayList)session.getAttribute("dec");
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head> <center>");
            out.println("<title>Cookies</title>");            
            out.println("</head>");
            out.println("<body bgcolor=#008080>");
            out.println("<h1>History taken from the database </h1>");
            out.println("</body> <center>");
            out.println("</html>");
            
            //retrieve data from the database
            List<HistoryEntity> history = dbManager.read();
            for(HistoryEntity element : history) {
                out.println( "ID: " + element.getId() + "<br/>"
                        + "KeyColumns: " + element.getKeyColumns() + "<br/>"
                        + "KeyMatrix: " + element.getKeyMatrix() + "<br/>"
                        + "Message: " + element.getMessage() + "<br/>"
                        + "Result: " + element.getResult() + "<br/>"                                        
                );
                out.println("<br>");
            }
            
            //Below is the code adding a cookie with the current amount of visits to the page
            int numberOfVisits = 1;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("historyPageVisit")) {
                        numberOfVisits = Integer.parseInt(cookie.getValue())+1;                    
                        break;
                    }
                }
            }
            Cookie cookie = new Cookie("historyPageVisit", Integer.toString(numberOfVisits));
            response.addCookie(cookie);             
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
