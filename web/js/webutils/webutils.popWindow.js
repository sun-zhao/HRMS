WEBUTILS.popWindow = (function () {
    return{
        closePopWindow:function () {
            $('.AppPop', 'body').hide(500);
            $('.AppPopp', 'body').remove();
        },
        hidePopWindow:function () {
            $('.AppPop', 'body').hide(500);
        },
        createPopWindow:function (width, height, title, url,scroll) {
            var mt,mr,mb,ml,margin,scrolling='no',winID='mdWindow';
            if(height&&height>530){
                height=530;
            }
            mt=parseInt(height/2);
            mr=0;
            mb=0;
            ml=parseInt(width/2);
            margin='-'+mt+'px '+mr+' '+mb+' -'+ml+'px';

            if(scroll){
                scrolling='yes';
            }
            var popObj = String.formatmodel(popMdPlus, {winID:winID,'width':width, 'height':height, 'title':title, 'url':url,'margin':margin,'scrolling':scrolling});
            $('.AppPop', 'body').remove();
            $('body').append(popObj);
            $('.AppPop', 'body').find('.off').off('click').on('click', function (e) {
                e.preventDefault();
                e.stopPropagation();
                $('.AppPop', 'body').hide(500);
                $('.AppPop', 'body').remove();
            });
            $('.AppPop', 'body').fadeIn(500);
        },
        offset:function (top,left) {
            if(top){
                $('.AppPop', 'body').css({top:top});
            }
            if(left){
                $('.AppPop', 'body').css({left:left});
            }
        }
    }
})();