let name = $("#name");
let surName = $("#surName");
let phone = $("#phone");
let editBtn = $("#editBtn");
let submitBtn = $("#submitBtn");
let username = $("#username").text();

function edit() {
    name.removeAttr("readonly");
    name.addClass("border border-primary");
    surName.removeAttr("readonly");
    surName.addClass("border border-primary");
    phone.removeAttr("readonly");
    phone.addClass("border border-primary");
    editBtn.addClass("invisible");
    submitBtn.removeClass("invisible");
}

function fix() {
    name.attr("readonly");
    name.removeClass("border border-primary");
    surName.attr("readonly");
    surName.removeClass("border border-primary");
    phone.attr("readonly");
    phone.removeClass("border border-primary");
    editBtn.removeClass("invisible");
    submitBtn.addClass("invisible");
}

function send() {
    $.ajax({
        type: 'PUT',
        url: '/profile',
        contentType: "application/json",
        data: `{"username": "${username}", "name": "${name.val()}", "surname": "${surName.val()}", "phone": "${phone.val()}"}`,
        success: function (data) {
            fix();
        },
        error: function (data) {
            location.reload();
        }
    });
}