<#include "base.ftl">
<#include "macro.ftl">
<@html "filter" true>
    <@_form action="filter" class="filter"
    fields=["year#text#'Введите год фильма'", "genres#text#'Жанры через пробел'", "actors#text#'Актеры через запятую'", "awards#text#'Награды через пробел'"]
    />
</@>
