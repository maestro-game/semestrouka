<#include "base.ftl">
<#include "macro.ftl">
<@html "films" true>
    <div class="search">
        <@_form class="search" action="filterFilms"
        fields=["img#url", "genres#text", "actors#text", "director#text"]
        btnValue="Search films" />
    </div>
    <div class="films">
        <#list films as film>
            <div class="film">
                <a href="/film?filmId=${film.getId()}">
                    <#assign description = film.getDescription()>
                    <img class="film__poster" src="${description.getImg()}" alt="film poster"/>
                    <p class="film__filmname">${film.getName()}</p>
                    <#list description.getGenres() as genre>
                        <p class="film__genre">${genre}</p>
                    </#list>
                    <#list description.getActors() as actor>
                        <p class="film__actor">${actor}</p>
                    </#list>
                    <#list description.getAwards() as award>
                        <p class="film__award">${award}</p>
                    </#list>
                </a>
            </div>
        </#list>
        <@_roleCheck "ADMIN">
            <@_form class="film" action="films"
            fields=["name#text#'Film name'",
            "imgSrc#text#'Film poster image source URL'",
            "genres#text#'Genres divided by space'"] btnValue="Add film" />
        </@>
    </div>
</@>
