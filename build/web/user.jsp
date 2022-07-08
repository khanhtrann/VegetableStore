<%-- 
    Document   : user
    Created on : Feb 12, 2022, 2:15:05 PM
    Author     : vankh
--%>

<%@page import="java.sql.Date"%>
<%@page import="store.shopping.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="store.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
        <jsp:include page="meta.jsp" flush="true"/>
    </head>
    <body>
        <jsp:include page="header.jsp" flush="true"/>

        <%
            String search = request.getParameter("search");
            String email = (String) session.getAttribute("email");
            if (search == null) {
                search = "";
            }
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser != null && "US".equals(loginUser.getRoleID())) {

            } else {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        <% if (loginUser != null && email == null) {%>
        <h1 style="margin-left: 5%; margin-top: 3%;">Welcome <%= loginUser.getFullName()%> </h1>
        <% } else {%>
        <h1 style="margin-left: 5%; margin-top: 3%;">Welcome <%= loginUser.getUserID() %> </h1>
        <% }%>

        <%-- Search for products --%>
        <form action="MainController" style="margin-top: 3%; margin-left: 5%; margin-bottom: 3%;">
            <input type="text" name="search" required="" value="<%= search%>" placeholder="Search products..." style="border: 2px solid gray; border-radius: 7px; padding: 4px; margin-right: 1%;">
            <input type="submit" name="action" value="Find" style="background-color: white; color:#69bb7e; border: 1px solid #69bb7e; border-radius: 12px; padding: 4px 15px;">
        </form>
        <form action="MainController" style="float:right; margin-right: 6%; margin-bottom: 2%;">
            <input type="submit" name="action" value="View" style="background-color: white; color:#69bb7e; border: 1px solid #69bb7e; border-radius: 12px; padding: 7px 15px;">
        </form>
            
        <%
            List<ProductDTO> listProduct = (List<ProductDTO>) request.getAttribute("LIST_PRODUCT");
            if (listProduct != null) {
                if (listProduct.size() > 0) {
        %>   
        <h3 style="margin: 3% 5%;">
            <%
                String message = (String) request.getAttribute("MESSAGE");
                if (message == null)
                    message = "";
            %>
            <%= message%>
        </h3>
        
        
        <table style="margin-left:5%; margin-bottom:5%; width: 90%;" class="table table-hover ">
            <thead>
                <tr>
                    <th scope="col">No</th>
                    <th scope="col">Product ID</th>
                    <th scope="col">Product Name</th>
                    <th scope="col">Image</th>
                    <th scope="col">Price</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Import Date</th>
                    <th scope="col">Using Date</th>
                    <th scope="col">Add to Cart</th>
                </tr>
            </thead>
            <%  int count = 1;
                long miliseconds = System.currentTimeMillis();
                Date today = new Date(miliseconds);
                for (ProductDTO product : listProduct) {
                    if (product.getQuantity() > 0 && product.getUsingDate().compareTo(today) > 0 && product.isStatus()) {
            %>
            <form action="MainController">
                <tbody>
                    <tr>
                        <td><%= count++%></td>
                        <td><%= product.getProductID()%></td>
                        <td><%= product.getProductName()%></td>
                        <td><img src="<%= product.getImage()%>" style="width: 12em; height: 8em;" class="img-fluid img-responsive"></td>
                        <td><%= product.getPrice()%></td>
                        <td><input type="number" name="cmbQuantity" min="1" required="">
                        <td><%= product.getImportDate()%></td>
                        <td><%= product.getUsingDate()%></td>
                        <td>
                            <input type="submit" name="action" value="Add" style="background-color: white; color:#69bb7e; border: 1px solid #69bb7e; border-radius: 12px; padding: 4px 30px;">          
                            <input type="hidden" name="productID" value="<%= product.getProductID()%>">
                            <input type="hidden" name="search" value="<%= search%>">
                        </td>
                    </tr>
                </tbody>
            </form>
            <%}
                }%>
        </table>
        
        <%
            String error = (String) request.getAttribute("ERROR");
            if (error == null)
                error = "";
        %>
        <%= error%>
        <%
                }
            }
        %>
        <jsp:include page="footer.jsp" flush="true"/>
    </body>
</html>
