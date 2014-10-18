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