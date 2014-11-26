$.fn.dialog=function(){
  this.MaskDiv=function()
       {
       
       var wnd = $(window), doc = $(document);
       //alert(doc.height());
       if(wnd.height() > doc.height()){  //当高度少于一屏
        wHeight = wnd.height();  
       }else{//当高度大于一屏
        wHeight = doc.height();   
       }
       //创建遮罩背景
       $("body").append("<div ID=MaskID></div>");
       $("body").find("#MaskID").width(wnd.width()).height(wHeight)
       .css({position:"absolute",top:"0px",left:"0px",background:"#ccc",filter:"Alpha(opacity=90);",opacity:"0.3",zIndex:"10000"});
       }
  this.sPosition=function(obj)
       {
      var MyDiv_w = parseInt(obj.width());
      var MyDiv_h = parseInt(obj.height());
      
      var width =parseInt($(document).width());
      var height = parseInt($(window).height());
      var left = parseInt($(document).scrollLeft());
      var top = parseInt($(document).scrollTop());
      var Div_topposition = top + (height / 2) - (MyDiv_h / 2); //计算上边距
      var Div_leftposition = left + (width / 2) - (MyDiv_w / 2); //计算左边距
      return Array(Div_topposition,Div_leftposition);
       }
     this.MaskDiv();
        $("body").append("<div ID=DivDialog style='display:none'><ul><li>提示</li></ul></div>");
  var bj=$("body").find("#DivDialog");
  // PosT=this.sPosition(obj);
  //       obj.css({position:"absolute",top:PosT[0]+"px",left:PosT[1]+"px",background:"#FFCC66",zIndex:"10001"}).show("slow");
  return this;
}