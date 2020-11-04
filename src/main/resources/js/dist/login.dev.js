"use strict";

window.addEventListener('load', function () {
    var node = document.createElement("p");
    var text = document.createTextNode("Remember me:");
    node.appendChild(text);
    node.setAttribute("class", "login__rememberMe");
    var rememberMe = document.querySelector("#rememberMe");
    rememberMe.before(node);
});
