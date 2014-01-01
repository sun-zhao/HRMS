WEBUTILS.status = (function () {
    var _focusObj = '';
    var _controlTrInObj = '';
    var _controlTdInObj = '';
    var _applyDivInObj = '';
    var _myFlowNodeInObj = '';
    return{
        controlTrInObj:function (controlTrInObj) {
            if (controlTrInObj) {
                _controlTrInObj = controlTrInObj;
            }
            return _controlTrInObj;
        },
        controlTdInObj:function (controlTdInObj) {
            if (controlTdInObj) {
                _controlTdInObj = controlTdInObj;
            }
            return _controlTdInObj;
        },

        applyDivInObj:function (applyDivInObj) {
            if (applyDivInObj) {
                _applyDivInObj = applyDivInObj;
            }
            return _applyDivInObj;
        },
        myFlowNodeInObj:function (myFlowNodeInObj) {
            if (myFlowNodeInObj) {
                _myFlowNodeInObj = myFlowNodeInObj;
            }
            return _myFlowNodeInObj;
        },
        scollPostion: function () {//滚动条位置
            var t, l, w, h;
            if (document.documentElement && document.documentElement.scrollTop) {
                t = document.documentElement.scrollTop;
                l = document.documentElement.scrollLeft;
                w = document.documentElement.scrollWidth;
                h = document.documentElement.scrollHeight;
            } else if (document.body) {
                t = document.body.scrollTop;
                l = document.body.scrollLeft;
                w = document.body.scrollWidth;
                h = document.body.scrollHeight;
            }
            return { top: t, left: l, width: w, height: h };
        },
        isIn:function (o, e) {
            var _in = false, ml, mt, ol, ow, ot, oh;
            ml = e.clientX;
            ml = parseInt(ml);
            mt = e.clientY;
            mt = parseInt(mt);
            var scollOffset=WEBUTILS.status.scollPostion();
            if(scollOffset){
                mt +=scollOffset.top;
            }
            ol = $(o).offset().left;
            ol = parseInt(ol);
            ow = $(o).width();
            ow = parseInt(ow);
            ot = $(o).offset().top;
            ot = parseInt(ot);
            oh = $(o).height();
            oh = parseInt(oh);
            if ((mt >= ot && mt <= (ot + oh)) && (ml >= ol && ml <= (ol + ow))) {
                _in = true;
            }
            return _in;
        },
        currentIn:function (e) {
            var _in = 'none', o;
            //controlTable
            $('tr', '#controlTable').each(function () {
                if ($(this).is(':visible')) {
                    o = $(this);
                    if (o) {
                        if (WEBUTILS.status.isIn(o, e)) {
                            _in = 'controlTr';
                            WEBUTILS.status.controlTrInObj(o);
                            return false;
                        }
                    }
                }
            });
            $('td', '.md-creation-in').each(function () {
                if ($(this).is(':visible')) {
                    o = $(this);
                    if (o) {
                        if (WEBUTILS.status.isIn(o, e)) {
                            _in = 'controlTd';
                            WEBUTILS.status.controlTdInObj(o);
                            return false;
                        }
                    }
                }
            });
            $('.md-doc-item-box-s').each(function () {
                if ($(this).is(':visible')) {
                    o = $(this);
                    if (o) {
                        if (WEBUTILS.status.isIn(o, e)) {
                            _in = 'applyDiv';
                            WEBUTILS.status.applyDivInObj(o);
                            return false;
                        }
                    }
                }
            });
            $('.flowNodeLi').each(function () {
                if ($(this).is(':visible')) {
                    o = $(this);
                    if (o) {
                        if (WEBUTILS.status.isIn(o, e)) {
                            _in = 'myFlowNode';
                            WEBUTILS.status.myFlowNodeInObj(o);
                            return false;
                        }
                    }
                }
            });
            if (_in != 'none') {
                return _in;
            }
        }
    }
})();