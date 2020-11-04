<#ftl encoding='UTF-8'>

<#macro html page>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/files/css/dist/main.min.css">
    <#--    <script src="/files/js/dist/main.prod.js"></script>-->
    <#list ['film', 'films', 'login'] as withJsPage >
        <#if page == withJsPage>
            <script src="/files/js/dist/${page}.prod.js"></script>
            <#break>
        </#if>
    </#list>
    <#--    <script src="/files/source/js/${page}.js"></script>-->
    <title>${page?capitalize}</title>
</head>
<body>
<div class="container container_${page}">
    <#include "header.ftl">
    <#nested>
</div>

</#macro>
