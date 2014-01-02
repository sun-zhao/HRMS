<#import "/view/template/common.ftl" as common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@common.html module="HR">
<script type="text/javascript">
    var submited = false;
    $(document).ready(function () {
    });
</script>
<!--左侧类目begin-->
<div class="PopLeft floatleft">
    <ul>
        <li><a href="/hr/employee!viewInfo.dhtml?id=${hrEmployee.id?c}">个人信息</a></li>
        <li><a href="/hr/employee!viewFamily.dhtml?id=${hrEmployee.id?c}">家庭信息</a></li>
        <li><a href="/hr/employeeJob!viewJob.dhtml?id=${hrEmployee.id?c}">工作经历</a></li>
        <li><a class="current" href="#">教育经历</a></li>
    </ul>
</div>
<!--左侧类目over-->
<!--右侧详细信息begin-->
        <div class="PopRight g-edit border-solid">
            <div class="UserInfo-top">
                <span class="PerImg floatleft">
                    <img src="${user.avstar100?if_exists}">
                <em class="bttip"></em>
                </span>
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
                            <#if hrEmployee.dutyLevel?exists>
                                <#if hrEmployee.dutyLevel==1>
                                    总裁
                                <#elseif hrEmployee.dutyLevel==2>
                                    副总裁
                                <#elseif hrEmployee.dutyLevel==3>
                                    总监
                                <#elseif hrEmployee.dutyLevel==4>
                                    副总监
                                <#elseif hrEmployee.dutyLevel==5>
                                    经理
                                <#elseif hrEmployee.dutyLevel==6>
                                    主管
                                <#elseif hrEmployee.dutyLevel==7>
                                    职员
                                <#else >
                                    未设置
                                </#if>
                            <#else >
                                未设置
                            </#if>
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
                </tr>
                </thead>
                <tbody>
                <#if employeeEduList?exists&&employeeEduList?size gt 0>
                    <#list employeeEduList as edu>
                    <tr title="${edu.description?if_exists}">
                        <td style="text-align: left;">${edu.name?if_exists}</td>
                        <td>${edu.title?if_exists}</td>
                        <td>${edu.startDate?string("yyyy-MM-dd")}</td>
                        <td>${edu.endDate?string("yyyy-MM-dd")}</td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
<!--右侧详细信息over-->
<p class="alignright mart5"><a class="button" href="/hr/employeeEdu!editEdu.dhtml?id=${hrEmployee.id?c}" >修改信息</a></p>
</@common.html>


