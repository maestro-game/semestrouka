window.addEventListener("load", () => {
    document.querySelector(".actors__form").addEventListener("submit", handlerActors);
})


async function handlerActors(event) {
    event.preventDefault();

    const form = event.currentTarget;
    const url = form.action;

    try {
        const formData = new FormData(form);
        const responseData = await postFormActors(url, formData);

        let neededNodeInnerHTML = document.querySelector(".films").innerHTML;

        // neededNodeInnerHTML = "";

        for (let film of JSON.parse(responseData)) {
            let mapDescriptionArray = (_, name) =>
                ```
                    <p class=${'film__' + name}>${_}</p>
                ```;

            neededNodeInnerHTML +=
                ```
                <div class="film">
                    <a href="/film?filmId=${film.id}">
                        <img class="film__poster" src="${film.img}" alt="film poster"/>
                        <p class="film__filename">${film.name}</p>
                        ```
                + film.genres.map((genre) => mapDescriptionArray(genre, "genre")) +
                +film.actors.map((actor) => mapDescriptionArray(actor, "genre")) +
                ```
                    </a>
                </div>
            ```
        }

    } catch (err) {
        console.log(err);
    }
}

async function postFormActors(url, formData) {

    const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));

    console.log(url, url.includes("reviewId"));

    const response = await fetch(url, {
        method: "post",
        // mode: "cors",
        headers: {"Content-Type": "application/json"},
        body: jsonData
    });

    return await response.json();
}
