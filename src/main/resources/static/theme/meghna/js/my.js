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
   });

   $(".product-search").click(function () {
       var catCode = $(this).data("code");
       var param = [];
       param.push($('<input>', {name:'categoryCode', value: catCode, type:"hidden"}));
       $("#productSearchForm").append(param);
       $("#productSearchForm").submit();

   });

});