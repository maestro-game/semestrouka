"use strict";

window.addEventListener('load', function () {
    return ["comments", "mark", "reviews", "review"].forEach(function (c) {
        return document.querySelectorAll("." + c + "__form").forEach(function (form) {
            return form.addEventListener("submit", handler);
        });
    });
});

function handler(event) {
    var form, url, formData, responseData, className, neededNode;
    return regeneratorRuntime.async(function handler$(_context) {
        while (1) {
            switch (_context.prev = _context.next) {
                case 0:
                    event.preventDefault();
                    form = event.currentTarget;
                    url = form.action;
                    _context.prev = 3;
                    formData = new FormData(form);
                    _context.next = 7;
                    return regeneratorRuntime.awrap(postForm(url, formData));

                case 7:
                    responseData = _context.sent;
                    className = form.className.split("__")[0];

                    if (className !== "mark" && className !== "review") {
                        neededNode = document.querySelector("." + className + "__widen");

                        if (className === "comments") {
                            neededNode.innerHTML += "<div class=\"comment\">\n                                <p class=\"comment__user\">".concat(responseData["personUsername"], "</p>\n                                <p class=\"comment__text\">").concat(responseData["reviewText"], "</p>\n                            </div>");
                        } else if (className === "reviews") {
                            neededNode.innerHTML += "<div class=\"review\">\n                                <p class=\"review__user\">".concat(responseData["personUsername"], "</p>\n                                <p class=\"review__text\">").concat(responseData["reviewText"], "</p>\n                                <p class=\"review__mark\">").concat(responseData["mark"], "</p>\n                                <p class=\"review__rating\">").concat(responseData["rating"], "</p>\n                                <form action=\"/film?filmId=").concat(responseData["filmId"], "&reviewId=").concat(responseData["id"], "\" class=\"review__form\">\n                                    <input type=\"range\" name=\"rating\" id=\"rating\" class=\"review__field\" min=\"1\" max=\"5\">\n                                    <input type=\"submit\" value=\"Rate review\" class=\"review__btn\">\n                                </form>\n                            </div>");
                            console.log(window.dispatchEvent(new Event("load")));
                        }
                    } else if (className === "mark") {
                        neededNode = document.querySelector("." + className + "__avg");
                        neededNode.innerHTML = "Average: " + responseData.toFixed(2);
                    } else {
                        neededNode = document.querySelector("." + className + "__rating");
                        neededNode.innerHTML = responseData.toFixed(2);
                    }

                    _context.next = 15;
                    break;

                case 12:
                    _context.prev = 12;
                    _context.t0 = _context["catch"](3);
                    console.log(_context.t0);

                case 15:
                case "end":
                    return _context.stop();
            }
        }
    }, null, null, [[3, 12]]);
}

function postForm(url, formData) {
    var jsonData, response;
    return regeneratorRuntime.async(function postForm$(_context2) {
        while (1) {
            switch (_context2.prev = _context2.next) {
                case 0:
                    jsonData = JSON.stringify(Object.fromEntries(formData.entries()));
                    console.log(url, url.includes("reviewId"));
                    _context2.next = 4;
                    return regeneratorRuntime.awrap(fetch(url, {
                        method: url.includes("reviewId") ? "put" : "post",
                        // mode: "cors",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: jsonData
                    }));

                case 4:
                    response = _context2.sent;
                    _context2.next = 7;
                    return regeneratorRuntime.awrap(response.json());

                case 7:
                    return _context2.abrupt("return", _context2.sent);

                case 8:
                case "end":
                    return _context2.stop();
            }
        }
    });
}
