<#import "/view/template/structure/common.ftl" as common_common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@common_common.common_common>
<script type="text/javascript">
    var submited = false;
    $(document).ready(function () {
    });
</script>
<div class="UserInfo-top clearfix">
    <span class="PerImg floatleft"><img src="${user.avstar100?if_exists}"></span>
    <table class="UserTbale mine floatleft">
        <tbody>
        <tr>
            <th colspan="6">
                <em class="icon icon-user"></em><span class="PerName font16">${user.name?if_exists}</span>
                <#--<a class="a-link marl30" href="#">修改我的档案 <em class="icon icon-edit"></em></a>-->
            </th>
        </tr>
        <tr>
            <th width="60">部门：</th>
            <td width="120">${user.department?if_exists}</td>
            <th>职级：</th>
            <td width="80">高层</td>
            <th width="40">职位：</th>
            <td>${user.job?if_exists}</td>
        </tr>
        <tr>
            <th>入职日期：</th>
            <td colspan="5">2013-10-28</td>
        </tr>
        <tr>
            <th>工作地点：</th>
            <td colspan="5">${user.work_site?if_exists}</td>
        </tr>
        </tbody>
    </table>
</div>
<table width="100%" class="UserTbale nomar Info mine mart10">
    <tbody>
    <tr>
        <th width="60">手机：</th>
        <td width="110">${user.mobilePhone?if_exists} <em class="icon icon-phone"></em></td>
        <th width="70">办公电话：</th>
        <td>${user.work_phone?if_exists} <em class="icon icon-phone2"></em></td>
        <th>电子邮件：</th>
        <td colspan="3"><a class="a-link" href="#">${user.email?if_exists}</a> <em class="icon icon-mail"></em></td>
    </tr>
    </tbody>
</table>
<table width="100%" class="UserTbale nomar Info mine mart5">
    <tbody>
    <tr>
        <th width="60">性别：</th>
        <td width="100">男</td>
        <th width="50">学历：</th>
        <td>研究生</td>
        <th width="30">民族：</th>
        <td>汉</td>
        <th>政治面貌：</th>
        <td width="250">贫农</td>
    </tr>
    <tr>
        <th>国籍：</th>
        <td>中国</td>
        <th>省份：</th>
        <td>河北省</td>
        <th>城市：</th>
        <td>石家庄</td>
        <th>出生日期：</th>
        <td width="70">1988-10-02</td>
    </tr>
    <tr>
        <th>身份证号：</th>
        <td colspan="3">1301231976543542</td>
        <th width="70">银行卡号：</th>
        <td colspan="3">1301231976543542 <em class="icon icon-card"></em></td>
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
    <#assign jobList=user.jobList?if_exists>
    <#if jobList?exists&&jobList?size gt 0>
    <#list jobList as job>
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
    <#assign educationList=user.educationList?if_exists>
    <#if educationList?exists&&educationList?size gt 0>
        <#list educationList as edu>
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