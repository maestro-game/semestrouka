<#include "base.ftl">
<#include "macro.ftl">
<@html "actors">
    <div class="top_content">
        <#list actors as actor>
            <#assign desc = actor.getDescription()>
            <div class="justify-content-start my_flex">
                <a href="/actor?actorId=${actor.getId()}">
                    <#if desc.img??>
                        <img class="col-md-auto avatar_img"
                             src="${desc.img}">
                    <#else>
                        <img class="col-md-auto avatar_img"
                             src="https://www.cameraegg.org/wp-content/uploads/2013/03/Canon-EOS-100D-Rebel-SL1-Sample-Image.jpg">
                    </#if>
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
