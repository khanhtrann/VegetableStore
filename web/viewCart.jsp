<%-- 
    Document   : viewcart
    Created on : Feb 24, 2022, 1:15:47 PM
    Author     : vankh
--%>

<%@page import="store.user.UserDTO"%>
<%@page import="store.shopping.Item"%>
<%@page import="store.shopping.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
        <jsp:include page="meta.jsp" flush="true"/>
    </head>
    <body>
        <jsp:include page="header.jsp" flush="true"/>
        <h1 style="margin-left: 5%; margin-top: 3%;">Your selected item:</h1>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart != null) {
        %>

        <table style="margin: 5% 5%; width: 90%;" class="table table-hover">
            <tr>
                <th>No</th>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total</th>
                <th>Remove</th>
                <th>Edit</th>
            </tr>
            <%
                int count = 1;
                int total = 0;
                for (Item item : cart.getCart().values()) {
                    total += item.getPrice() * item.getQuantity();
            %>
            <form action="MainController">
                <tr>
                    <td><%= count++%></td>
                    <td><%= item.getId()%>
                        <input type="hidden" name="id" value="<%= item.getId()%>">
                    </td>
                    <td><%= item.getName()%></td>
                    <td><%= item.getPrice()%>VND</td>
                    <td><input type="number" name="quantity" value="<%= item.getQuantity()%>" min="1" required=""></td>
                    <td><%= item.getPrice() * item.getQuantity()%>VND</td>
                    <td>
                        <input type="submit" name="action" value="Remove" style="background-color: white; color:#69bb7e; border: 1px solid #69bb7e; border-radius: 12px; padding: 4px 15px;">
                    </td>
                    <td>
                        <input type="submit" name="action" value="Edit" style="background-color: white; color:#69bb7e; border: 1px solid #69bb7e; border-radius: 12px; padding: 4px 15px;">
                    </td>
                </tr>
            </form>
            <%
                }
            %>

        </table>
        <div style="display: flex; justify-content: space-between; margin: 2% 5%;">
            <a href="MainController?action=Find&search=" style=" color: #69bb7e; text-decoration: none;">Add More</a>
            <h2>Total: <%= total%>VND</h1>
        </div>
        <div style="margin-left: 85%;">
            <form action="MainController" style="margin: 2% 5%;">
                <input type="submit" name="action" value="Checkout" style="background-color: white; color:#69bb7e; border: 1px solid #69bb7e; border-radius: 12px; padding: 7px;">
                <input type="hidden" name="userID" value="<%= loginUser.getUserID()%>">
            </form>
        </div>
        <script src="https://www.paypal.com/sdk/js?client-id=ASaPflndRRnbf67urUh1wp_yVc7eeje-rfbVrw2MxTjksRBSrgzIJGMGC_iaZbzX-a2cDQbn3Dxejkxs&currency=USD"></script>
        <h4 style="margin-left: 77%; margin-top: 2%;">Purchase With Paypal</h4>
        <!-- Set up a container element for the button -->
        <input type="number" id="paypal_amount" style="margin-bottom: 1%; margin-left: 78%" placeholder="Type your amount...">
        <div id="paypal-button-container" style="width: 15%; margin-left: 78%"></div>

        <script>
            paypal.Buttons({

            // Sets up the transaction when a payment button is clicked
            createOrder: function (data, actions) {
            return actions.order.create({
            purchase_units: [{
            amount: {
            value: document.getElementById('paypal_amount').value // Can reference variables or functions. Example: `value: document.getElementById('...').value`
            }
            }]
            });
            },
                    // Finalize the transaction after payer approval
                    onApprove: function (data, actions) {
                    return actions.order.capture().then(function (orderData) {
                    // Successful capture! For dev/demo purposes:
                    console.log('Capture result', orderData, JSON.stringify(orderData, null, 2));
                            var transaction = orderData.purchase_units[0].payments.captures[0];
                            alert('Transaction ' + transaction.status + ': ' + transaction.id + '\n\nSee console for all available details');
                            // When ready to go live, remove the alert and show a success message within this page. For example:
                            // var element = document.getElementById('paypal-button-container');
                            // element.innerHTML = '';
                            // element.innerHTML = '<h3>Thank you for your payment!</h3>';
                            // Or go to another URL:  actions.redirect('thank_you.html');
                    });
                    },
            style: {
                layout:  'vertical',
                color:   'silver',
                shape:   'pill',
                label:   'paypal'
            }
            }).render('#paypal-button-container');

        </script>
        <%
        } else {
        %>
        <h2 style="margin: 3% 5%;">Your cart does not exist!</h2>
        <%
            }
        %>

        <jsp:include page="footer.jsp" flush="true"/>
    </body>
</html>
