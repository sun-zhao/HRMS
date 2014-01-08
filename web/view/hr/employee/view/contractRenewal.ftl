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
                <table width="380" class="UserTbale Info floatleft" style="border-top: 0px;">
                    <tbody>
                    <tr>
                        <td colspan="4"><em class="icon icon-user"></em>
                        ${hrEmployee.userName?if_exists}
                        </td>
                    </tr>
                    <tr>
                        <th style="width: 60px;">公司：</th>
                        <td style="width: 100px;">
                        ${(hrEmployee.orgId.name)?if_exists}
                        </td>
                        <th style="width: 60px;">部门：</th>
                        <td >
                        ${hrEmployee.deptName?if_exists}
                        </td>
                    </tr>
                    <tr>
                        <th>职级：</th>
                        <td >
                            <#if hrEmployee.dutyLevel?exists>
                            ${(hrEmployee.dutyLevel.name)?if_exists}
                            <#else>
                                未设置
                            </#if>
                        </td>
                        <th >职位：</th>
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
            <table width="100%" class="UserTbale nomar Info mart5">
                <tbody>
                <tr>
                    <th style="width: 75px;">合同编号：</th>
                    <td style="width: 130px;">
                        ${hrContract.contractNo?if_exists}
                    </td>
                    <th style="width: 75px;">合同类别：</th>
                    <td>
                       <#if hrContract.contractType?exists>
                           <#if hrContract.contractType==0>
                               无固定期劳动合同
                           <#elseif hrContract.contractType==1>
                               固定期劳动合同
                           </#if>
                        </#if>
                    </td>
                </tr>
                <tr>
                    <th >签订日期：</th>
                    <td >
                    ${hrContract.startDate?string("yyyy-MM-dd")}
                    </td>
                    <th >截止日期：</th>
                    <td>
                    ${hrContract.endDate?string("yyyy-MM-dd")}
                    </td>
                </tr>
                <tr>
                    <th >工作地点：</th>
                    <td >
                        ${hrContract.workArea?if_exists}
                    </td>
                    <th >工时制度：</th>
                    <td>
                        ${hrContract.workTime?if_exists}
                    </td>
                </tr>
                <tr>
                    <th >薪资：</th>
                    <td colspan="3">
                    ${hrContract.pay?c}
                    </td>
                </tr>
                <tr>
                    <th >保险缴纳地：</th>
                    <td colspan="3">
                        ${hrContract.insuranceArea?if_exists}
                    </td>
                </tr>
                <tr>

                    <th >保险种类：</th>
                    <td colspan="3">
                        ${hrContract.insuranceType?if_exists}
                    </td>
                </tr>
                <tr>

                    <th >合同模版：</th>
                    <td colspan="3">
                        ${(hrContract.templateId.name)?if_exists}
                    </td>
                </tr>
                <tr>
                    <th>备注：</th>
                    <td colspan="3">
                        ${hrContract.remarks?if_exists}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
<!--右侧详细信息over-->
<p class="alignright mart5"><a class="button" href="/hr/contract!view.dhtml?empId=${hrEmployee.id?c}" >返回</a></p>
</@common.html>


