<#include "base.ftl">
<#include "macro.ftl">
<@html "film">
    <@_roleCheck "USER">
        <@_errors "film" />
        <div class="content content__film">
            <div class="film">
                <#assign description = film.getDescription()>
                <img class="film__poster" src="${description.getImg()}" alt="film poster"/>
                <p class="film__filename">${film.getName()}</p>
                <#list description.getGenres() as genre>
                    <p class="film__genre">${genre}</p>
                </#list>
            </div>
            <div class="comments">
                <h3>Comments:</h3>
                <div class="comments__shorten">
                    <#list comments as comment>
                        <div class="comment">
                            <#--                            <img src="${comment.getImg()}" alt="commenter's avatar" class="comment__img" />-->
                            <p class="comment__user">${comment.getPersonUsername()}</p>
                            <p class="comment__text">${comment.getText()}</p>
                        </div>
                    <#else>
                        <h4>No comments yet</h4>
                    </#list>
                </div>
                <div class="comments__widen"></div>
                <div class="comments__add">
                    <div class="comment">
                        <#--                        <img src="${user.getImg()}"  alt="my avatar"/>-->
                        <@_form class="comments" action="/film?filmId=${film.getId()}"
                        fields=["reviewText#text"] btnValue="Add comment" />
                    </div>
                </div>
            </div>
            <div class="mark">
                <h3>Mark:</h3>
                <#if avgMark??>
                    <h4 class="mark__avg">Average: ${avgMark?string("0.##")}</h4>
                <#else>
                    <h4 class="mark__avg">No marks yet</h4>
                </#if>
                <@_form class="mark" action="/film?filmId=${film.getId()}"
                fields=["mark#range"]
                btnValue="Add mark" />
                <p class="mark__description">-1 0 1</p>
            </div>
            <div class="reviews">
                <h3>Reviews:</h3>
                <#--                TODO: LIMIT REVIEWS AND COMMENTS AND ADD BUTTON 'SHOW MORE' -->
                <div class="reviews__shorten">
                    <#list reviews?keys as review>
                        <div class="review">
                            <#if reviews[review]??>
                                <h4>Average: ${review[review]}</h4>
                            <#else>
                                <h4>No marks yet</h4>
                            </#if>
                            <p class="review__text">${review.getReviewText()}</p>
                            <p class="review__mark">${review.getMark().toInt()}</p>
                            <p class="review__rating">${review.getRating().toInt()}</p>
                            <@_form class="review"
                            action="/film?filmId=${film.getId()}&reviewId=${review.getId()}"
                            fields=["rating#range"] btnValue="Rate review" />
                        </div>
                    <#else>
                        <h4>No reviews yet</h4>
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
</@>
