include("js/jquery.color.js");
include("js/jquery.backgroundpos.js");
include("js/jquery.easing.js");
include("js/jquery.mousewheel.js");
include("js/jquery.fancybox-1.3.4.pack.js");
include("js/googleMap.js");
include("js/superfish.js");
include("js/switcher.js");
include("js/bgStretch.js");
include("js/forms.js");
include("js/MathUtils.js");
include("js/jquery.cycle.all.min.js");

preloadIMG(["submenu_elem.png", "submenu_bg.jpg"])

function include(url) {
    document.write('<script src="' + url + '"></script>');
}
var MSIE = false, content, defColor;

function addAllListeners() {
    var val1 = $('.readMore3').css('color');
    $('.readMore3').hover(
        function(){
            $(this).stop().animate({'color':'#975355'},300,'easeOutExpo');
        },
        function(){
            $(this).stop().animate({'color':val1},500,'easeOutCubic');
        }
    );
    $('.playBtn,.buyBtn').hover(
        function(){
            $(this).stop().animate({'backgroundPosition':'center top'},300,'easeOutExpo');
        },
        function(){
            $(this).stop().animate({'backgroundPosition':'center bottom'},500,'easeOutCubic');
        }
    );
	$('.list1>li>a,.list2>li>a')
    .find('strong').css('top','200px').end()
    .hover(
        function(){
            if (!MSIE){
                $(this).children('.sitem_over').css({display:'block',opacity:'0'}).stop().animate({'opacity':1}).end() 
                .find('strong').css({'opacity':0}).stop().animate({'opacity':1,'top':'0'},350,'easeInOutExpo');
            } else { 
                $(this).children('.sitem_over').stop().show().end()
                .find('strong').stop().show().css({'top':'0'});
            }
        },
        function(){
            if (!MSIE){
                $(this).children('.sitem_over').stop().animate({'opacity':0},1000,'easeOutQuad',function(){$(this).children('.sitem_over').css({display:'none'})}).end()  
                .find('strong').stop().animate({'opacity':0,'top':'200px'},1000,'easeOutQuad');  
            } else {
                $(this).children('.sitem_over').stop().hide().end()
                .find('strong').stop().hide();
            }            
        }
    );
}

$(document).ready(ON_READY);
$(window).load(ON_LOAD);
function ON_READY() {
    /*SUPERFISH MENU*/   
    $('.menu #menu').superfish({
	   delay: 800,
	   animation: {
	       height: 'show'
	   },
       speed: 'slow',
       autoArrows: false,
       dropShadows: false
    });
}
function ON_LOAD(){
    MSIE = ($.browser.msie) && ($.browser.version <= 8);
    $('.spinner').fadeOut();

    if ($(".slider").length) {
        $('.slider').cycle({
            fx: 'scrollHorz',
            speed: 600,
    		timeout: 0,             
    		easing: 'easeInOutExpo',
    		cleartypeNoBg: true ,
            rev:0,
            startingSlide: 0,
            wrap: true
  		})
    };
    var ind = 0;
    var len = $('.nav_box a').length;
    $('.nav_box a').each(function (ind){ $(this).attr('data',ind); })
    .bind('click',function(){
        ind = parseInt($(this).attr('data'));
        $('.nav_box a').each(function(index,elem){if (index!=(ind)){$(elem).removeClass('active');}});
        $(this).addClass('active');
        $('.slider').cycle(ind);
        return false;
    });
    
	$('.list1>li>a,.list2>li>a').attr('rel','appendix')
    .prepend('<span class="sitem_over"><strong></strong></span>')
    $('.list1>li>a,.list2>li>a').fancybox({
        'transitionIn': 'elastic',
    	'transitionOut': 'elastic',
    	'speedIn': 500,
    	'speedOut': 300,
        'centerOnScroll': true,
        'overlayColor': '#000'
    });
    
    //content switch
    content = $('#content');
    content.tabs({
        show:0,
        preFu:function(_){
            _.li.css({'visibility':'hidden'});		
        },
        actFu:function(_){
            if(_.curr){                
                _.curr
                    .css({'left':'-1000px','visibility':'visible'}).stop(true).delay(600).show().animate({'left':'0px'},{duration:1000,easing:'easeOutExpo'});
            }   
    		if(_.prev){
  		        $('#leftPanel').stop(true).animate({'marginLeft':'400px'},600,'easeInOutExpo')
                    .animate({'marginLeft':'0'},600,'easeOutExpo');
  		        _.prev
                    .show().stop(true).animate({'left':'-1000px'},{duration:600,easing:'easeInOutExpo',complete:function(){
                            if (_.prev){
                                _.prev.css({'visibility':'hidden'});
                            }
                        }
		              });
            }            
  		}
    });
    defColor = $('#menu>li>a').eq(0).css('color'); 
    var nav = $('.menu');
    nav.navs({
		useHash:true,
        defHash: '#!/page_home',
        hoverIn:function(li){
            $('>a', li).stop().animate({color: '#3b2c36'},350,'easeOutExpo');
        },
        hoverOut:function(li){
            if ((!li.hasClass('with_ul')) || (!li.hasClass('sfHover'))) {
                $('>a', li).stop().animate({color: defColor},700,'easeOutCubic');
            }
        }
    })
    .navs(function(n){	
   	    $('#content').tabs(n);
   	});
    
    setTimeout(function(){
        $('#bgStretch').bgStretch({
    	   align:'leftTop',
           autoplay: false,
           navs: false
        });
    },0);
    setTimeout(function(){  $('body').css({'overflow':'visible'}); },300);    
    addAllListeners();
    $(window).trigger('resize');
};

$(window).resize(function (){
    if (content) content.stop().animate({'top':(windowH()-content.height())*.5-33},500,'easeOutCubic');
});