function send() {
    prompt("search");
    let href = "/test";
    let content = "test"
    $('search_result').append(`<a class="dropdown-item" href="${href}">${content}</a>`);
    return false;
}