<#import "/view/template/structure/common.ftl" as common_common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@common_common.common_common>
<script type="text/javascript">
    var submited = false;

    $(document).ready(function () {
        <#if hrEmployee.complete==3>
            WEBUTILS.msg.alertInfo("请在即将弹出的信息框里完善您的个人信息...", 5000, function () {
                WEBUTILS.popWindow.createPopWindow(650, 530, '${hrEmployee.userName?if_exists}的人事档案', '/hr/employee!improveEdit.dhtml', true);
                WEBUTILS.popWindow.offset('30%', false);
            });
        </#if>
    });
</script>
<div class="UserInfo-top clearfix">
    <span class="PerImg floatleft">
        <img src="${user.avstar100?if_exists}">
    <em class="bttip"></em>
    </span>
    <table class="UserTbale Info mine floatleft" style="border-top: 0px;">
        <tbody>
        <tr>
            <th colspan="6">
                <em class="icon icon-user"></em>
            ${hrEmployee.userName?if_exists}(${(hrEmployee.orgId.name)?if_exists})
            <#--<a class="a-link marl30" href="#">修改我的档案 <em class="icon icon-edit"></em></a>-->
            </th>
        </tr>
        <tr>
            <th style="width: 80px;">部门：</th>
            <td style="width: 120px;">${hrEmployee.deptName?if_exists}</td>
            <th style="width: 80px;">职级：</th>
            <td style="width: 120px;">
                <#if hrEmployee.dutyLevel?exists>
                ${(hrEmployee.dutyLevel.name)?if_exists}
                <#else>
                    未设置
                </#if>
            </td>
            <th style="width: 80px;">职位：</th>
            <td>
                <#if hrEmployee.jobId?exists>
                ${(hrEmployee.jobId.name)?if_exists}
                <#else>
                    未设置
                </#if>
            </td>
        </tr>
        <tr>
            <th >用工类型：</th>
            <td >
                <#if hrEmployee.empType?exists>
                    <#if hrEmployee.empType==1>
                        劳务
                    <#elseif hrEmployee.empType==0>
                        实习
                    </#if>
                <#else >
                    &nbsp;
                </#if>
            </td>
            <th width="80">工作状态：</th>
            <td width="120">
                <#if hrEmployee.workState?exists>
                    <#if hrEmployee.workState==0>
                        在职
                    <#elseif hrEmployee.workState==1>
                        离职
                    </#if>
                <#else >
                    &nbsp;
                </#if>
            </td>
            <th width="80">入职日期：</th>
            <td>
                <#if hrEmployee.entryDate?exists>
                    ${hrEmployee.entryDate?string("yyyy-MM-dd")}
                </#if>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<table width="100%" class="UserTbale nomar Info mine mart10">
    <tbody>
    <tr>
        <th style="width: 70px;">手机：</th>
        <td style="width: 130px;">${hrEmployee.mobileTel?if_exists} <em class="icon icon-phone"></em></td>
        <th style="width: 70px;">办公电话：</th>
        <td style="width: 150px;">${hrEmployee.officeTel?if_exists} <em class="icon icon-phone2"></em></td>
        <th style="width: 70px;">电子邮件：</th>
        <td><a class="a-link" href="#">${hrEmployee.userEmail?if_exists}</a> <em class="icon icon-mail"></em></td>
    </tr>
    <tr>
        <th width="80">工作地点：</th>
        <td colspan="5">
        ${(hrEmployee.officeAddress.address)?if_exists}
        </td>
    </tr>
    </tbody>
</table>
<table width="100%" class="UserTbale nomar Info mine mart5">
    <tbody>
    <tr>
        <th style="width: 70px;">性别：</th>
        <td style="width: 100px;">
            <#if hrEmployee.userSex==1>
                男
            <#elseif hrEmployee.userSex==2>
                女
            </#if>
        </td>
        <th style="width: 70px;">学历：</th>
        <td style="width: 100px;">${(hrEmployee.eduLevel.codeName)?if_exists}</td>
        <th style="width: 70px;">民族：</th>
        <td style="width: 100px;">${(hrEmployee.nationalityId.codeName)?if_exists}</td>
        <th style="width: 70px;">政治面貌：</th>
        <td>${(hrEmployee.politicsLevel.codeName)?if_exists}</td>
    </tr>
    <tr>
        <th>国籍：</th>
        <td>
        ${(hrEmployee.contryId.codeName)?if_exists}
        </td>
        <th>省份：</th>
        <td>
        ${(hrEmployee.provinceId.provinceName)?if_exists}
        </td>
        <th>城市：</th>
        <td>
        ${(hrEmployee.cityId.cityName)?if_exists}
        </td>
        <th>出生日期：</th>
        <td>
            <#if hrEmployee.birthDay?exists>
            ${hrEmployee.birthDay?string("yyyy-MM-dd")}
            </#if>
        </td>
    </tr>
    <tr>
        <th>身份证号：</th>
        <td colspan="3">
        ${hrEmployee.idCard?if_exists}
        </td>
        <th>银行卡号：</th>
        <td colspan="3">
        ${hrEmployee.bankCard?if_exists}
            <em class="icon icon-card"></em>
        </td>
    </tr>
    </tbody>
</table>
<table width="100%" class="UserTbale nomar Info mine mine-work mart5">
    <tbody>
    <tr>
        <th class="p-top10" colspan="4"><em class="icon icon-work"></em> 工作经历</th>
    </tr>
    <tr>
        <th style="width: 160px;">公司名称</th>
        <th style="width: 100px;">担任职务</th>
        <th style="width: 110px;">入职日期</th>
        <th style="width: 110px;">离职日期</th>
        <th style="width: auto;">描述</th>
    </tr>
        <#if employeeJobList?exists&&employeeJobList?size gt 0>
            <#list employeeJobList as job>
            <tr>
                <td>${job.name?if_exists}</td>
                <td>${job.title?if_exists}</td>
                <td>${job.startDate?string("yyyy-MM-dd")}</td>
                <td>${job.endDate?string("yyyy-MM-dd")}</td>
                <td>${job.description?if_exists}</td>
            </tr>
            </#list>
        <#else >
        <tr>
            <td colspan="5">暂无</td>
        </tr>
        </#if>
    </tbody>
</table>
<table width="100%" class="UserTbale nomar Info mine mine-work mart5">
    <tbody>
    <tr>
        <th class="p-top10" colspan="4"><em class="icon icon-house"></em> 院校经历</th>
    </tr>
    <tr>
        <th style="width: 160px;">院校名称</th>
        <th style="width: 100px;">专业和学历</th>
        <th style="width: auto;">核心课程</th>
        <th style="width: 110px;">入学日期</th>
        <th style="width: 110px;">毕业日期</th>
    </tr>
        <#if employeeEduList?exists&&employeeEduList?size gt 0>
            <#list employeeEduList as edu>
            <tr>
                <td>${edu.name?if_exists}</td>
                <td>${edu.title?if_exists}</td>
                <td>${edu.description?if_exists}</td>
                <td>${edu.startDate?string("yyyy-MM-dd")}</td>
                <td>${edu.endDate?string("yyyy-MM-dd")}</td>
            </tr>
            </#list>
        <#else >
        <tr>
            <td colspan="5">暂无</td>
        </tr>
        </#if>
    </tbody>
</table>
</@common_common.common_common>


