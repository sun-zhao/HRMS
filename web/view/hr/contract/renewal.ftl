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
        $('#hrContract\\.endDate').off('focus').on('focus', function () {
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
        $('#btnSave').off('click').on('click', function () {
            if (!submited) {
                WEBUTILS.validator.checkAll();
                window.setTimeout(function () {
                    var passed = WEBUTILS.validator.isPassed();
                    if (passed) {
                        WEBUTILS.popMask.show();
                        document.editForm.submit();
                        submited = true;
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
    <form action="/hr/contract!saveRenewal.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
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
                        ${(hrEmployee.jobId.name)?if_exists}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <table width="100%" class="UserTbale nomar Info mart5">
                <tbody>
                <tr>
                    <th width="75">合同编号：</th>
                    <td width="130">
                        <div class="has-general">
                            <input type="text" class="edit" id="hrContract.contractNo" name="hrContract.contractNo">
                            <label class="control-label"></label>
                        </div>
                    </td>
                    <th width="60">合同类别：</th>
                    <td>
                        <select class="edit" id="hrContract.contractType" name="hrContract.contractType">
                            <option value="1">固定期劳动合同</option>
                            <option value="0">无固定期劳动合同</option>
                        </select>
                    </td>
                </tr>
                <tr style="line-height: 17px;">
                    <th width="75">到期日期：</th>
                    <td colspan="3">
                    ${hrContract.endDate?string("yyyy-MM-dd")}
                    </td>
                </tr>
                <tr>
                    <th width="75">签订日期：</th>
                    <td width="130">
                    ${renewalDate?string("yyyy-MM-dd")}
                    </td>
                    <th width="60">截止日期：</th>
                    <td>
                        <div class="has-general">
                            <input type="text" class="edit" id="hrContract.endDate" name="hrContract.endDate"
                                   value="${staticDueDate?string("yyyy-MM-dd")}">
                            <label class="control-label"></label>
                        </div>
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
        <input type="hidden" name="hrContract.id" id="hrContract.id">
    </form>
    </@c.joddForm>
<p class="alignright mart5"><a class="button" href="##" id="btnSave">续签合同</a></p>
<!--右侧详细信息over-->
</@common.html>


