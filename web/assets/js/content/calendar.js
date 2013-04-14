$(document).ready(function(){
    $(".tabAnchor").click(function(){
        var id = $(this).attr("href");
        $(".tabPane").removeClass("active");
        $(id).addClass("active");
    });
});