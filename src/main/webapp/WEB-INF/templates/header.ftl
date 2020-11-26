<#include "macro.ftl">
<header class="fixed-top bg-white">
    <nav class="navbar navbar-expand-lg header__nav">
        <img class="header__logo" src="/files/images/live.svg"/>
        <ul class="navbar-nav ml-auto">
            <@_roleCheck "GUEST">
            <li>
                <a class="nav-item nav-link" href="/signUp">Sign Up</a>
            </li>
            <li>
                <a class="nav-item nav-link" href="/login">Login</a>
            </li>
            </@_roleCheck>
            <li>
                <a class="nav-item nav-link" href="/films">Films</a>
            </li>
            <@_roleCheck "GUEST" false>
            <li>
                <a class="nav-item nav-link" href="/profile">Profile</a>
            </li>
            <li>
                <a class="nav-item nav-link" href="/logout">Logout</a>
            </li>
            </@_roleCheck>
            <@_roleCheck "ADMIN">
            <li>
                <a class="nav-item nav-link" href="/users">Users</a>
            </li>
            </@_roleCheck>
        </ul>
        <form onsubmit="return search()">
            <input class="form-control" type="search" placeholder="Поиск фильма">
            <div class="dropdown-menu" id="search_result">
            </div>
        </form>
        <@_roleCheck "ADMIN">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addFilm">
                Добавить фильм
            </button>

            <div class="modal fade" id="addFilm" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Добавить фильм</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <@_form class="film" action="films"
                            fields=["name#text#'Название'",
                            "imgSrc#text#'Ссылка на постер'",
                            "year#number#'Год",
                            "genres#text#'Жанры через пробел'",
                            "actors#text#'Актёры через пробел'",
                            "awards#text#'Жанры через пробел'",
                            ""] btnValue="Добавить" />
                        </div>
                    </div>
                </div>
            </div>
        </@_roleCheck>
    </nav>
</header>
