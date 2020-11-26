<#include "base.ftl">
<#include "macro.ftl">
<@html "actor" false>
    <@_errors "actor" />
    <div class="top_content row justify-content-center">
        <#assign desc = actor.getDescription()>
        <div class="top_content row justify-content-center">
            <img class="col-md-auto avatar_img"
                 src="${desc.img}">
            <span class="col-md-auto description">
                <h1 class="username">${actor.fullname}</h1>
                <#if desc.director!false>
                    <h3>director</h3>
                </#if>
                <div class="userinfo">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            born<span class="info_row_value">${desc.yearB}</span>
                        </li>
                        <li class="list-group-item">
                            dead<span class="info_row_value">${desc.yearD!"Not yet)"}</span>
                        </li>
                        <li class="list-group-item">
                            town<span class="info_row_value">${desc.town}</span>
                        </li>
                        <li class="list-group-item">genres
                            <#list desc.getGenres() as genre>
                                <span class="info_row_value">${genre}</span>
                            </#list>
                        </li>
                        <li class="list-group-item">awards
                            <#list desc.awards as award>
                                <span class="info_row_value">${award}</span>
                            </#list>
                        </li>
                    </ul>
                </div>
            </span>
        </div>
    </div>
</@>
