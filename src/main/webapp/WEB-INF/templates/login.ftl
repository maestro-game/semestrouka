<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/files/css/dist/main.min.css">
    <script src="/files/js/dist/main.prod.js"></script>
    <title>Login</title>
</head>
<body>
<div class="container">
    <#include "base.ftl">
    <div class="login">
        <#if user?? && user.getRole().name() == "GUEST">
            <@_errors class="login" />
            <div class="content login__content">
                <@_form class="login" action="login"
                fields=["username#text#'Enter your username'",
                        "password#password#'Enter your password'",
                        "rememberMe#checkbox"
                ] btnValue="Login" />
            </div>
        </#if>
    </div>
</div>
</body>
</html>
