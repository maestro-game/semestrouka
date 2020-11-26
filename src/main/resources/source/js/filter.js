window.addEventListener("load", () => {
    document.querySelector(".filter__form").addEventListener("submit", handlerFilter);
});

let mapDescriptionArray2 = (_) => `<span class="info_row_value">${_}</span>`;

async function handlerFilter(event) {
    event.preventDefault();

    const form = event.currentTarget;
    const url = form.action;

    try {
        const formData = new FormData(form);
        const responseData = await postFormFilter(url, formData);

        let neededNodeHTML = document.querySelector(".films__list");

        neededNodeHTML.html('');

        for (let film of responseData) {
            console.log(film);
            let text = `<a href="film?filmId=${film.id}">
                    <img class="col-md-auto film_avatar_img"
                         src="${film.description.img}">
                </a>
                <span class="col-md-auto description">
                <h2 class="film_name">${film.name}</h2>
                <div class="userinfo">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">Year
                            <span class="info_row_value">${film.description.year}</span>
                        </li>
                        <li class="list-group-item">Genres
                            ${film.description.genres.map((genre) => mapDescriptionArray2(genre))}
                        </li>
                        <li class="list-group-item">Awards
                            ${film.description.actors.map((actor) => mapDescriptionArray2(actor))}
                        </li>
                        <li class="list-group-item">Actors
                            ${film.description.awards.map((award) => mapDescriptionArray2(award))}
                        </li>
                    </ul>
                </div>
            </span>
            </div>`;
            neededNodeHTML += text;
        }
    } catch (err) {
        console.log(err);
    }
}

async function postFormFilter(url, formData) {

    const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));

    const response = await fetch(url, {
        method: "post",
        headers: {"Content-Type": "application/json"},
        body: jsonData
    });

    return await response.json();
}
