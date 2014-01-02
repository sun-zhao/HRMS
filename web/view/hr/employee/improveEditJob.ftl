<#import "/view/template/common.ftl" as common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@common.html module="HR">
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
    var submited = false;

    $(document).ready(function () {
        $('#hrEmployeeJob\\.startDate').off('focus').on('focus', function () {
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
        $('#hrEmployeeJob\\.endDate').off('focus').on('focus', function () {
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
        $('#addJob').off('click').on('click', function () {
            if (!submited) {
                WEBUTILS.popMask.show();
                document.editForm.submit();
                submited=true;
            }
        });
    });
</script>
<!--左侧类目begin-->
<div class="PopLeft floatleft">
    <ul>
        <li><a href="#">个人信息</a></li>
        <li><a href="#">家庭信息</a></li>
        <li><a class="current" href="#">工作经历</a></li>
        <li><a href="#">教育经历</a></li>
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
                    <th width="70" class="alignleft">公司名称</th>
                    <th width="60">担任职务</th>
                    <th width="60">入职日期</th>
                    <th width="60">离职日期</th>
                    <th width="40">操作</th>
                </tr>
                </thead>
                <tbody>
                <#if employeeJobList?exists&&employeeJobList?size gt 0>
                    <#list employeeJobList as job>
                    <tr>
                        <td>${job.name?if_exists}</td>
                        <td>${job.title?if_exists}</td>
                        <td>${job.startDate?string("yyyy-MM-dd")}</td>
                        <td>${job.endDate?string("yyyy-MM-dd")}</td>
                        <td>
                            <a title="修改" class="icon icon-edit" href="#"></a>
                            <a title="删除" class="icon icon-delete" href="#"></a>
                        </td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
            <form action="/hr/employeeJob!improveSaveJob.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
                  onsubmit="return false;">
            <table width="100%" class="UserTbale nomar Info mart5">
                <tbody>
                <tr>
                    <th width="60">公司名称：</th>
                    <td width="160">
                        <input type="text" class="edit"  id="hrEmployeeJob.name" name="hrEmployeeJob.name">
                    </td>
                    <th width="60">职位：</th>
                    <td w>
                        <input type="text" class="edit"  id="hrEmployeeJob.title" name="hrEmployeeJob.title">
                    </td>
                </tr>
                <tr>
                    <th width="60">开始日期：</th>
                    <td width="160">
                        <input type="text" class="edit"  id="hrEmployeeJob.startDate" name="hrEmployeeJob.startDate">
                    </td>
                    <th width="60">结束日期：</th>
                    <td w>
                        <input type="text" class="edit"  id="hrEmployeeJob.endDate" name="hrEmployeeJob.endDate">
                    </td>
                </tr>
                <tr>
                    <th>描述：</th>
                    <td colspan="3">
                        <input type="text" class="edit"  id="hrEmployeeJob.description" name="hrEmployeeJob.description">
                    </td>
                </tr>
                </tbody>
            </table>
            </form>
            <a class="add-more block aligncenter" href="##" id="addJob"><em class="icon icon-add"></em> 新增工作经历</a>
        </div>

<p class="alignright mart5">
    <a class="button" href="/hr/employeeEdu!improveEditEdu.dhtml" id="nextSave">下一步</a>
</p>
<!--右侧详细信息over-->
</@common.html>


