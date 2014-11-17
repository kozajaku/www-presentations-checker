/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 
 $(".ui-paginator").each(function(){
 
 var paginationContainer = $("<div></div>");
 var paginationUl = $("<ul></ul>").addClass("pagination");
 
 $(this).find(".ui-paginator-pages span").each(function(){
 var paginationLi = $("<li></li>").text($(this).text());
 if($(this).hasClass("ui-state-active")) paginationLi.addClass("active");
 paginationLi.appendTo(paginationUl);
 });
 
 paginationUl.appendTo(paginationContainer);
 
 $(this).replaceWith(paginationContainer);
 
 });
 
 */

$(function () {
    setTimeout(function () {
        $(".ui-paginator").each(function () {

            var e = $(this);

            e.find(".ui-paginator-current").remove();
            e.find(".ui-paginator-first").remove();
            e.find(".ui-paginator-last").remove();


            var ul = $("<ul></ul>");
            ul.addClass("pagination").css("cursor", "pointer");

            e.find(".ui-paginator-prev span").html("&laquo;");
            e.find(".ui-paginator-prev").appendTo($("<li></li>").appendTo(ul));
            e.find(".ui-paginator-pages").children().each(function () {
                $(this).appendTo($("<li></li>").click(function () {
                    $(this).parent().children().removeClass("active");
                    $(this).addClass("active");
                }).appendTo(ul));
            });
            e.find(".ui-paginator-next span").html("&raquo;");
            e.find(".ui-paginator-next").appendTo($("<li></li>").appendTo(ul));

            ul.find(".ui-state-active").removeClass("ui-state-active").addClass("active");

            e.html('');
            ul.insertAfter(e);
            ul.appendTo(e);
        });
    }, 20);

});
