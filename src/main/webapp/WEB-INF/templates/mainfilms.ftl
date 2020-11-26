<#include "base.ftl">
<#include "macro.ftl">
<@html "films" true>
    <div class="search">
        <div class="modal-body">
            <@_form action="filter" class="filter"
            fields=["year#text#'Year'",
            "genres#text#'Genres (separated by \",\")'",
            "actors#text#'Actors (separated by \",\")'",
            "awards#text#'Awards (separated by \",\")'"]
            btnValue="Search films"
            />
        </div>
    </div>
    <div class="films__list">
        <#list films as film>
            <div class="film row justify-content-center">
                <#assign description = film.getDescription()>
                <a href="film?filmId=${film.id}">
                    <img class="col-md-auto film_avatar_img"
                         src="${description.img!"https://www.cameraegg.org/wp-content/uploads/2013/03/Canon-EOS-100D-Rebel-SL1-Sample-Image.jpg"}">
                </a>
                <span class="col-md-auto description">
                <h2 class="film_name">${film.getName()}</h2>
                <div class="userinfo">
                    <ul class="list-group list-group-flush">
                        <#if description.year??>
                            <li class="list-group-item">Year<span class="info_row_value">${description.year}</span></li>
                        </#if>
                        <li class="list-group-item">Genres
                            <#list description.getGenres() as genre>
                                <span class="info_row_value">${genre}</span>
                            </#list>
                        </li>
                        <li class="list-group-item">Awards
                            <#list description.awards as award>
                                <span class="info_row_value">${award}</span>
                            </#list>
                        </li>
                        <li class="list-group-item">Actors
                            <#list description.actors as actor>
                                <span class="info_row_value">${actor}</span>
                            </#list>
                        </li>
                    </ul>
                </div>
            </span>
            </div>
        </#list>
    </div>

    <script src="/files/source/js/filter.js"></script>
</@>
