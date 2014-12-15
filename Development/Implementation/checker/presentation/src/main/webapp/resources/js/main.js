/* 
 Created on : 16.11.2014, 12:24:09
 Author     : Jindřich Máca
 */
$(function () {
    //defining global variables
    var speed = "fast";

    //hides all slidable elements in the beginning
    $('.graph ul:not(:first)').slideUp();

    //sets style to slidable elements in the beginning
    $('.graph span').each(function () {
        if ($(this).next('ul:first').children('li').length > 0) {
            $(this).css({
                cursor: 'pointer',
                textDecoration: 'underline'
            }).addClass("accordion") //adds special class to regognize slidable elements
                    .before('<div class="arrow"></div>'); //adds arrow element

        }
    });

    //slides element on click and change its arrow
    $('.accordion').click(function () {
        if ($(this).next('ul:first').is(':hidden')) {
            $(this).prev('div:first').css("backgroundPosition", "-64px -16px"); //changes the arrow
            $(this).next('ul:first').stop().slideDown(speed);
        } else {
            $(this).prevAll('div:first').css("backgroundPosition", "-32px -16px"); //changes the arrow
            $(this).next('ul:first').stop().slideUp(speed, function () {
                //hides all element's children
                $(this).find('ul').slideUp();
                $(this).find('.arrow').css("backgroundPosition", "-32px -16px");
            });
        }
    });
});
