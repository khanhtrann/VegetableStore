<%-- 
    Document   : login
    Created on : Feb 12, 2022, 1:31:18 PM
    Author     : vankh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <meta name="google-signin-client_id" content="509457839207-nmqc2m5qrd6q9ak89q0c9mpmt92e0g1o.apps.googleusercontent.com">
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
    </head>
    <body>
        <h1>Input your information:</h1>
        <form action="MainController" method="POST" id="my_form" onsubmit="return submitUserForm();">
            User ID<input type="text" name="userID" required="" placeholder="Input your ID"><br>
            Password<input type="password" name="password" required="" placeholder="Input your password"><br>
            <div class="g-recaptcha" data-sitekey="6LeYBKkeAAAAABteImBjEtjr2BkmMOQqYkj4M9OH" data-callback="verifyCaptcha"></div>
            <div id="g-recaptcha-error"></div>
            <br/>
            <input type="submit" name="action" value="Login">
            <input type="reset" value="Reset"><br>
        </form>
        <%
            String error = (String) request.getAttribute("ERROR");
            if (error == null)
                error = "";
        %>

        <%= error%>
        <br>
        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:6969/VegetableStore/login-google&response_type=code&client_id=509457839207-nmqc2m5qrd6q9ak89q0c9mpmt92e0g1o.apps.googleusercontent.com&approval_prompt=force">Login With Google</a><br>

        <script>
            var recaptcha_response = '';
            function submitUserForm() {
                if (recaptcha_response.length == 0) {
                    document.getElementById('g-recaptcha-error').innerHTML = '<span style="color:red;">This field is required.</span>';
                    return false;
                }
                return true;
            }

            function verifyCaptcha(token) {
                recaptcha_response = token;
                document.getElementById('g-recaptcha-error').innerHTML = '';
            }
        </script>
    </body>
</html>
