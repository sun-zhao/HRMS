var validateTipTemp =
    '<div class="reqformError parentFormformID formError" style="opacity: 0.87; position: absolute; top: {top}px; left: {left}px;" forId="{forId}">' +
        '<div class="formErrorContent">* {message}<br></div>' +
        '<div class="formErrorArrow">' +
        '<div class="line10">&nbsp;</div>' +
        '<div class="line9">&nbsp;</div>' +
        '<div class="line8">&nbsp;</div>' +
        '<div class="line7">&nbsp;</div>' +
        '<div class="line6">&nbsp;</div>' +
        '<div class="line5">&nbsp;</div>' +
        '<div class="line4">&nbsp;</div>' +
        '<div class="line3">&nbsp;</div>' +
        '<div class="line2">&nbsp;</div>' +
        '<div class="line1">&nbsp;</div>' +
        '</div>' +
        '</div>';

var searchAllTemp = '<li ><a href="###" type="all">显示全部数据</a></li>';

/**********遮罩*********/
var blockLayout =
    '<div id="blockLayout" style=" display: none; z-index: 1000000003; border: medium none; margin: 0pt; padding: 0pt; width: 100%; height: 100%; top: 0pt; left: 0pt; background:none repeat scroll 0 0 transparent; cursor: wait; position: fixed;"></div>';
var ddmoveShadow = '<div id="shortcut_shadow" style="position:absolute;z-index: 100000003;">{object}</div>';

var ddmoveControl = '<span class="md-p-in"><a>{text}</a></span>';

var ddmoveApply = '<div class="md-doc-item-box-s floatleft">' +
    '<div class="md-doc-item" title="员工入职申请">' +
    '<a href="#" class="h2">{applyName}</a>' +
    '<div class="md-plus-h clearfix">' +
    '<span class="md-ico floatleft md-ico-01"></span>' +
    '<span class="md-doc-txt floatleft">{applyDesc}</span>' +
    '</div>' +
    '<p class="mart10 clearfix"><a href="#" class="md-plus-btn floatright">填 写<em class="md-plus-btn-r"></em></a></p>' +
    '</div>' +
    '</div>';

var ddmoveControlSpan = '<li uid="{id}">' +
    '<span class="md-p-in">' +
    '<a>{text}</a>' +
    '<a class="md-delect" href="#"></a>' +
    '</span>' +
    '</li>';


var popMdPlus = '<div style="left:50%;top:50%;margin:{margin};width:{width}px;" class="AppPop" id="{winID}">' +
'<div class="PopInner">' +
'<div class="PopTitle">' +
'<span>{title}</span>' +
'<span class="off">×</span>' +
'</div>' +
'<div class="PopMain clearfix">' +
'<iframe width="100%" scrolling="{scrolling}" height="{height}px" frameborder="0" src="{url}" style="overflow-x:hidden;overflow-y: auto;" id="pop-md-plus-iframe">' +
'</iframe>' +
'</div>' +
'</div>' +
'</div>';

var popMdDiv = ' <div style="width:{width}px;top:50%;left:50%;display:none;margin:{margin}" class="md-plus-pop">' +
    '<div class="md-plus-pop-inner">' +
    '<a class="md-delect"></a>' +
    '<p class="font18 md-title">{title}</p>' +
    '<div>' +
    '<div width="100%"  height="{height}px"  style="overflow-x:hidden;" id="pop-md-div">' +
    '</div>' +
    '</div>' +
    '</div>' +
    '</div>';

var flowDD = '<dd uid="{flowId}">' +
    '<a class="flowNodeA">{flowName}</a>' +
    '<div class="md-btn-team flowDiv">' +
    '<a class="md-mi-ico2 md-mi-edit" uid="{flowId}"></a>' +
    '<a class="md-mi-ico2 md-mi-delete" uid="{flowId}"></a>' +
    '<a class="md-mi-ico2 md-mi-up" uid="{flowId}"></a>' +
    '<a class="md-mi-ico2 md-mi-down" uid="{flowId}"></a>' +
    '</div>' +
    '</dd>';

var dataDD = '<dd uid="{dataId}">' +
    '<a class="dataA">{keyName}</a>' +
    '<div class="md-btn-team dataDiv">' +
    '<a class="md-mi-ico2 md-mi-edit" uid="{dataId}"></a>' +
    '<a class="md-mi-ico2 md-mi-delete" uid="{dataId}"></a>' +
    '<a class="md-mi-ico2 md-mi-up" uid="{dataId}"></a>' +
    '<a class="md-mi-ico2 md-mi-down" uid="{dataId}"></a>' +
    '</div>' +
    '</dd>';

var dataDDLI = '<li uid="{dataId}" class="current">' +
    '<div><span class="m-p-plus"></span>' +
    '<a> {keyName}</a>' +
    '<div class="md-btn-team">' +
    '<a uid="{dataId}" class="md-mi-ico2 md-mi-add dataAdd"></a>' +
    '</div>' +
    '</div>' +
    '<dl style="display: none;">' +
    '</dl>' +
    '</li>';


