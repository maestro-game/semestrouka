<#include "base.ftl">
<#include "macro.ftl">
<@html "login">
    <@_roleCheck "GUEST">
        <@_errors class="login" />
        <div class="content login__content">
            <@_form class="login" action="login"
            fields=["username#text#'Enter your username'",
            "password#password#'Enter your password'",
            "rememberMe#checkbox"
            ] btnValue="Login">
                <div class="login__rememberMe">
                    <div class="rememberMe__label">
                        Remember me
                    </div>
                </div>
            </@_form>
        </div>
    </@>
</@>
