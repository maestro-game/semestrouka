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
</@>
