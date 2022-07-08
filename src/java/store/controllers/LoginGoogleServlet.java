/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package store.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import store.user.UserDAO;
import store.user.UserDTO;
import store.utils.GooglePojo;
import store.utils.GoogleUtils;

/**
 *
 * @author vankh
 */
@WebServlet("/login-google")
public class LoginGoogleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public LoginGoogleServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            RequestDispatcher dis = request.getRequestDispatcher("login.jsp");
            dis.forward(request, response);
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
            HttpSession session = request.getSession();
            session.setAttribute("id", googlePojo.getId());
            session.setAttribute("name", googlePojo.getName());
            session.setAttribute("email", googlePojo.getEmail());
            String email = googlePojo.getEmail();
            UserDAO dao = new UserDAO();
            boolean check = false;
            try {
                check = dao.checkDuplicate(email);
            } catch (SQLException ex) {
                Logger.getLogger(LoginGoogleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (check == false) {
                try {
                    dao.createGmailUser(email);
                } catch (SQLException ex) {
                    Logger.getLogger(LoginGoogleServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                UserDTO loginUser = dao.checkLogin(email, "");
                if (loginUser != null) {
                    session.setAttribute("LOGIN_USER", loginUser);
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginGoogleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            RequestDispatcher dis = request.getRequestDispatcher("MainController?action=Find&search=");
            dis.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
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
