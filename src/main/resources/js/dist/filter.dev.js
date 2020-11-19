"use strict";

window.addEventListener("load", function () {
    document.querySelector(".filter__form").addEventListener("submit", handler);
});

var mapDescriptionArray = function mapDescriptionArray(_, name) {
    return "<p class=".concat('film__' + name, ">").concat(_, "</p>");
};

function handler(event) {
    var form, url, formData, responseData, neededNodeHTML, _iteratorNormalCompletion, _didIteratorError, _iteratorError,
        _iterator, _step, film;

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
                    neededNodeHTML = document.querySelector(".filter").innerHTML;
                    _iteratorNormalCompletion = true;
                    _didIteratorError = false;
                    _iteratorError = undefined;
                    _context.prev = 12;

                    for (_iterator = responseData[Symbol.iterator](); !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                        film = _step.value;
                        console.log(film);
                        neededNodeHTML += "\
            <div class=\"film\">\
                <a href=\"/film?filmId=" + film.id + "\">\
                    <img class=\"film__poster\" src=\"" + film.description.img + "\" alt=\"film poster\"/>\
                    <p class=\"film__filename\">" + film.name + "</p>" + film.description.genres.map(function (genre) {
                            return mapDescriptionArray(genre, "genre");
                        }) + +film.description.actors.map(function (actor) {
                            return mapDescriptionArray(actor, "actor");
                        }) + +film.description.awards.map(function (award) {
                            return mapDescriptionArray(award, "award");
                        }) + "</a></div>";
                    }

                    _context.next = 20;
                    break;

                case 16:
                    _context.prev = 16;
                    _context.t0 = _context["catch"](12);
                    _didIteratorError = true;
                    _iteratorError = _context.t0;

                case 20:
                    _context.prev = 20;
                    _context.prev = 21;

                    if (!_iteratorNormalCompletion && _iterator["return"] != null) {
                        _iterator["return"]();
                    }

                case 23:
                    _context.prev = 23;

                    if (!_didIteratorError) {
                        _context.next = 26;
                        break;
                    }

                    throw _iteratorError;

                case 26:
                    return _context.finish(23);

                case 27:
                    return _context.finish(20);

                case 28:
                    _context.next = 33;
                    break;

                case 30:
                    _context.prev = 30;
                    _context.t1 = _context["catch"](3);
                    console.log(_context.t1);

                case 33:
                case "end":
                    return _context.stop();
            }
        }
    }, null, null, [[3, 30], [12, 16, 20, 28], [21, , 23, 27]]);
}

function postForm(url, formData) {
    var jsonData, response;
    return regeneratorRuntime.async(function postForm$(_context2) {
        while (1) {
            switch (_context2.prev = _context2.next) {
                case 0:
                    jsonData = JSON.stringify(Object.fromEntries(formData.entries()));
                    _context2.next = 3;
                    return regeneratorRuntime.awrap(fetch(url, {
                        method: "post",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: jsonData
                    }));

                case 3:
                    response = _context2.sent;
                    _context2.next = 6;
                    return regeneratorRuntime.awrap(response.json());

                case 6:
                    return _context2.abrupt("return", _context2.sent);

                case 7:
                case "end":
                    return _context2.stop();
            }
        }
    });
}
