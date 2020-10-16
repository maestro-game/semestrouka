 window.addEventListener('load', () => {
    let node = document.createElement("p");
    let text = document.createTextNode("Remember me:");

    node.appendChild(text);
    node.setAttribute("class", "login__rememberMe");

    let rememberMe = document.querySelector("#rememberMe");
    rememberMe.before(node);
 });