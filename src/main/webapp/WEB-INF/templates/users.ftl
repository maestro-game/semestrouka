<#include "base.ftl">
<#include "macro.ftl">
<@html "users">
    <@_roleCheck role="ADMIN" errorMessage=true>
        <#list users as u>
            <span class="user__name">${u}</span>
        </#list>
    </@>
</@>
