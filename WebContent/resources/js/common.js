//选项卡切换
function setTab(name,cursel,n){
    for(i=1;i<=n;i++){
        var menu=document.getElementById(name+i);
        var con=document.getElementById("con_"+name+"_"+i);
        menu.className=i==cursel?"active":"";
        con.style.display=i==cursel?"block":"none";
    }
}
//模拟下拉菜单
$(document).ready(function(){
    $(".btn-dropdown").click(function(event){   
            event.stopPropagation();
            $(this).find(".dropdown-menu").toggle();
            $(this).parent().siblings().find(".dropdown-menu").hide();
    });
    $(document).click(function(event){
            var eo=$(event.target);
            if($(".btn-dropdown").is(":visible") && eo.attr("class")!="dropdown-menu" && !eo.parent(".dropdown-menu").length)
            $('.dropdown-menu').hide();									  
    });
    /*赋值给文本框*/
    $(".dropdown-menu a").click(function(){
            var value=$(this).text();
            $(this).parent().parent().siblings().find(".select-txt").text(value);
            $("#select_value").val(value)
    })
})
//媒体-轮播图片
$(function () {
    var sWidth = $("#focus").width();
    var len = $("#focus ul li").length;
    var index = 0;
    var picTimer;
    var btn = "<div class='btnBg'></div><div class='btn'>";
    for (var i = 0; i < len; i++) {
        btn += "<span></span>";
    }
    btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
    $("#focus").append(btn);
    $("#focus .btnBg").css("opacity", 0);
    $("#focus .btn span").css("opacity", 0.4).mouseenter(function () {
        index = $("#focus .btn span").index(this);
        showPics(index);
    }).eq(0).trigger("mouseenter");
    $("#focus .preNext").css("opacity", 0.0).hover(function () {
        $(this).stop(true, false).animate({ "opacity": "0.5" }, 300);
    }, function () {
        $(this).stop(true, false).animate({ "opacity": "0" }, 300);
    });
    $("#focus .pre").click(function () {
        index -= 1;
        if (index == -1) { index = len - 1; }
        showPics(index);
    });
    $("#focus .next").click(function () {
        index += 1;
        if (index == len) { index = 0; }
        showPics(index);
    });
    $("#focus ul").css("width", sWidth * (len));
    $("#focus").hover(function () {
        clearInterval(picTimer);
    }, function () {
        picTimer = setInterval(function () {
            showPics(index);
            index++;
            if (index == len) { index = 0; }
        }, 2800);
    }).trigger("mouseleave");
    function showPics(index) {
        var nowLeft = -index * sWidth;
        $("#focus ul").stop(true, false).animate({ "left": nowLeft }, 300);
        $("#focus .btn span").stop(true, false).animate({ "opacity": "0.4" }, 300).eq(index).stop(true, false).animate({ "opacity": "1" }, 300);
    }
});
