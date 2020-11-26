<#include "base.ftl">
<#include "macro.ftl">
<@html "film" true>
    <@_errors "film" />
    <div class="top_content row justify-content-center">
        <#assign description = film.getDescription()>
        <img class="col-md-auto film_avatar_img" src="${description.getImg()}" alt="film poster">
        <span class="col-md-auto description">
            <h2 class="film_name">${film.getName()}</h2>
            <div class="film_info">
                <ul class="list-group list-group-flush">
                    <#if description.year??>
                        <li class="list-group-item">год<span class="info_row_value">${description.year}</span></li>
                    </#if>
                    <li class="list-group-item">жанры
                        <#list description.getGenres() as genre>
                            <span class="info_row_value">${genre}</span>
                        </#list>
                    </li>
                    <li class="list-group-item">награды
                        <#list description.awards as award>
                            <span class="info_row_value">${award}</span>
                        </#list>
                    </li>
                    <li class="list-group-item">актёры
                        <#list description.actors as actor>
                            <span class="info_row_value">${actor}</span>
                        </#list>
                    </li>
                </ul>
                <div class="marks">
                    <h3>Mark:
                        <#if avgMark??>
                            ${avgMark?string("0.##")}
                        <#else>
                            No marks
                        </#if>
                    </h3>
                    <p class="d-flex justify-content-between zero-margin">
                        <span>dislike</span>
                        <span>neutral</span>
                        <span>like</span>
                    </p>
                    <@_form class="mark" action="/film?filmId=${film.getId()}"
                    fields=["mark#range"]
                    btnValue="Add mark" />
                </div>
            </div>
        </span>
    </div>
    <div class="top_content row justify-content-center">
        <div class="col-md-auto comments">
            <h3>Comments:</h3>
            <div class="comments__shorten">
                <#list comments as comment>
                    <div class="comment">
                        <p class="comment__user">${comment.getPersonUsername()}</p>
                        <p class="comment__text">${comment.getText()}</p>
                    </div>
                <#else>
                    <h4 id="noComments">No comments yet</h4>
                </#list>
            </div>
            <div class="comments__widen"></div>
            <div class="comments__add">
                <div class="comment">
                    <@_form class="comments" action="/film?filmId=${film.getId()}"
                    fields=["reviewText#text"] btnValue="Send" />
                </div>
            </div>
        </div>
        <div class="col-md-auto reviews">
        <h3>Reviews:</h3>
        <div class="reviews__shorten">
            <#list reviews?keys as review>
                <div class="review">
                    <#if reviews[review]??>
                        <h4>Average: ${review[review]}</h4>
                    <#else>
                        <h4 id="noReviewMarks">No marks yet</h4>
                    </#if>
                    <p class="review__text">${review.getReviewText()}</p>
                    <p class="review__mark">${review.getMark().toInt()}</p>
                    <p class="review__rating">${review.getRating().toInt()}</p>
                    <@_form class="review"
                    action="/film?filmId=${film.getId()}&reviewId=${review.getId()}"
                    fields=["rating#range"] btnValue="Rate review" />
                </div>
            <#else>
                <h4 id="noReviews">No reviews yet</h4>
            </#list>
        </div>
        <div class="reviews__widen"></div>
        <div class="reviews__add">
            <@_form class="reviews" action="/film?filmId=${film.getId()}"
            fields=["reviewText#text", "mark#range"] btnValue="Add review" />
        </div>
    </div>
    </div>
</@>
