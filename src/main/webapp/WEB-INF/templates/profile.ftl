<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/files/css/dist/main.min.css">
    <title>Profile</title>
</head>
<body>
<div class="container">
    <#include "base.ftl">
    <#if user?? && user.getRole().name() != "GUEST">
        <h1>${user.username}</h1>
    <#else>
        <h1>You have no rights to see this page.</h1>
    </#if>
</div>
</body>
</html>
