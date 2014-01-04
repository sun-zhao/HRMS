<#import "/view/template/structure/setting/settingCommon.ftl" as setting_common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@setting_common.setting_common>
<script type="text/javascript">
    var submited = false;
    var ignoreName='';
    function pagerAction(start, rows) {
        var searchUrl = '/hr/contractTemplate.dhtml';
        searchUrl += '?start=' + start + '&rows=' + rows;
        var searchKey = $('#name').val();
        if(searchKey){
            searchKey=searchKey.trim();
            searchUrl+='&name='+searchKey;
        }
        searchUrl = encodeURI(searchUrl);
        document.location.href = searchUrl
    }
    $(document).ready(function () {
        $('#name').off('keyup').on('keyup', function (e) {
            e = (e) ? e : ((window.event) ? window.event : "")
            var keyCode = e.keyCode ? e.keyCode : (e.which ? e.which : e.charCode);
            if (keyCode ==13) {
                pagerAction(0,20);
            }
        });
        $('#hrContractTemplate\\.name').off('keyup').on('keyup', function (e) {
            e = (e) ? e : ((window.event) ? window.event : "")
            var keyCode = e.keyCode ? e.keyCode : (e.which ? e.which : e.charCode);
            if (keyCode ==13) {
                $('#btnSave').trigger('click');
            }
        });
        $('#btnSave').off('click').on('click', function () {
            var name=$('#hrContractTemplate\\.name').val();
            if(name){
                name=name.trim();
                if(name!=''){
                    $.ajax({
                        type:'POST',
                        url:'/hr/contractTemplate!validateName.dhtml',
                        data:{name:name,ignore:ignoreName},
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
                                    WEBUTILS.msg.alertFail('不能重复添加合同模版名称['+name+']');
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
                    url:'/hr/contractTemplate!getTemplate.dhtml',
                    data:{id:uid},
                    dataType:'json',
                    success:function (jsonData) {
                        if (jsonData) {
                            if (jsonData['result'] == '0') {
                                $('#hrContractTemplate\\.name').val(jsonData['name']);
                                $('#hrContractTemplate\\.id').val(jsonData['id']);
                                $('#btnSave').text('修改');
                                ignoreName=jsonData['name'];
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
                var r=confirm("此操作将删除该合同模版,是否继续?");
                if(r==true){
                    WEBUTILS.popMask.show();
                    document.location.href='/hr/contractTemplate!delete.dhtml?id='+uid;
                }
            }
        });

        $('.icon-add').off('click').on('click',function(){
            var uid=$(this).attr('uid');
            var name=$(this).attr('name');
            if(uid){
                WEBUTILS.popWindow.createPopWindow(788, 600, '['+name+']的模版内容', '/hr/contractTemplate!index.dhtml?id='+uid, true);
                WEBUTILS.popWindow.offset('30%', false);
            }
        });
    });
</script>
<!--排序搜索begin-->
<div class="clearfix gotop">
    <!--搜索框begin-->
    <div class="search-area floatleft">
        <input type="text" id="name" name="name" class="search-int" placeholder="请输入模版名称"
               <#if name?exists>value="${name?if_exists}"</#if>>
        <em class="line-y"></em>
        <a href="##" class="search-btn"><i class="app-icon ico-search"></i></a>
    </div>
    <!--搜索框over-->
</div>
<!--排序搜索over-->
    <form action="/hr/contractTemplate!saveName.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
      onsubmit="return false;">
    <p class="font14 mart10">添加一个合同模版</p>
    <p class="clearfix mart10">
        <input type="text" align="middle" class="ThemeInput width-200 floatleft" id="hrContractTemplate.name" name="hrContractTemplate.name">
        <a class="button marl20 floatleft" href="##" id="btnSave">添加</a>
    </p>
    <table width="100%" class="UserTbale nomar mart20 Info WorkStory workli border-solid">
        <thead>
        <tr>
            <th width="70" class="alignleft">合同模版</th>
            <th width="40">操作</th>
        </tr>
        </thead>
        <tbody>
        <#if templateList?exists&&templateList?size gt 0>
            <#list templateList as template>
            <tr>
                <th><span style="cursor: pointer;">${template.name?if_exists}</span></th>
                <td>
                    <a href="##" uid="${template.id?c}" name="${template.name?if_exists}" class="icon icon-add" title="设置模版"></a>
                    <a href="/hr/contractTemplate!print.dhtml?id=${template.id?c}" target="_blank" class="icon icon-print" title="打印预览"></a>
                    <a href="##" uid="${template.id?c}" class="icon icon-edit" title="编辑"></a>
                    <a href="##" uid="${template.id?c}" class="icon icon-delete" title="删除"></a>
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
        <input type="hidden" name="hrContractTemplate.id" id="hrContractTemplate.id">
    </form>
<!--分页begin-->
    <@pager.pagerCommon object=pageObj?if_exists max=14/>
<!--分页over-->
</@setting_common.setting_common>