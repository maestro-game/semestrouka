<#include "base.ftl">
<#include "macro.ftl">
<@html "actors">
    <div class="top_content">
        <#list actors as actor>
            <#assign desc = actor.getDescription()>
            <div class="justify-content-start my_flex">
                <a href="/actor?actorId=${actor.getId()}">
                    <img class="col-md-auto avatar_img"
                         src="${desc.img}">
                </a>
                <span class="description">
                    <h1 class="username">${actor.getFullname()}</h1>
                    <div class="userinfo">
                        <h4>genres</h4>
                        <div>
                            <#list desc.getGenres() as genre>
                                <span class="info_row_value">${genre}</span>
                            </#list>
                        </div>
                    </div>
                </span>
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
