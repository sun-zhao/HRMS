<#import "/view/template/structure/common.ftl" as common_common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@common_common.common_common>
<script type="text/javascript">
    var submited = false;

    $(document).ready(function () {
        WEBUTILS.msg.alertInfo("请在即将弹出的信息框里完善您的个人信息...",5000,function(){
            parent.WEBUTILS.popWindow.createPopWindow(620, 530, '${hrEmployee.userName?if_exists}的人事档案', '/hr/employee!improveEdit.dhtml',true);
            parent.WEBUTILS.popWindow.offset('30%',false);
        });
    });
</script>
<div class="UserInfo-top clearfix">
    <span class="PerImg floatleft"><img src="${user.avstar100?if_exists}"></span>
    <table class="UserTbale mine floatleft">
        <tbody>
        <tr>
            <th colspan="6">
                <em class="icon icon-user"></em><span class="PerName font16">${hrEmployee.userName?if_exists}</span>
                <#--<a class="a-link marl30" href="#">修改我的档案 <em class="icon icon-edit"></em></a>-->
            </th>
        </tr>
        <tr>
            <th width="80">部门：</th>
            <td width="120">${hrEmployee.deptName?if_exists}</td>
            <th width="60">职级：</th>
            <td width="120">&nbsp;</td>
            <th width="60">职位：</th>
            <td>${hrEmployee.jobName?if_exists}</td>
        </tr>
        <tr>
            <th>入职日期：</th>
            <td colspan="5">2013-10-28</td>
        </tr>
        <tr>
            <th>工作地点：</th>
            <td colspan="5">${hrEmployee.officeAddress?if_exists}</td>
        </tr>
        </tbody>
    </table>
</div>
<table width="100%" class="UserTbale nomar Info mine mart10">
    <tbody>
    <tr>
        <th width="50">手机：</th>
        <td width="133">${hrEmployee.mobileTel?if_exists} <em class="icon icon-phone"></em></td>
        <th width="70">办公电话：</th>
        <td>${hrEmployee.officeTel?if_exists} <em class="icon icon-phone2"></em></td>
        <th>电子邮件：</th>
        <td colspan="3"><a class="a-link" href="#">${hrEmployee.userEmail?if_exists}</a> <em class="icon icon-mail"></em></td>
    </tr>
    </tbody>
</table>
<table width="100%" class="UserTbale nomar Info mine mart5">
    <tbody>
    <tr>
        <th width="50">性别：</th>
        <td width="80">
            <#if hrEmployee.userSex==1>
                男
            <#elseif hrEmployee.userSex==2>
                女
            </#if>
        </td>
        <th width="50">学历：</th>
        <td width="80">&nbsp;</td>
        <th width="50">民族：</th>
        <td width="80">&nbsp;</td>
        <th width="50">政治面貌：</th>
        <td >&nbsp;</td>
    </tr>
    <tr>
        <th>国籍：</th>
        <td>&nbsp;</td>
        <th>省份：</th>
        <td>&nbsp;</td>
        <th>城市：</th>
        <td>&nbsp;</td>
        <th>出生日期：</th>
        <td width="70">
            <#if hrEmployee.birthDay?exists>
            ${hrEmployee.birthDay?string("yyyy-MM-dd")}
            </#if>
        </td>
    </tr>
    <tr>
        <th>身份证号：</th>
        <td colspan="3">&nbsp;</td>
        <th width="70">银行卡号：</th>
        <td colspan="3">&nbsp; <em class="icon icon-card"></em></td>
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
            <td>${edu.title?if_exists}<</td>
            <td>${edu.description?string("yyyy-MM-dd")}</td>
            <td>${edu.startDate?string("yyyy-MM-dd")}</td>
            <td>${edu.endDate?if_exists}</td>
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


