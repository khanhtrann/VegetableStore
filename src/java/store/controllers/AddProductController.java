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
import store.shopping.ProductDAO;
import store.shopping.ProductDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import store.shopping.ProductError;

/**
 *
 * @author vankh
 */
@WebServlet(name = "AddProductController", urlPatterns = {"/AddProductController"})
public class AddProductController extends HttpServlet {

    private static final String ERROR = "addProduct.jsp";
    private static final String SUCCESS = "MainController?action=Search&search=";

    private static final Logger logger = LogManager.getLogger(AddProductController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        ProductError productError = new ProductError();
        try {
            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            String image = request.getParameter("image");
            int price = Integer.parseInt(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int categoryID = Integer.parseInt(request.getParameter("categoryID"));
            Date importDate = new SimpleDateFormat("yyyy-mm-dd").parse(request.getParameter("importDate"));
            Date usingDate = new SimpleDateFormat("yyyy-mm-dd").parse(request.getParameter("usingDate"));
            boolean status = Boolean.parseBoolean(request.getParameter("status"));
            boolean check = true;
            ProductDAO dao = new ProductDAO();
            boolean checkDuplicate = dao.checkDuplicate(productID);

            if (checkDuplicate) {
                check = false;
                productError.setProductID("Duplicate product ID!");
            }
            if (productID.length() > 20) {
                check = false;
                productError.setProductID("Product ID must be less than 20 characters!");
            }
            if (productName.length() < 3 || productName.length() > 20) {
                check = false;
                productError.setProductName("Product name must be in between 3-20 characters.");
            }
            if (image.length() > 200) {
                check = false;
                productError.setImage("Image links must be less than 200 characters!");
            }
            if (categoryID < 1 || categoryID > 4) {
                check = false;
                productError.setCategoryID("Category ID must be between 1-4.");
            }
            if (check) {
                ProductDTO product = new ProductDTO(productID, productName, image, price, quantity, categoryID, importDate, usingDate, status);
                boolean checkCreate = dao.addProduct(product);
                if (checkCreate) {
                    url = SUCCESS;
                }
            }else {
                request.setAttribute("PRODUCT_ERROR", productError);
            }

        } catch (Exception e) {
            logger.error("Error at AddProductController: " + e.toString());
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
