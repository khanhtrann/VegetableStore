/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package store.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import store.shopping.ProductDAO;
import store.shopping.ProductDTO;

/**
 *
 * @author vankh
 */
@WebServlet(name = "UpdateController", urlPatterns = {"/UpdateController"})
public class UpdateController extends HttpServlet {

    private static final String ERROR = "SearchController";
    private static final String SUCCESS = "SearchController";
    private static final Logger logger = LogManager.getLogger(UpdateController.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            String image = request.getParameter("image");
            int price = Integer.parseInt(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int categoryID = Integer.parseInt(request.getParameter("categoryID"));
            Date importDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("importDate"));
            Date usingDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("usingDate"));
            String Status = request.getParameter("status");
            boolean status = Boolean.parseBoolean(Status);
            ProductDAO dao = new ProductDAO();
            ProductDTO user = new ProductDTO(productID, productName, image, price, quantity, categoryID, importDate, usingDate, status);
            boolean check = dao.updateProduct(user);
            if (check) {
                url = SUCCESS;
            }
        } catch (Exception e) {
            logger.error("Error at UpdateController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
