<#import "/view/template/structure/setting/settingCommon.ftl" as setting_common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@setting_common.setting_common>
<script type="text/javascript">
    var submited = false;
    var ignoreName='';
    function pagerAction(start, rows) {
        var searchUrl = '/hr/officeAddr.dhtml';
        searchUrl += '?start=' + start + '&rows=' + rows;
        var searchKey = $('#address').val();
        if(searchKey){
            searchKey=searchKey.trim();
            searchUrl+='&address='+searchKey;
        }
        searchUrl = encodeURI(searchUrl);
        document.location.href = searchUrl
    }
    $(document).ready(function () {
        $('#address').off('keyup').on('keyup', function (e) {
            e = (e) ? e : ((window.event) ? window.event : "")
            var keyCode = e.keyCode ? e.keyCode : (e.which ? e.which : e.charCode);
            if (keyCode ==13) {
                pagerAction(0,20);
            }
        });
        $('#hrOfficeAddr\\.address').off('keyup').on('keyup', function (e) {
            e = (e) ? e : ((window.event) ? window.event : "")
            var keyCode = e.keyCode ? e.keyCode : (e.which ? e.which : e.charCode);
            if (keyCode ==13) {
                $('#btnSave').trigger('click');
            }
        });
        $('#btnSave').off('click').on('click', function () {
            var address=$('#hrOfficeAddr\\.address').val();
            if(address){
                address=address.trim();
                if(address!=''){
                    $.ajax({
                        type:'POST',
                        url:'/hr/officeAddr!validateAddress.dhtml',
                        data:{address:address,ignore:ignoreName},
                        dataType:'json',
                        success:function (jsonData) {
                            if (jsonData) {
                                if (jsonData['result'] == '0') {
                                    if (!submited) {
                                        WEBUTILS.popMask.show();
                                        document.editForm.submit();
                                        submited=true;
                                    }
                                }else  if (jsonData['result'] == '-1') {
                                    WEBUTILS.msg.alertFail('不能重复添加工作地点['+address+']');
                                }
                            }
                        },
                        error:function (jsonData) {

                        }
                    });
                }
            }
        });

        $('.icon-edit').off('click').on('click',function(){
            var uid=$(this).attr('uid');
            if(uid){
                $.ajax({
                    type:'POST',
                    url:'/hr/officeAddr!getAddress.dhtml',
                    data:{id:uid},
                    dataType:'json',
                    success:function (jsonData) {
                        if (jsonData) {
                            if (jsonData['result'] == '0') {
                                $('#hrOfficeAddr\\.address').val(jsonData['address']);
                                $('#hrOfficeAddr\\.id').val(jsonData['id']);
                                $('#btnSave').text('修改');
                                ignoreName=jsonData['address'];
                            }
                        }
                    },
                    error:function (jsonData) {

                    }
                });
            }
        });

        $('.icon-delete').off('click').on('click',function(){
            var uid=$(this).attr('uid');
            if(uid){
                var r=confirm("此操作将删除该工作地点,是否继续?");
                if(r==true){
                    WEBUTILS.popMask.show();
                    document.location.href='/hr/officeAddr!delete.dhtml?id='+uid;
                }
            }
        });

        $('.icon-up').off('click').on('click',function(){
            var uid=$(this).attr('uid');
            var order=$(this).attr('order');
            if(order){
                if(order==1){
                    WEBUTILS.msg.alertFail('该工作地点已排第一位,无法再进行上移操作!');
                    return false;
                }
            }
            if(uid){
                WEBUTILS.popMask.show();
                document.location.href='/hr/officeAddr!up.dhtml?id='+uid;
            }
        });
        $('.icon-down').off('click').on('click',function(){
            var uid=$(this).attr('uid');
            var order=$(this).attr('order');
            if(order){
                if(order==${maxOrder?c}){
                    WEBUTILS.msg.alertFail('该工作地点已排最后一位,无法再进行下移操作!');
                    return false;
                }
            }
            if(uid){
                WEBUTILS.popMask.show();
                document.location.href='/hr/officeAddr!down.dhtml?id='+uid;
            }
        });
    });
</script>
<!--排序搜索begin-->
<div class="clearfix gotop">
    <div class="AppSequence floatleft marr10"><!--加current下拉框显示-->
        <a href="#">按时间排序</a>
        <em class="app-icon ico-toogle"></em>
        <div class="down-list">
            <ul>
                <li>按时间排序</li>
                <li>按类别排序</li>
                <li>按紧急排序</li>
            </ul>
        </div>
    </div>
    <!--搜索框begin-->
    <div class="search-area floatleft">
        <input type="text" id="name" name="name" class="search-int" placeholder="请输入工作地点"
               <#if address?exists>value="${address?if_exists}"</#if>>
        <em class="line-y"></em>
        <a href="##" class="search-btn"><i class="app-icon ico-search"></i></a>
    </div>
    <!--搜索框over-->
</div>
<!--排序搜索over-->
    <form action="/hr/officeAddr!save.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
      onsubmit="return false;">
    <p class="font14 mart10">添加一个工作地点</p>
    <p class="clearfix mart10">
        <input type="text" align="middle" class="ThemeInput width-200 floatleft" id="hrOfficeAddr.address" name="hrOfficeAddr.address">
        <a class="button marl20 floatleft" href="##" id="btnSave">添加</a>
    </p>
    <table width="100%" class="UserTbale nomar mart20 Info WorkStory workli border-solid">
        <thead>
        <tr>
            <th width="70" class="alignleft">工作地点</th>
            <th width="40">操作</th>
        </tr>
        </thead>
        <tbody>
        <#if officeAddrList?exists&&officeAddrList?size gt 0>
            <#list officeAddrList as officeAddr>
            <tr>
                <th>${officeAddr.address?if_exists}</th>
                <td>
                    <a href="##" uid="${officeAddr.id?c}" order="${officeAddr.displayOrder?c}" class="icon icon-up" title="上移"></a>
                    <a href="##" uid="${officeAddr.id?c}" order="${officeAddr.displayOrder?c}" class="icon icon-down" title="下移"></a>
                    <a href="##" uid="${officeAddr.id?c}" class="icon icon-edit" title="编辑"></a>
                    <a href="##" uid="${officeAddr.id?c}" class="icon icon-delete" title="删除"></a>
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
        <input type="hidden" name="hrOfficeAddr.id" id="hrOfficeAddr.id">
    </form>
<!--分页begin-->
    <@pager.pagerCommon object=pageObj?if_exists max=14/>
<!--分页over-->
</@setting_common.setting_common>