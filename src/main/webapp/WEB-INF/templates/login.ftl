<#include "base.ftl">
<#include "macro.ftl">
<@html "login" true>
    <div class="login">
        <@_roleCheck "GUEST">
            <@_errors class="login" />
            <div class="content login__content">
                <@_form class="login" action="login"
                fields=["username#text#'Enter your username'",
                "password#password#'Enter your password'",
                "rememberMe#checkbox"
                ] btnValue="Login" />
            </div>
        </@>
    </div>
</@>
