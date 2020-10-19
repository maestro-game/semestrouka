<#include "base.ftl">
<#include "macro.ftl">
<@html "films">
    <div class="film">
        <#list films as film>
            <#assign description = film.getDescription()>
            <img class="film__poster" src="${description.getImg()}" alt="film poster"/>
            <p class="film__filmname">${film.getName()}</p>
            <#list description.getGenres() as genre>
                <p class="film__genre">${genre}</p>
            </#list>
        </#list>
        <@_roleCheck "ADMIN">
            <@_form class="film" action="films"
            fields=["name#text#'Film name'",
            "imgSrc#text#'Film poster image source URL'",
            "genres#text#'Genres divided by space'"] btnValue="Add film" />
        </@>
    </div>
</@>
