<#import "/view/template/common.ftl" as common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@common.html module="HR">
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/webutils/webutils.validator.js"></script>
<script type="text/javascript" src="/js/webutils/reg.js"></script>
<script type="text/javascript">
    var submited = false;
    function initValidator() {
        WEBUTILS.validator.init({
            modes:[
                {
                    id:'hrEmployeeEdu\\.name',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入学校名称'}
                    ]
                },
                {
                    id:'hrEmployeeEdu\\.title',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入专业学历'}
                    ]
                },
                {
                    id:'hrEmployeeEdu\\.startDate',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入入学日期'}
                    ]
                },
                {
                    id:'hrEmployeeEdu\\.endDate',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入毕业日期'}
                    ]
                },
                {
                    id:'hrEmployeeEdu\\.description',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入核心课程'}
                    ]
                }
            ]
        }, true);
    }
    $(document).ready(function () {
        initValidator();
        $('#hrEmployeeEdu\\.startDate').off('focus').on('focus', function () {
            var obj = $(this);
            WdatePicker({
                doubleCalendar:false,
                readOnly:true,
                highLineWeekDay:true,
                skin:'twoer',
                onpicked:function () {
                    $(obj).trigger('blur');
                }
            });
        });
        $('#hrEmployeeEdu\\.endDate').off('focus').on('focus', function () {
            var obj = $(this);
            WdatePicker({
                doubleCalendar:false,
                readOnly:true,
                highLineWeekDay:true,
                skin:'twoer',
                onpicked:function () {
                    $(obj).trigger('blur');
                }
            });
        });
        $('#addEdu').off('click').on('click', function () {
            if (!submited) {
                WEBUTILS.validator.checkAll();
                window.setTimeout(function () {
                    var passed = WEBUTILS.validator.isPassed();
                    if (passed) {
                        WEBUTILS.popMask.show();
                        document.editForm.submit();
                        submited=true;
                    } else {
                        WEBUTILS.validator.showErrors();
                    }
                }, 500);
            }
        });
        $('.icon-delete').off('click').on('click',function(){
            var uid=$(this).attr('uid');
                if(uid){
                    var r=confirm("将删除选定的教育经历,是否继续?");
                    if(r==true){
                        document.location.href='/hr/employeeEdu!improveDelete.dhtml?id='+uid;
                    }
                }
        });

    });
</script>
<!--左侧类目begin-->
<div class="PopLeft floatleft">
    <ul>
        <li><a href="#">个人信息</a></li>
        <li><a href="#">家庭信息</a></li>
        <li><a href="#">工作经历</a></li>
        <li><a class="current" href="#">教育经历</a></li>
    </ul>
</div>
<!--左侧类目over-->
<!--右侧详细信息begin-->
        <div class="PopRight border-solid">
            <div class="UserInfo-top">
                <span class="PerImg floatleft"><img src="${user.avstar100?if_exists}"></span>
                <table width="350" class="UserTbale Info floatleft" style="border-top: 0px;">
                    <tbody>
                    <tr>
                        <td colspan="4"><em class="icon icon-user"></em>
                        ${hrEmployee.userName}
                        </td>
                    </tr>
                    <tr>
                        <th width="60">部门：</th>
                        <td colspan="3">
                            ${hrEmployee.deptName}
                        </td>
                    </tr>
                    <tr>
                        <th>职级：</th>
                        <td width="130">
                            <select class="edit" id="hrEmployee.dutyLevel"  name="hrEmployee.dutyLevel" disabled="disabled">
                                <option value="1">总裁</option>
                                <option value="2">副总裁</option>
                                <option value="3">总监</option>
                                <option value="4">副总监</option>
                                <option value="5">经理</option>
                                <option value="6">主管</option>
                                <option value="7" selected="selected">职员</option>
                            </select>
                        </td>
                        <th width="60">职位：</th>
                        <td>
                        ${hrEmployee.jobName}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <table width="100%" class="UserTbale nomar Info WorkStory">
                <thead>
                <tr>
                    <th width="70" class="alignleft">学校名称</th>
                    <th width="60">专业学历</th>
                    <th width="60">入学日期</th>
                    <th width="60">毕业日期</th>
                    <th width="40">操作</th>
                </tr>
                </thead>
                <tbody>
                <#if employeeEduList?exists&&employeeEduList?size gt 0>
                    <#list employeeEduList as edu>
                    <tr>
                        <td style="text-align: left;">${edu.name?if_exists}</td>
                        <td>${edu.title?if_exists}</td>
                        <td>${edu.startDate?string("yyyy-MM-dd")}</td>
                        <td>${edu.endDate?string("yyyy-MM-dd")}</td>
                        <td>
                            <a title="修改" class="icon icon-edit" href="##" uid="${edu.id?c}"></a>
                            <a title="删除" class="icon icon-delete" href="##" uid="${edu.id?c}"></a>
                        </td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
            <form action="/hr/employeeEdu!improveSaveEdu.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
                  onsubmit="return false;">
            <table width="100%" class="UserTbale nomar Info mart5">
                <tbody>
                <tr>
                    <th width="60">学校名称：</th>
                    <td width="160">
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployeeEdu.name" name="hrEmployeeEdu.name">
                            <label class="control-label"></label>
                        </div>
                    </td>
                    <th width="60">专业学历：</th>
                    <td >
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployeeEdu.title" name="hrEmployeeEdu.title">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th width="60">入学日期：</th>
                    <td width="160">
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployeeEdu.startDate" name="hrEmployeeEdu.startDate">
                            <label class="control-label"></label>
                        </div>
                    </td>
                    <th width="60">毕业日期：</th>
                    <td >
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployeeEdu.endDate" name="hrEmployeeEdu.endDate">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>核心课程：</th>
                    <td colspan="3">
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployeeEdu.description" name="hrEmployeeEdu.description">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            </form>
            <a class="add-more block aligncenter" href="##" id="addEdu"><em class="icon icon-add"></em> 新增教育经历</a>
        </div>

<p class="alignright mart5">
    <a class="button" href="/hr/employeeJob!improveEditJob.dhtml" id="preSave">上一步</a>&nbsp;
    <a class="button" href="/hr/employee!improveDone.dhtml" id="nextSave">完成</a>
</p>
<!--右侧详细信息over-->
</@common.html>


