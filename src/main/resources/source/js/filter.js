window.addEventListener("load", () => {
    document.querySelector(".filter__form").addEventListener("submit", handler);
});

let mapDescriptionArray = (_, name) => `<p class=${'film__' + name}>${_}</p>`

async function handler(event) {
    event.preventDefault();

    const form = event.currentTarget;
    const url = form.action;

    try {
        const formData = new FormData(form);
        const responseData = await postForm(url, formData);

        let neededNodeHTML = document.querySelector(".filter").innerHTML;

        for (let film of responseData) {
            console.log(film);
            neededNodeHTML += "\
            <div class=\"film\">\
                <a href=\"/film?filmId=" + film.id + "\">\
                    <img class=\"film__poster\" src=\"" + film.description.img + "\" alt=\"film poster\"/>\
                    <p class=\"film__filename\">" + film.name + "</p>"
                + film.description.genres.map((genre) => mapDescriptionArray(genre, "genre")) +
                +film.description.actors.map((actor) => mapDescriptionArray(actor, "actor")) +
                +film.description.awards.map((award) => mapDescriptionArray(award, "award")) +
                "</a></div>";
        }
    } catch (err) {
        console.log(err);
    }
}

async function postForm(url, formData) {

    const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));

    const response = await fetch(url, {
        method: "post",
        headers: {"Content-Type": "application/json"},
        body: jsonData
    });

    return await response.json();
}
