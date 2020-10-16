<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/files/css/dist/main.min.css">
    <title>Sign up</title>
</head>
<body>
<div class="container">
    <#include "base.ftl">
    <@_errors class="signUp"/>
    <#if user?? && user.getRole().name() == "GUEST">
        <div class="content signUp__content">
            <@_form class="signUp" action="signUp"
                    fields=["email#email#YourEmail@example.com", "username#text#'Enter your username'",
                            "password#password#'Enter your password'",
                            "passwordConfirm#password#'Confirm your password'",
                            "phone#tel#'Enter your phone'"
                    ] btnValue="Sign Up" />
        </div>
    </#if>
</div>
</body>
</html>
