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
    </nav>
</header>
