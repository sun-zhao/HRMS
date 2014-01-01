WEBUTILS.msg = (function () {
    return {
        alertInfo:function (text, time,callback) {
            if (!time) {
                time = 1500;
            }
            $('body').append(String.formatmodel(alertSuccess,{'text':text}));
            $('#alertSuccessDialogMD').fadeIn(500);
            $('#alertSuccessDialogMD').css('marginLeft','-'+$('#alertSuccessDialogMD').width()/2+'px');
            $.doTimeout('alertSuccessDialogMDClose', time, function () {
                $('#alertSuccessDialogMD').hide();
                $('#alertSuccessDialogMD').remove();
                if(typeof callback!='undefined'&callback instanceof Function){
                    callback();
                }
            });
        },
        alertSuccess:function (text, time,callback) {
            if (!time) {
                time = 1500;
            }
            $('body').append(String.formatmodel(alertSuccess,{'text':text}));
            $('#alertSuccessDialogMD').fadeIn(500);
            $('#alertSuccessDialogMD').css('marginLeft','-'+$('#alertSuccessDialogMD').width()/2+'px');
            $.doTimeout('alertSuccessDialogMDClose', time, function () {
                $('#alertSuccessDialogMD').hide();
                $('#alertSuccessDialogMD').remove();
                if(typeof callback!='undefined'&callback instanceof Function){
                    callback();
                }
            });
        },
        alertFail:function (text, time,callback) {
            if (!time) {
                time = 1500;
            }
            $('body').append(String.formatmodel(alertError,{'text':text}));
            $('#alertErrorDialogMD').fadeIn(500);
            $('#alertErrorDialogMD').css('marginLeft','-'+$('#alertErrorDialogMD').width()/2+'px');
            $.doTimeout('alertErrorDialogMDClose', time, function () {
                $('#alertErrorDialogMD').hide();
                $('#alertErrorDialogMD').remove();
                if(typeof callback!='undefined'&callback instanceof Function){
                    callback();
                }
            });
        },
        alertLoading:function (text, time,callback) {
            if (!time) {
                time = 50000;
            }
            $('body').append(String.formatmodel(alertSuccess,{'text':text}));
            $('#alertSuccessDialogMD').fadeIn(500);
            $('#alertSuccessDialogMD').css('marginLeft','-'+$('#alertSuccessDialogMD').width()/2+'px');
            $.doTimeout('alertSuccessDialogMDClose', time, function () {
                $('#alertSuccessDialogMD').hide();
                $('#alertSuccessDialogMD').remove();
                if(typeof callback!='undefined'&callback instanceof Function){
                    callback();
                }
            });
        },
        close:function () {
            $('#alertSuccessDialogMD').hide();
            $('#alertSuccessDialogMD').remove();

            $('#alertErrorDialogMD').hide();
            $('#alertErrorDialogMD').remove();
        }
    }
})();