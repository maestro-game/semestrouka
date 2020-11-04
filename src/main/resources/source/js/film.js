window.addEventListener('load', () =>
    ["comments", "mark", "reviews", "review"].forEach((c) =>
        document.querySelectorAll("." + c + "__form").forEach((form) =>
            form.addEventListener("submit", handler)
        )
    )
);

async function handler(event) {
    event.preventDefault();

    const form = event.currentTarget;
    const url = form.action;

    try {
        const formData = new FormData(form);
        const responseData = await postForm(url, formData);

        const className = form.className.split("__")[0];
        if (className !== "mark" && className !== "review") {
            var neededNode = document.querySelector("." + className + "__widen");
            if (className === "comments") {
                neededNode.innerHTML +=
                    `<div class="comment">
                                <p class="comment__user">${responseData["personUsername"]}</p>
                                <p class="comment__text">${responseData["reviewText"]}</p>
                            </div>`;
            } else if (className === "reviews") {
                neededNode.innerHTML +=
                    `<div class="review">
                                <p class="review__user">${responseData["personUsername"]}</p>
                                <p class="review__text">${responseData["reviewText"]}</p>
                                <p class="review__mark">${responseData["mark"]}</p>
                                <p class="review__rating">${responseData["rating"]}</p>
                                <form action="/film?filmId=${responseData["filmId"]}&reviewId=${responseData["id"]}" class="review__form">
                                    <input type="range" name="rating" id="rating" class="review__field" min="1" max="5">
                                    <input type="submit" value="Rate review" class="review__btn">
                                </form>
                            </div>`;
                console.log(window.dispatchEvent(new Event("load")));
            }
        } else if (className === "mark") {
            var neededNode = document.querySelector("." + className + "__avg");
            neededNode.innerHTML = "Average: " + responseData.toFixed(2);
        } else {
            var neededNode = document.querySelector("." + className + "__rating");
            neededNode.innerHTML = responseData.toFixed(2);
        }

    } catch (err) {
        console.log(err);
    }
}

async function postForm(url, formData) {

    const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));

    console.log(url, url.includes("reviewId"));

    const response = await fetch(url, {
        method: url.includes("reviewId") ? "put" : "post",
        // mode: "cors",
        headers: {"Content-Type": "application/json"},
        body: jsonData
    });

    return await response.json();
}
