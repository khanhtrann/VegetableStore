/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package store.controllers;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.shopping.Cart;
import store.shopping.Item;
import store.shopping.OrderDTO;
import store.shopping.OrderDetailDTO;
import store.shopping.ProductDAO;
import store.shopping.ProductDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author vankh
 */
@WebServlet(name = "CheckoutController", urlPatterns = {"/CheckoutController"})
public class CheckoutController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "MainController?action=Find&search=";
    private static final Logger logger = LogManager.getLogger(CheckoutController.class);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("CART");
            ProductDAO dao = new ProductDAO();
            String userID = request.getParameter("userID");
            int total = 0, orderQuantity, productQuantity = 0;
            long miliseconds = System.currentTimeMillis();
            Date today = new Date(miliseconds);
            if (cart != null) {
                for (Item item : cart.getCart().values()) {
                    total += item.getPrice() * item.getQuantity();
                    orderQuantity = item.getQuantity();
                    ProductDTO product = dao.getProductByID(item.getId());
                    productQuantity = product.getQuantity();
                    if (orderQuantity > productQuantity) {
                        request.setAttribute("MESSAGE", "Sorry, there is not enough product in the warehouse!");
                        request.getRequestDispatcher("user.jsp").forward(request, response);
                        return;
                    }
                }
                int orderID = dao.getOrderID() + 1;
                OrderDTO order = new OrderDTO(orderID, today, total, userID);
                dao.addOrder(order);
                //add order detail for each item and update its quantity
                for (Item item : cart.getCart().values()) {
                    OrderDetailDTO orderDetail = new OrderDetailDTO(item.getPrice(), item.getQuantity(), orderID, item.getId());
                    dao.addOrderDetail(orderDetail);
                    orderQuantity = item.getQuantity();
                    ProductDTO product = dao.getProductByID(item.getId());
                    productQuantity = product.getQuantity();
                    dao.updateProductQuantity(productQuantity - orderQuantity, product.getProductID());
                }
                
                session.removeAttribute("CART");
                request.setAttribute("MESSAGE", "Your order has been checked out successfully!");
            }else{
                request.setAttribute("MESSAGE", "Your cart does not exist!");
            }
            url = SUCCESS;
        } catch (Exception e) {
            logger.error("Error at CheckoutController: " + e.toString());
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
