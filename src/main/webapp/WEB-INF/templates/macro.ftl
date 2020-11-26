<#macro _errors class>

    <#if errors??>
        <#list errors?values as e>
            <p class="${class}__error error">${e}</p>
        </#list>
    </#if>

</#macro>

<#-- Fields should contain list of elements formatted like
        "fieldName#fieldType" or "fieldName#fieldType#'field placeHolder'" -->
<#macro _form fields class action method="post" label=false btnValue="Submit">

    <#assign pattern="^((\\+7|7|8)+([0-9])\{10})$">

    <form action="${action}" method="${method}" class="${class}__form">
        <#list fields as field>
            <#assign fieldVars = field?split("#")>
            <#assign fieldName = fieldVars[0]>
            <#assign fieldType = fieldVars[1]>
            <#if label>
                <label for="${fieldName}" class="${class}__label">Enter your ${fieldName}:</label>
            </#if>
            <input type="${fieldType}" name="${fieldName}" id="${fieldName}"
                   class="${(fieldName == "rememberMe")?then(
                   class + "__" + fieldName, class + "__field")} form-control"
                    <#if fieldVars[2]??>
                   placeholder=${fieldVars[2]}
                   </#if>
                   <#switch fieldType>
                   <#case "tel">
                   pattern=${pattern}
                <#break>
                    <#case "username">
                    <#case "password">
                <#case "email">
                    required
                    <#break>
                <#case "range">
                    <#if fieldName == "mark">
                        min="-1"
                        max="1"
                    <#else>
                        min="1"
                        max="5"
                    </#if>
                    </#switch>
            />
        </#list>
        <input type="submit" value="${btnValue}" class="btn btn-primary"/>
        <#nested>
    </form>

</#macro>

<#macro _roleCheck role inclusion=true errorMessage=false>
    <#if user??>
        <#if !inclusion>
            <#if user.getRole().name() != role>
                <#nested>
            <#elseif errorMessage>
                <p class="not_allowed">You are not allowed to see this page</p>
            </#if>
        <#else>
            <#if user.getRole().name() == role>
                <#nested>
            <#elseif errorMessage>
                <p class="not_allowed">You are not allowed to see this page</p>
            </#if>
        </#if>
    </#if>
</#macro>
