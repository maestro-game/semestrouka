<#ftl encoding='UTF-8'>

<#macro html page js = false>
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
        <link rel="stylesheet" href="/files/css/dist/custom.css">
        <#if page == "profile">
            <link rel="stylesheet"
                  href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
        </#if>
        <script
                src="https://code.jquery.com/jquery-3.5.1.min.js"
                integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"
                integrity="sha384-LtrjvnR4Twt/qOuYxE721u19sVFLVSA4hf/rRt6PrZTmiPltdZcI7q7PXQBYTKyf"
                crossorigin="anonymous"></script>
        <#if page == "profile">
            <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
        </#if>
        <link rel="stylesheet" href="/files/css/dist/main.css">
        <link rel="stylesheet" href="/files/css/dist/${page}.css">
        <title>${page?capitalize}</title>
    </head>
    <body>
    <div class="main_container container_${page}">
        <#include "header.ftl">
        <div class="container ${page}">
            <#nested>
        </div>
    </div>
    <script src="/files/source/js/main.js"></script>
    <#if js>
        <script src="/files/source/js/${page}.js"></script>
    </#if>
    <@_roleCheck "ADMIN">
        <div class="modal fade" id="addFilm" tabindex="-1" role="dialog" aria-labelledby="addFilmModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Add film</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <@_form class="film" action="films"
                        fields=["name#text#'Title'",
                        "imgSrc#url#'URL of film's poster picture'",
                        "year#number#'Year'",
                        "genres#text#'Genres (separated by \",\")'",
                        "actors#text#'Actors (separated by \",\")'",
                        "awards#text#'Awards (separated by \",\")'"] btnValue="Add" />
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="addActor" tabindex="-1" role="dialog" aria-labelledby="addActorModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Add actor</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <@_form class="actors" action="/actors"
                        fields=["fullname#text#'Full name of actor'",
                        "img#url#'URL of actor's profile picture'",
                        "yearB#number#'Enter year of birth'",
                        "yearD#number#'Enter year of death (or -1 if alive)'",
                        "town#text#'Enter hometown'",
                        "genres#text#'Genres actor is envolved into (separated by \",\")'",
                        "awards#text#'Awards (separated by \",\")'",
                        "isDirector#checkbox"]
                        btnValue="Add actor" />
                    </div>
                </div>
            </div>
        </div>

        <script src="/files/source/js/films.js"></script>
        <script src="/files/source/js/actors.js"></script>
    </@_roleCheck>
    </body>
    </html>
</#macro>
