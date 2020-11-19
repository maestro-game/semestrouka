<#include "base.ftl">
<#include "macro.ftl">
<@html "profile">
    <div class="profile">
        <@_roleCheck role="GUEST" inclusion=false errorMessage=true>
            <h1>${user.username}</h1>
            <#list profileInfo.getGenres() as genre >
                <p>${genre}</p>
            </#list>
        </@>
    </div>
</@>
