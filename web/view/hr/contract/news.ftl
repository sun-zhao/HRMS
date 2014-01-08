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
            modes: [
                {
                    id: 'hrContract\\.contractNo',
                    required: true,
                    pattern: [
                        {type: 'blank', exp: '!=', msg: '请输入合同编号'}
                    ]
                },
                {
                    id: 'hrContract\\.endDate',
                    required: true,
                    pattern: [
                        {type: 'blank', exp: '!=', msg: '请输入截止日期'}
                    ]
                },
                {
                    id: 'hrContract\\.workArea',
                    required: true,
                    pattern: [
                        {type: 'blank', exp: '!=', msg: '请输入工作地点'}
                    ]
                },
                {
                    id: 'hrContract\\.probationPay',
                    required: true,
                    pattern: [
                        {type: 'number', exp: '==', msg: '请输入试用期薪资'}
                    ]
                },
                {
                    id: 'hrContract\\.pay',
                    required: true,
                    pattern: [
                        {type: 'number', exp: '==', msg: '请输入转正后薪资'}
                    ]
                },
                {
                    id: 'hrContract\\.insuranceArea',
                    required: true,
                    pattern: [
                        {type: 'blank', exp: '!=', msg: '请输入保险缴纳地'}
                    ]
                }
            ]
        }, true);
    }
    function setEndDate() {
        var contractType = $('#hrContract\\.contractType').val();
        if (contractType) {
            if (contractType == 0) {
                $('#hrContract\\.endDate').val('${dueDate?string("yyyy-MM-dd")}');
            } else if (contractType == 1) {
                $('#hrContract\\.endDate').val('${staticDueDate?string("yyyy-MM-dd")}');
            }
        }
    }
    $(document).ready(function () {
        initValidator();
        $('#btnSave').off('click').on('click', function () {
            if (!submited) {
                WEBUTILS.validator.checkAll();
                window.setTimeout(function () {
                    var passed = WEBUTILS.validator.isPassed();
                    if (passed) {
                        WEBUTILS.msg.alertInfo("合同签订完成后可到人事档案-员工档案进行合同打印签订",1500,function(){
                            WEBUTILS.popMask.show();
                            document.editForm.submit();
                            submited = true;
                        })
                    } else {
                        WEBUTILS.validator.showErrors();
                    }
                }, 500);
            }
        });
        $('#hrContract\\.contractType').off('change').on('change', function () {
            setEndDate();
        });
    });
</script>
<!--左侧类目begin-->
<div class="PopLeft floatleft">
    <ul>
        <li class="noname"><a href="##">个人信息</a></li>
        <li class="noname"><a href="##">家庭信息</a></li>
        <li class="noname"><a href="##">工作经历</a></li>
        <li class="noname"><a href="##">教育经历</a></li>
        <li><a class="current" href="##">合同信息</a></li>
    </ul>
</div>
<!--左侧类目over-->
<!--右侧详细信息begin-->
    <@c.joddForm bean="hrContract" scope="request">
    <form action="/hr/contract!saveNews.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
          onsubmit="return false;">
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
                        <div class="has-general">
                            <input type="text" class="edit" id="hrContract.contractNo" name="hrContract.contractNo">
                            <label class="control-label"></label>
                        </div>
                    </td>
                    <th style="width: 75px;">合同类别：</th>
                    <td>
                        <select class="edit" id="hrContract.contractType" name="hrContract.contractType">
                            <option value="1">固定期劳动合同</option>
                            <option value="0">无固定期劳动合同</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th >签订日期：</th>
                    <td >
                    ${hrEmployee.entryDate?string("yyyy-MM-dd")}
                    </td>
                    <th >截止日期：</th>
                    <td>
                        <div class="has-general">
                            <input type="text" class="edit" id="hrContract.endDate" name="hrContract.endDate"
                                   value="${staticDueDate?string("yyyy-MM-dd")}" readonly="readonly">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th >工作地点：</th>
                    <td >
                        <div class="has-general">
                            <input type="text" class="edit" id="hrContract.workArea" name="hrContract.workArea">
                            <label class="control-label"></label>
                        </div>
                    </td>
                    <th >工时制度：</th>
                    <td>
                        <select class="edit" id="hrContract.workTime" name="hrContract.workTime">
                            <option value="每周5天每天8小时">每周5天每天8小时</option>
                            <option value="根据劳动行政部门批准，实行不定时工作制">根据劳动行政部门批准，实行不定时工作制</option>
                            <option value="自行填写"> 实行按N计时的综合计算工作</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th >试用期薪资：</th>
                    <td >
                        <div class="has-general">
                            <input type="text" class="edit" id="hrContract.probationPay" name="hrContract.probationPay">
                            <label class="control-label"></label>
                        </div>
                    </td>
                    <th >转正后薪资：</th>
                    <td>
                        <div class="has-general">
                            <input type="text" class="edit" id="hrContract.pay" name="hrContract.pay">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th >保险缴纳地：</th>
                    <td colspan="3">
                        <div class="has-general">
                            <input type="text" class="edit" id="hrContract.insuranceArea" name="hrContract.insuranceArea">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                <tr>

                    <th >保险种类：</th>
                    <td colspan="3">
                        养老 <input type="checkbox" name="insuranceType" value="养老">
                        医疗 <input type="checkbox" name="insuranceType" value="医疗">
                        工伤 <input type="checkbox" name="insuranceType" value="工伤">
                        失业 <input type="checkbox" name="insuranceType" value="失业">
                        生育 <input type="checkbox" name="insuranceType" value="生育">
                    </td>
                </tr>
                <tr>

                    <th >合同模版：</th>
                    <td colspan="3">
                        <select class="edit" id="hrContract.templateId.id" name="hrContract.templateId.id">
                            <#if templateList?exists&&templateList?size gt 0>
                            <#list templateList as template>
                                <option value="${template.id?c}">${template.name?if_exists}</option>
                            </#list>
                            </#if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>备注：</th>
                    <td colspan="3">
                        <div class="has-general">
                            <input type="text" class="edit" id="hrContract.remarks" name="hrContract.remarks">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <input type="hidden" name="empId" id="empId" value="${hrEmployee.id?c}">
    </form>
    </@c.joddForm>
<p class="alignright mart5"><a class="button" href="##" id="btnSave">签订合同</a></p>
<!--右侧详细信息over-->
</@common.html>


