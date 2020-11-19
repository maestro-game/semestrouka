<#ftl encoding='UTF-8'>

<#macro html page js=false>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/files/css/dist/main.min.css">
    <#if js>
        <script src="/files/source/js/${page}.js"></script>
    </#if>
    <title>${page?capitalize}</title>
</head>
<body>
<div class="container container_${page}">
    <#include "header.ftl">
    <#nested>
</div>

</#macro>
