/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {

    $(".full-graph-graphviz-source, .reduced-graph-graphviz-source").each(function () {
        $(this).html(Viz($(this).text(), format = "svg", engine = "twopi", options = null));
    });

});