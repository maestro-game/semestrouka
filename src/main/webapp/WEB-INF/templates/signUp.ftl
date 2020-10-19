<#include "base.ftl">
<#include "macro.ftl">
<@html "signUp">
    <@_roleCheck "GUEST">
        <div class="signUp">
            <@_errors class="signUp"/>
            <div class="content signUp__content">
                <@_form class="signUp" action="signUp"
                fields=["email#email#YourEmail@example.com", "username#text#'Enter your username'",
                "password#password#'Enter your password'",
                "passwordConfirm#password#'Confirm your password'",
                "phone#tel#'Enter your phone'"
                ] btnValue="Sign Up" />
            </div>
        </div>
    </@>
</@>
