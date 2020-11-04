"use strict";

function _templateObject6() {
    var data = _taggedTemplateLiteral(["\n                    </a>\n                </div>\n            "]);

    _templateObject6 = function _templateObject6() {
        return data;
    };

    return data;
}

function _templateObject5() {
    var data = _taggedTemplateLiteral([""]);

    _templateObject5 = function _templateObject5() {
        return data;
    };

    return data;
}

function _templateObject4() {
    var data = _taggedTemplateLiteral(["\n                <div class=\"film\">\n                    <a href=\"/film?filmId=", "\">\n                        <img class=\"film__poster\" src=\"", "\" alt=\"film poster\"/>\n                        <p class=\"film__filename\">", "</p>\n                        "]);

    _templateObject4 = function _templateObject4() {
        return data;
    };

    return data;
}

function _templateObject3() {
    var data = _taggedTemplateLiteral([""]);

    _templateObject3 = function _templateObject3() {
        return data;
    };

    return data;
}

function _templateObject2() {
    var data = _taggedTemplateLiteral(["\n                    <p class=", ">", "</p>\n                "]);

    _templateObject2 = function _templateObject2() {
        return data;
    };

    return data;
}

function _templateObject() {
    var data = _taggedTemplateLiteral([""]);

    _templateObject = function _templateObject() {
        return data;
    };

    return data;
}

function _taggedTemplateLiteral(strings, raw) {
    if (!raw) {
        raw = strings.slice(0);
    }
    return Object.freeze(Object.defineProperties(strings, {raw: {value: Object.freeze(raw)}}));
}

window.addEventListener("load", function () {
    document.querySelector(".search__form").addEventListener("submit", handler);
});

function handler(event) {
    var form, url, formData, responseData, neededNodeInnerHTML, _iteratorNormalCompletion, _didIteratorError,
        _iteratorError, _loop, _iterator, _step;

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
                    neededNodeInnerHTML = document.querySelector(".films").innerHTML; // neededNodeInnerHTML = "";

                    _iteratorNormalCompletion = true;
                    _didIteratorError = false;
                    _iteratorError = undefined;
                    _context.prev = 12;

                    _loop = function _loop() {
                        var film = _step.value;

                        var mapDescriptionArray = function mapDescriptionArray(_, name) {
                            return ""(_templateObject2(), 'film__' + name, _)(_templateObject());
                        };

                        neededNodeInnerHTML += ""(_templateObject4(), film.id, film.img, film.name)(_templateObject3()) + film.genres.map(function (genre) {
                            return mapDescriptionArray(genre, "genre");
                        }) + +film.actors.map(function (actor) {
                            return mapDescriptionArray(actor, "genre");
                        }) + ""(_templateObject6())(_templateObject5());
                    };

                    for (_iterator = JSON.parse(responseData)[Symbol.iterator](); !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                        _loop();
                    }

                    _context.next = 21;
                    break;

                case 17:
                    _context.prev = 17;
                    _context.t0 = _context["catch"](12);
                    _didIteratorError = true;
                    _iteratorError = _context.t0;

                case 21:
                    _context.prev = 21;
                    _context.prev = 22;

                    if (!_iteratorNormalCompletion && _iterator["return"] != null) {
                        _iterator["return"]();
                    }

                case 24:
                    _context.prev = 24;

                    if (!_didIteratorError) {
                        _context.next = 27;
                        break;
                    }

                    throw _iteratorError;

                case 27:
                    return _context.finish(24);

                case 28:
                    return _context.finish(21);

                case 29:
                    _context.next = 34;
                    break;

                case 31:
                    _context.prev = 31;
                    _context.t1 = _context["catch"](3);
                    console.log(_context.t1);

                case 34:
                case "end":
                    return _context.stop();
            }
        }
    }, null, null, [[3, 31], [12, 17, 21, 29], [22, , 24, 28]]);
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
                        method: "post",
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
