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
        <li><a href="/hr/employeeEdu!viewEdu.dhtml?id=${hrEmployee.id?c}">教育经历</a></li>
        <li <#if hrEmployee.contractFlag==0> class="noname" </#if>><a href="##" class="current">合同信息</a></li>
        <li class="noname"><a href="##">考勤信息</a></li>
        <li class="noname"><a href="##">保险信息</a></li>
        <li class="noname"><a href="##">薪资体系</a></li>
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
                            ${(hrEmployee.dutyLevel.name)?if_exists}
                            <#else>
                                未设置
                            </#if>
                        </td>
                        <th width="60">职位：</th>
                        <td>
                            <#if hrEmployee.jobId?exists>
                                ${(hrEmployee.jobId.name)?if_exists}
                            <#else>
                                未设置
                            </#if>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <table width="100%" class="UserTbale nomar Info WorkStory">
                <thead>
                <tr>
                    <th width="70" class="alignleft">合同编号</th>
                    <th >合同类别</th>
                    <th width="60">签订日期</th>
                    <th width="60">截止日期</th>
                    <th width="60">状态</th>
                </tr>
                </thead>
                <tbody>
                <#if contractList?exists&&contractList?size gt 0>
                    <#list contractList as contract>
                    <tr title="${contract.remarks?if_exists}">
                        <td style="text-align: left;">${contract.contractNo?if_exists}</td>
                        <td>
                            <#if contract.contractType==0>
                                无固定期劳动合同
                            <#elseif contract.contractType==1>
                                固定期劳动合同
                            </#if>
                        </td>
                        <td>${contract.startDate?string("yyyy-MM-dd")}</td>
                        <td>${contract.endDate?string("yyyy-MM-dd")}</td>
                        <td>
                            <#if contract.contractState==0>
                                新签
                            <#elseif contract.contractState==1>
                                续签
                            <#elseif contract.contractState==2>
                                终止
                            </#if>
                        </td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
<!--右侧详细信息over-->
<p class="alignright mart5"><a class="button" href="/hr/employeeJob!editJob.dhtml?id=${hrEmployee.id?c}" >修改信息</a></p>
</@common.html>


