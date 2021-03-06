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
        <li><a class="current" href="##">个人信息</a></li>
        <li><a href="/hr/employee!viewFamily.dhtml?id=${hrEmployee.id?c}">家庭信息</a></li>
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
                                <span style="color: #ff0000">未设置</span>
                            </#if>
                        </td>
                        <th >职位：</th>
                        <td>
                            <#if hrEmployee.jobId?exists>
                                ${(hrEmployee.jobId.name)?if_exists}
                            <#else>
                                <span style="color: #ff0000">未设置</span>
                            </#if>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <table width="100%" class="UserTbale nomar Info mart5">
                <tbody>
                <tr>
                    <th style="width: 60px;">用工类型：</th>
                    <td style="width: 130px;">
                        <#if hrEmployee.empType?exists>
                            <#if hrEmployee.empType==1>
                                劳务
                            <#elseif hrEmployee.empType==0>
                                实习
                            <#else >
                                <span style="color: #ff0000">未设置</span>
                            </#if>
                        <#else >
                            <span style="color: #ff0000">未设置</span>
                        </#if>
                    </td>
                    <th style="width: 60px;">工作状态：</th>
                    <td>
                        <#if hrEmployee.workState?exists>
                            <#if hrEmployee.workState==1>
                                离职
                            <#elseif hrEmployee.workState==0>
                                在职
                            <#else >
                                <span style="color: #ff0000">未设置</span>
                            </#if>
                        <#else >
                            <span style="color: #ff0000">未设置</span>
                        </#if>
                    </td>
                </tr>
                <tr>
                    <th>入职日期：</th>
                    <td colspan="3">
                        <#if hrEmployee.entryDate?exists>
                        ${hrEmployee.entryDate?string("yyyy-MM-dd")}
                        </#if>
                    </td>
                </tr>
                </tbody>
            </table>
            <table width="100%" class="UserTbale nomar Info mart5">
                <tbody><tr>
                    <th style="width: 60px;">移动电话：</th>
                    <td style="width: 130px;">
                        ${hrEmployee.mobileTel?if_exists}
                    </td>
                    <th width="60">办公电话：</th>
                    <td>
                        ${hrEmployee.officeTel?if_exists}
                    </td>
                </tr>
                <tr>
                    <th>电子邮件：</th>
                    <td colspan="3">
                        ${hrEmployee.userEmail?if_exists}
                    </td>
                </tr>
                <tr>
                    <th>工作地点：</th>
                    <td colspan="3">
                        ${(hrEmployee.officeAddress.address)?if_exists}
                    </td>
                </tr>
                </tbody>
            </table>
            <table width="100%" class="UserTbale nomar Info mart5">
                <tbody>
                <tr>
                    <th style="width: 60px;">性别：</th>
                    <td style="width: 100px;">
                        <#if hrEmployee.userSex?exists>
                            <#if hrEmployee.userSex==1>
                                男
                            <#elseif hrEmployee.userSex==2>
                                女
                            <#else >
                                <span style="color: #ff0000">未设置</span>
                            </#if>
                        <#else >
                            <span style="color: #ff0000">未设置</span>
                        </#if>
                    </td>
                    <th style="width: 60px;">学历：</th>
                    <td style="width: 100px;">
                        ${(hrEmployee.eduLevel.codeName)?if_exists}
                    </td>
                    <th style="width: 60px;">民族：</th>
                    <td>
                        ${(hrEmployee.nationalityId.codeName)?if_exists}
                    </td>
                </tr>
                <tr>
                    <th>国籍：</th>
                    <td>
                        ${(hrEmployee.contryId.codeName)?if_exists}
                    </td>
                    <th>省份：</th>
                    <td>
                    ${(hrEmployee.provinceId.name)?if_exists}
                    </td>
                    <th>城市：</th>
                    <td>
                    ${(hrEmployee.cityId.name)?if_exists}
                    </td>
                </tr>
                <tr>
                    <th>政治面貌：</th>
                    <td colspan="2">
                        ${hrEmployee.politicsLevel.codeName?if_exists}
                    </td>
                    <th>身份证号：</th>
                    <td colspan="2">
                        ${hrEmployee.idCard?if_exists}
                    </td>
                </tr>
                <tr>
                    <th>出生日期：</th>
                    <td  colspan="2">
                        <#if hrEmployee.birthDay?exists>
                            ${hrEmployee.birthDay?string("yyyy-MM-dd")}
                        </#if>
                    </td>
                    <th >银行卡号：</th>
                    <td colspan="2">
                        ${hrEmployee.bankCard?if_exists}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <input type="hidden" name="hrEmployee.id" id="hrEmployee.id">
<!--右侧详细信息over-->
<p class="alignright mart5"><a class="button" href="/hr/employee!editInfo.dhtml?id=${hrEmployee.id?c}" >修改信息</a></p>
</@common.html>


