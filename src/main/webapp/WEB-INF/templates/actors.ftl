<#include "base.ftl">
<#include "macro.ftl">
<@html "actors">
    <div class="actors">
        <#list actors as actor>
            <div class="actor">
                <p>${actor.getId()}</p>
                <p>${actor.getFullname()}</p>
                <p>${actor.getDescription()}</p>
            </div>
        </#list>
    </div>
    <@_roleCheck "ADMIN">
        <@_form class="actors" action="/actors"
        fields=["fullname#text#'Full name of actor'", "img#url#'URL of actor's profile picture'",
        "yearB#text#'Enter year of birth'", "yearD#text#'Enter year of death (or -1 if alive)'",
        "town#text#'Enter hometown'", "isDirector#checkbox",
        "genres#text#'Genres actor is envolved into (separated by \",\")'",
        "awards#text#'Awards (separated by \",\")'"]
        btnValue="Add actor" />
    </@>
</@>
