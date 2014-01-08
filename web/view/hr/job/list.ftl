<#import "/view/template/structure/setting/settingCommon.ftl" as setting_common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@setting_common.setting_common>
<script type="text/javascript">
    var submited = false;
    var ignoreName='';
    function pagerAction(start, rows) {
        var searchUrl = '/hr/job.dhtml';
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
        $('#hrJob\\.name').off('keyup').on('keyup', function (e) {
            e = (e) ? e : ((window.event) ? window.event : "")
            var keyCode = e.keyCode ? e.keyCode : (e.which ? e.which : e.charCode);
            if (keyCode ==13) {
                $('#btnSave').trigger('click');
            }
        });
        $('#btnSave').off('click').on('click', function () {
            var name=$('#hrJob\\.name').val();
            if(name){
                name=name.trim();
                if(name!=''){
                    $.ajax({
                        type:'POST',
                        url:'/hr/job!validateName.dhtml',
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
                                    WEBUTILS.msg.alertFail('不能重复添加岗位['+name+']');
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
                    url:'/hr/job!getJob.dhtml',
                    data:{id:uid},
                    dataType:'json',
                    success:function (jsonData) {
                        if (jsonData) {
                            if (jsonData['result'] == '0') {
                                $('#hrJob\\.name').val(jsonData['name']);
                                $('#hrJob\\.id').val(jsonData['id']);
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
            var count=$(this).attr('count');
            if(uid&&count){
                count=parseInt(count);
                if(count>0){
                    WEBUTILS.msg.alertFail('该职位下还有员工存在,暂时无法删除!');
                    return false;
                }
                var r=confirm("此操作将删除该职位,是否继续?");
                if(r==true){
                    WEBUTILS.popMask.show();
                    document.location.href='/hr/job!delete.dhtml?id='+uid;
                }
            }
        });
    });
</script>
<!--排序搜索begin-->
<div class="clearfix gotop">
    <!--搜索框begin-->
    <div class="search-area floatleft">
        <input type="text" id="name" name="name" class="search-int" placeholder="请输入职位名称"
               <#if name?exists>value="${name?if_exists}"</#if>>
        <em class="line-y"></em>
        <a href="##" class="search-btn"><i class="app-icon ico-search"></i></a>
    </div>
    <!--搜索框over-->
</div>
<!--排序搜索over-->
    <form action="/hr/job!save.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
      onsubmit="return false;">
    <p class="font14 mart10">添加一个职位</p>
    <p class="clearfix mart10">
        <input type="text" align="middle" class="ThemeInput width-200 floatleft" id="hrJob.name" name="hrJob.name">
        <a class="button marl20 floatleft" href="##" id="btnSave">添加</a>
    </p>
    <table width="100%" class="UserTbale nomar mart20 Info WorkStory workli border-solid">
        <thead>
        <tr>
            <th width="70" class="alignleft">职位名称</th>
            <th width="60">现有用户数</th>
            <th width="40">操作</th>
        </tr>
        </thead>
        <tbody>
        <#if jobList?exists&&jobList?size gt 0>
            <#list jobList as job>
            <tr>
                <th>${job.name?if_exists}</th>
                <td>${job.count?c}</td>
                <td>
                    <a href="##" uid="${job.id?c}" class="icon icon-edit" title="编辑"></a>
                    <a href="##" uid="${job.id?c}" count="${job.count?c}" class="icon icon-delete" title="删除"></a>
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
        <input type="hidden" name="hrJob.id" id="hrJob.id">
    </form>
<!--分页begin-->
    <@pager.pagerCommon object=pageObj?if_exists max=14/>
<!--分页over-->
</@setting_common.setting_common>