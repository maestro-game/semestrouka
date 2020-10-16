<header class="header">
    <div class="header__logo">
        <svg class="header__logo-bottom"
             xmlns="http://www.w3.org/2000/svg">
            <image href="/files/images/kino.svg" />
        </svg>
        <svg class="header__logo-up"
             xmlns="http://www.w3.org/2000/svg">
            <image href="/files/images/live.svg" />
        </svg>
    </div>
    <nav class="header__nav">
        <#if user?? && user.getRole().name() == "GUEST">
            <a class="header__link" href="/signUp">Sign Up</a>
            <a class="header__link" href="/login">Login</a>
        <#elseif user?? && user.getRole().name() != "GUEST">
            <a class="header__link" href="/logout">Logout</a>
            <a class="header__link" href="/profile">Profile</a>
        </#if>
        <#if user?? && user.getRole().name() == "ADMIN">
            <a class="header__link" href="/users">Users</a>
        </#if>
    </nav>
</header>
