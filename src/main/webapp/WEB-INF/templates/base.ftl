<#ftl encoding='UTF-8'>

<#macro html page>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
              integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
              crossorigin="anonymous">
        <#--ONLY FOR PROFILE -->
        <link rel="stylesheet"
              href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
        <#--ONLY FOR PROFILE /-->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
                integrity="sha256-4+XzXVhsDmqanXGHaHvgh1gMQKX40OUvDEBTu8JcmNs=" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"
                integrity="sha384-LtrjvnR4Twt/qOuYxE721u19sVFLVSA4hf/rRt6PrZTmiPltdZcI7q7PXQBYTKyf"
                crossorigin="anonymous"></script>
        <#--ONLY FOR PROFILE -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
        <#--ONLY FOR PROFILE /-->

        <link rel="stylesheet" href="/files/css/dist/main.css">
        <link rel="stylesheet" href="/files/css/dist/${page}.css">
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
    <div class="main_container container_${page}">
        <#include "header.ftl">
        <div class="container ${page}">
            <#nested>
        </div>
    </div>
    </body>
    </html>
</#macro>