var resultUserDIV = '<div style="left:{left}px;top:{top}px;display: none;" class="md-result-user"></div>';
var resultUserItemDIV = '<div class="md-results {classMd} clearfix" uid="{userId}" type="{type}">' +
    '<span><img src="{avStar100}"></span>' +
    '<span class="md-name">{userName}</span>' +
    '<span>（{deptName}</span> ' +
    '<span>{jobName}）</span>' +
    '</div>';

var resultUserItemDIVMobile = '<li class="{classMd}"><a href="##" uid="{userId}" type="{type}" name="{userName}"><img src="{avStar100}">{userName}&nbsp;（{deptName})</a></li>';


var resultVariableItemDIV = '<div class="md-results {classMd} clearfix" uid="{id}" type="{type}">' +
    '<span><img src="{avStar100}"></span>' +
    '<span class="md-name">{name}</span>' +
    '<span>（{deptName}</span> ' +
    '<span>{jobName}）</span>' +
    '</div>';

var addUserItem = '<li uid="{userId}" type="{type}"><span class="md-pro-user"><em>{userName}</em><a title="删除此人" class="md-delect"></a></span></li>';


var alertSuccess = '<div style="display:none;top:30%;left:50%;" class="alertDialog alertMsg" id="alertSuccessDialogMD">' +
    '<span>{text}</span>' +
    '<a class="close" href="#">×</a>' +
    '</div>';

var alertError = '<div style="display:none;top:30%;left:50%;" class="errorDialog alertMsg" id="alertErrorDialogMD">' +
    '<span>{text}</span>' +
    '<a class="close" href="#">×</a>' +
    '</div>';

var addMyFlowNode = '<tr>' +
    '<th><span class="font14">第{index}步审批</span></th>' +
    '<td>' +
    '<div class="md-clander floatleft">' +
    '<input type="text" class="md-int-mid searchApprove" id="node{index}" name="node{index}" nodeSeq="{index}">' +
    '<a class="md-ser-ico" nodeSeq="{index}"></a>' +
    '<ul class="add-user-list clearfix" nodeSeq="{index}">' +
    '</ul>' +
    '</div>' +
    '</td>' +
    '</tr>';
var addMyFlowNodeHide = '<input type="hidden" name="nodeType{index}" id="nodeType{index}" nodeSeq="{index}">' +
    '<input type="hidden" name="approveType{index}" id="approveType{index}" nodeSeq="{index}">' +
    '<input type="hidden" name="approve{index}" id="approve{index}" nodeSeq="{index}">';

var approveNodeDiv = '<div class="md-ap-pro" rows="{rows}">' +
    '<ul class="clearfix mart10" >' +
    '</ul>' +
    '</div>';

var approveNodeBox = '<div class="md-doc-set-box" style="margin-left: 15px;" rows="{rows}">' +
    '<div class="md-ap-pro">' +
    '<ul class="clearfix mart10">' +
    '</ul>' +
    '</div>' +
    '</div>';


var approveNodeLi = '<li class="flowNodeLi {className}" nodeType="{nodeType}" approveType="{approveType}" approveId="{approveId}" nodeSeq="{nodeSeq}">' +
    '<span class="md-num-ico">{nodeIndex}</span>' +
    '<span class="md-pro-user" >' +
    '<em>{approveName}</em>' +
    '<a title="删除此审批人" class="md-delect"></a>' +
    '</span>' +
    '</li><li class="arrow"><em class="md-r-arrow"></em></li>';

var flowListTr='<tr class="step" style="" nodeType="{nodeType}" approveType="{approveType}" approveId="{approveId}" nodeSeq="{nodeSeq}">' +
    '<td >{nodeIndex}{desc} <span class="color-p">{approveName}</span></td>' +
    '</tr>';

var approveNodeLiMobile='<li class="flowNodeLi" nodeType="{nodeType}" approveType="{approveType}" approveId="{approveId}" nodeSeq="{nodeSeq}">' +
    '<span class="label label-info">{nodeIndex}</span><a href="#">{approveName}' +
    '</a><span class="icon-arrow-right"></span></li>';

var ddmoveMyFlowNode = '<span class="md-pro-user" >' +
    '<em>{approveName}</em>' +
    '</span>';

var maskDiv = '<div class="mask">' +
    '<span class="mask-x">{text}</span>' +
    '</div>';

var maskMobileDiv = '<div style="display:block;" class="mask">' +
    ' <span class="mask-x">{text}</span>' +
    '</div>';

