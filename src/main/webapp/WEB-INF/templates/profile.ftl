<#include "base.ftl">
<#include "macro.ftl">
<@html "profile">
    <@_roleCheck role="GUEST" inclusion=false errorMessage=true>
        <h1>${user.username}</h1>
    </@>
</@>
