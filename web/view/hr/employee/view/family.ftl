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
        <li><a class="current" href="##">家庭信息</a></li>
        <li><a href="/hr/employeeJob!viewJob.dhtml?id=${hrEmployee.id?c}">工作经历</a></li>
        <li><a href="/hr/employeeEdu!viewEdu.dhtml?id=${hrEmployee.id?c}">教育经历</a></li>
        <li <#if hrEmployee.contractFlag==0> class="noname" </#if>><a href="/hr/contract!view.dhtml?empId=${hrEmployee.id?c}">合同信息</a></li>
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
            <table width="100%" class="UserTbale nomar Info mart5">
                <tbody>
                <tr>
                    <th width="75">家人姓名：</th>
                    <td width="130">
                        ${hrEmployeeFamily.familyName?if_exists}
                    </td>
                    <th width="60">家人关系：</th>
                    <td>
                        <#if hrEmployeeFamily.familyRelation?exists>
                            <#if hrEmployeeFamily.familyRelation==0>
                                配偶
                            <#elseif hrEmployeeFamily.familyRelation==1>
                                父亲
                            <#elseif hrEmployeeFamily.familyRelation==1>
                                母亲
                            <#elseif hrEmployeeFamily.familyRelation==1>
                                爷爷
                            <#elseif hrEmployeeFamily.familyRelation==1>
                                奶奶
                            <#else >
                                未设置
                            </#if>
                        <#else >
                            未设置
                        </#if>
                    </td>
                </tr>
                <tr>
                    <th>家人电话：</th>
                    <td colspan="3">
                        ${hrEmployeeFamily.familyTel?if_exists}
                    </td>
                </tr>
                <tr>
                    <th>家庭住址：</th>
                    <td colspan="3">
                        ${hrEmployeeFamily.familyAddress?if_exists}
                    </td>
                </tr>
                <tr>
                    <th width="75">婚姻状况：</th>
                    <td width="130">
                        <#if hrEmployeeFamily.marry?exists>
                            <#if hrEmployeeFamily.marry==0>
                                未婚
                            <#elseif hrEmployeeFamily.marry==1>
                                已婚
                            <#else >
                                未设置
                            </#if>
                        <#else >
                            未设置
                        </#if>
                    </td>
                    <th width="60">户口性质：</th>
                    <td>
                        <#if hrEmployeeFamily.residenceBookletType?exists>
                            <#if hrEmployeeFamily.residenceBookletType==0>
                                城镇户口
                            <#elseif hrEmployeeFamily.residenceBookletType==1>
                                农村户口
                            <#else >
                                未设置
                            </#if>
                        <#else >
                            未设置
                        </#if>
                    </td>
                </tr>
                <tr>
                    <th>户口所在地：</th>
                    <td colspan="3">
                        ${hrEmployeeFamily.residenceBooklet?if_exists}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
<!--右侧详细信息over-->
<p class="alignright mart5"><a class="button" href="/hr/employee!editFamily.dhtml?id=${hrEmployee.id?c}" >修改信息</a></p>
</@common.html>


