<#import "/view/template/structure/setting/settingCommon.ftl" as setting_common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@setting_common.setting_common>
<script type="text/javascript">
    var submited = false;
    var ignoreName='';
    $(document).ready(function () {
        $('#hrOrg\\.name').off('keyup').on('keyup', function (e) {
            e = (e) ? e : ((window.event) ? window.event : "")
            var keyCode = e.keyCode ? e.keyCode : (e.which ? e.which : e.charCode);
            if (keyCode ==13) {
                $('#btnSave').trigger('click');
            }
        });
        $('#btnSave').off('click').on('click', function () {
            var name=$('#hrOrg\\.name').val();
            if(name){
                name=name.trim();
                if(name!=''){
                    $.ajax({
                        type:'POST',
                        url:'/hr/org!validateName.dhtml',
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
                                    WEBUTILS.msg.alertFail('不能重复添加公司['+name+']');
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
                    url:'/hr/org!getOrg.dhtml',
                    data:{id:uid},
                    dataType:'json',
                    success:function (jsonData) {
                        if (jsonData) {
                            if (jsonData['result'] == '0') {
                                $('#hrOrg\\.name').val(jsonData['name']);
                                $('#hrOrg\\.id').val(jsonData['id']);
                                ignoreName=jsonData['name'];
                                $('#btnSave').text('修改');
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
            var count=$(this).attr('count');
            if(uid&&count){
                count=parseInt(count);
                if(count>0){
                    WEBUTILS.msg.alertFail('该公司下还有员工存在,暂时无法删除!');
                    return false;
                }
                var r=confirm("此操作将删除该公司,是否继续?");
                if(r==true){
                    WEBUTILS.popMask.show();
                    document.location.href='/hr/org!delete.dhtml?id='+uid;
                }
            }
        });
        $('.icon-up').off('click').on('click',function(){
            var uid=$(this).attr('uid');
            var order=$(this).attr('order');
            if(order){
                if(order==1){
                    WEBUTILS.msg.alertFail('该公司已排第一位,无法再进行上移操作!');
                    return false;
                }
            }
            if(uid){
                WEBUTILS.popMask.show();
                document.location.href='/hr/org!up.dhtml?id='+uid;
            }
        });
        $('.icon-down').off('click').on('click',function(){
            var uid=$(this).attr('uid');
            var order=$(this).attr('order');
            if(order){
                if(order==${maxOrder?c}){
                    WEBUTILS.msg.alertFail('该公司已排最后一位,无法再进行下移操作!');
                    return false;
                }
            }
            if(uid){
                WEBUTILS.popMask.show();
                document.location.href='/hr/org!down.dhtml?id='+uid;
            }
        });
    });
</script>
    <form action="/hr/org!save.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
      onsubmit="return false;">
    <p class="font14 mart10">添加一个公司</p>
    <p class="clearfix mart10">
        <input type="text" align="middle" class="ThemeInput width-200 floatleft" id="hrOrg.name" name="hrOrg.name">
        <a class="button marl20 floatleft" href="##" id="btnSave">添加</a>
    </p>
    <table width="100%" class="UserTbale nomar mart20 Info WorkStory workli border-solid">
        <thead>
        <tr>
            <th width="70" class="alignleft">公司名称</th>
            <th width="60">现有用户数</th>
            <th width="40">操作</th>
        </tr>
        </thead>
        <tbody>
        <#if orgList?exists&&orgList?size gt 0>
            <#list orgList as org>
            <tr>
                <th>${org.name?if_exists}</th>
                <td>${org.count?c}</td>
                <td>
                    <a href="##" uid="${org.id?c}" order="${org.displayOrder?c}" class="icon icon-up" title="上移"></a>
                    <a href="##" uid="${org.id?c}" order="${org.displayOrder?c}" class="icon icon-down" title="下移"></a>
                    <a href="##" uid="${org.id?c}" class="icon icon-edit" title="编辑"></a>
                    <a href="##" uid="${org.id?c}" count="${org.count?c}" class="icon icon-delete" title="删除"></a>
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
        <input type="hidden" name="hrOrg.id" id="hrOrg.id">
    </form>
<!--分页begin-->
    <@pager.pagerCommon object=pageObj?if_exists max=20/>
<!--分页over-->
</@setting_common.setting_common>