var appTable = '<div class="md-doc-set-box" rows="{rows}">' +
    '<a title="删除此行" class="md-delect cut-table" href="#"></a>' +
    '<table width="100%" class="md-set-table">' +
    '<tbody><tr>' +
    '<th></th>' +
    '<td>' +
    '<div class="td-inner clearfix">' +
    '<a class="add-controls" href="#">添加控件</a>' +
    '<span class="floatright md-make-btns">' +
    '</span>' +
    '</div>' +
    '</td>' +
    '<th></th>' +
    '<td>' +
    '<div class="td-inner clearfix">' +
    '<a class="add-controls" href="#">添加控件</a>' +
    '<span class="floatright md-make-btns">' +
    '<a href="#" class="md-minus-ico" title="删除一列"></a>' +
    '</span>' +
    '</div>' +
    '</td>' +
    '</tr>' +
    '</tbody></table>' +
    '</div>';





var globalDiv = '<div class="md-doc-item-box floatleft"><div class="md-doc-item">' +
    '<h2>' +
    '<span class="txt_hidden font14">{globalName}</span>' +
    '<span class="floatright">' +
    '<a uid="{globalId}" class="md-mi-ico2 md-mi-edit" title="编辑"></a>' +
    '<a uid="{globalId}" class="md-mi-ico2 md-mi-delete" title="删除"></a>' +
    '</span>' +
    '</h2>' +
    '<div class="clearfix mart10">' +
    '<div class="md-plus-pho floatleft"><img src="{avstar}"></div>' +
    '<ul class="md-user-info marl5 floatleft">' +
    '<li>{userName}</li>' +
    '<li>{deptName}</li>' +
    '<li>{jobName}</li>' +
    '</ul>' +
    '</div>' +
    '</div></div>';

var globalDivAdd = '<div class="md-doc-item-box floatleft">' +
    '<div style="cursor: pointer;" class="md-doc-item addGlobal">' +
    '<p class="aligncenter mart30"><i class="md-plus-add"></i></p>' +
    '<p class="aligncenter font14 mart10 strong"><a href="#">创建审批职位</a></p>' +
    '</div>' +
    '</div>';

var mobileModal='<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">' +
'<div class="modal-header">' +
    '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>' +
    '<h3 id="myModalLabel">{title}</h3>' +
    '</div>' +
    '<div class="modal-body">' +
    '<iframe width="100%" scrolling="{scrolling}" height="{height}px" frameborder="0" src="{url}" style="overflow-x:hidden;" id="pop-md-plus-iframe">' +
    '</iframe>' +
    '</div>' +
    '<div class="modal-footer">' +
    '<button class="btn" data-dismiss="modal" aria-hidden="true">关 闭</button>' +
    '<button class="btn btn-primary">保 存</button>' +
    '</div>' +
'</div>';

var flowSetAddStep='<tr nodeSeq="{nodeSeq}">' +
    '<td>第{nodeSeq}步 审批</td>' +
    '<td>' +
    '<div class="md-clander floatleft inputDiv">' +
    '<input type="text" class="md-int-mid searchApprove"  nodeSeq="{nodeSeq}">' +
    '<a href="##" class="md-ser-ico" nodeSeq="{nodeSeq}"></a>' +
    '<ul class="add-user-list clearfix" nodeSeq="{nodeSeq}">' +
    '</ul>' +
    '</div>' +
    '<span class="color-p editDiv" style="display: none;">' +
    '<span class="name"></span>' +
    '<em class="md-d-triangle"></em>' +
    '<dl class="md-make-pro">' +
    '<dd><a href="##" class="reNode" nodeSeq="{nodeSeq}">替换审批人</a></dd>' +
    '<dd><a href="##" class="deleteCurrentNode" nodeSeq="{nodeSeq}">删除这个审批步骤</a></dd>' +
    '</dl>' +
    '</span>' +
    '</td>' +
    '</tr>';


var flowSetAddStepMobile='<tr nodeSeq="{nodeSeq}">' +
    '<th>第{nodeSeq}步 审批</th>' +
    '<td>' +
    '<div class="inputDiv">' +
    '<div class="input-append">' +
    '<input type="text"  class="span2 searchApprove" nodeSeq="{nodeSeq}">' +
    '<button type="button" class="btn searchBtn" nodeSeq="{nodeSeq}"><i class="icon-search"></i></button>' +
    '</div>' +
    '</div>' +
    '<div class="btn-group editDiv" style="display: none;">' +
    '<button data-toggle="dropdown" class="btn btn-small dropdown-toggle"><span class="name"></span><span class="caret"></span></button>' +
    '<ul class="dropdown-menu">' +
    '<li><a href="#" class="reNode" nodeSeq="{nodeSeq}">替换审批人</a></li>' +
    '<li><a href="#" class="deleteCurrentNode" nodeSeq="{nodeSeq}">删除这个审批步骤</a></li>' +
    '</ul>' +
    '</div>' +
    '</td>' +
    '</tr>';

var addFlowNodeHide = '<input type="hidden" name="nodeType{index}" id="nodeType{index}" nodeSeq="{index}" value="{nodeType}">' +
    '<input type="hidden" name="approveType{index}" id="approveType{index}" nodeSeq="{index}" value="{approveType}">' +
    '<input type="hidden" name="approve{index}" id="approve{index}" nodeSeq="{index}" value="{approve}">';