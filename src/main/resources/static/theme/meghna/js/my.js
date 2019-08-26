$(function () {

   $(".og-grid .mix a img").each(function () {
/*
       var height = $(this).height();
       var width = $(this).width();
       var rate = width/height;
       var obj = $(this).parent();
       var pWidth = obj.width();
       var pHeight = obj.height();
       var pRate = pWidth/pHeight;

       if(rate>=pRate){
           $(this).css("height", "100%");
       }else {
           $(this).css("height", "100%");
       }*/
       $(this).css("height", "250px");
       $(this).css("width", "300px");
   })

});