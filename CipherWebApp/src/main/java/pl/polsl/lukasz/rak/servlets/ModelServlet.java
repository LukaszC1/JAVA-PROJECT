/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.lukasz.rak.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import pl.polsl.lukasz.rak.database.ManageDB;
import pl.polsl.lukasz.rak.entities.HistoryEntity;
import pl.polsl.lukasz.rak.model.CipherModel;
import pl.polsl.lukasz.rak.model.IncorrectInput;

/**
 * The servlet handling communication with the model class running the cipher/decipher operations.
 * 
 * @author Łukasz Rak
 * @version FINAL-5
 */
@WebServlet(name = "Cipher" ,urlPatterns = {"/Cipher"})
public class ModelServlet extends HttpServlet {

     /**The list which holds the message for ancrpytion together with the result.*/
    private ArrayList<String> encryptions = new ArrayList<>();
    
    /**The list which holds the message for decryption together with the result.*/
    private ArrayList<String> decryptions = new ArrayList<>();
    
    /**ModelServlet field containing the model used for encryption/decryption.*/
    private final CipherModel model;
    
    /**The database manager.*/
    private ManageDB dbManager;
    
    /**ModelServlet class constructor.*/
    public ModelServlet()
    {
        this.model = new CipherModel();     
    }
    
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
 
        response.setContentType("text/html; charset=ISO-8859-2");
        PrintWriter out = response.getWriter();

        // Get parameter values
        String keyMatrix = request.getParameter("keyMatrix");
        String keyColumns = request.getParameter("keyColumns");
        String message = request.getParameter("message");
        String action = request.getParameter("data");
     
            
            if (keyMatrix.length() == 0 || keyColumns.length() == 0 || message.length() == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "One of the input fields is empty!"); 
            } 
            else 
            {
                model.setKeyColumns(keyColumns);
                model.setKeyPolybiusSquare(keyMatrix);
                model.setMessage(message);
                
              if(action.equals("DECRYPT"))
              {
                  try{
                      //run the algorithm
                      model.decrypt();
                      if(model.getMessage().length() != (message.length()/2)) //The input message cannot be decrypted (input data is incorrect)
                      {
                          response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The message could not be decrypted!");
                          return;
                      }
                      //add an entry to the list with history of decryptions
                      decryptions.add(message.toUpperCase() + " , " + model.getMessage());
                      out.println("<body bgcolor=#008080> <center>" +"<h1>Result</h1><br> " + model.getMessage());
                       
                       //add a new history entity to the database
                       HistoryEntity his = new HistoryEntity();
                      his.setKeyColumns(model.getKeyColumns());
                       his.setKeyMatrix(model.getKeyPolybiusSquare());
                       his.setMessage(message.toUpperCase());
                       his.setResult(model.getMessage());
                       dbManager.add(his);
                       
                  } catch(IncorrectInput e){
                      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The input data is incorrect!"); 
                  }
              }
              else //else encrypt (one option is always checked)
              {
                  try{
                      //run the algorithm
                      model.encrypt(); 
                      //add an entry to the list with history of encryptions
                      encryptions.add(message.toUpperCase() + " , " + model.getMessage());
                      out.println("<body bgcolor=#008080> <center>" +"<h1>Result</h1><br> " + model.getMessage());
                      
                        //add a new history entity to the database
                       HistoryEntity his = new HistoryEntity();
                       his.setKeyColumns(model.getKeyColumns());
                       his.setKeyMatrix(model.getKeyPolybiusSquare());
                       his.setMessage(message.toUpperCase());
                       his.setResult(model.getMessage());
                       dbManager.add(his);  
                       
                  } catch(IncorrectInput e){
                      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The input data is incorrect!"); 
                  }
              }
              //addint the lists to the session to be able to display the history of operations
                HttpSession session = request.getSession();
                session.setAttribute("dec", decryptions);
                session.setAttribute("enc",encryptions);
             
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
