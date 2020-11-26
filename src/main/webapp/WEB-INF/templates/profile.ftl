<#include "base.ftl">
<#include "macro.ftl">
<@html "profile" true>
    <@_roleCheck role="GUEST" inclusion=false errorMessage=true>
        <div class="top_content row justify-content-center">
            <img class="col-md-auto avatar_img"
                 src="https://yt3.ggpht.com/a/AATXAJyOHm7BOqYcGiqatNSFkca-4XYwc5-0ysIN-3yFgg=s900-c-k-c0xffffffff-no-rj-mo">
            <span class="col-md-auto description">
                <h1 id="username" class="username">${profileInfo.username}</h1>
                <@_roleCheck role="ADMIN">
                    <h5>Admin</h5>
                </@>
                <div class="userinfo">
                    <form onsubmit="return send()">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                <div class="row">
                                    <label for="email" class="col-sm-2 col-form-label">Email</label>
                                    <div class="col-sm-10">
                                        <input id="email" type="email" readonly class="form-control-plaintext info_row_value" value="${profileInfo.email}">
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <label for="name" class="col-sm-2 col-form-label">Name</label>
                                    <div class="col-sm-10">
                                        <input id="name" type="text" readonly class="form-control-plaintext info_row_value" value="${profileInfo.name!'Not stated'}">
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <label for="surName" class="col-sm-2 col-form-label">Surname</label>
                                    <div class="col-sm-10">
                                        <input id="surName" type="text" readonly class="form-control-plaintext info_row_value" value="${profileInfo.surname!'Not stated'}">
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <label for="phone" class="col-sm-2 col-form-label">Phone</label>
                                    <div class="col-sm-10">
                                        <input id="phone" type="tel" readonly class="form-control-plaintext info_row_value" value="${profileInfo.phone!'Not stated'}">
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </form>
                    <button id="editBtn" class="btn btn-primary" onclick="edit()">Edit</button>
                    <button id="submitBtn" class="btn btn-primary invisible" onclick="send()">Submit</button>
                </div>
            </span>
        </div>
        <div class="liked_genres">
            <form action="/profile">
                <select class="selectpicker" data-style="btn-secondary" multiple data-live-search="true"
                        title="Choose favorite genres"
                        data-width="75%">
                    <#list profileInfo.genres as genre>
                        <#if genre != "">
                            <option selected="true">${genre}</option>
                        </#if>
                    </#list>
                </select>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </@>
</@>
