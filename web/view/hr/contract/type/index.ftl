<#import "/view/template/structure/setting/settingCommon.ftl" as setting_common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@setting_common.setting_common>
<script type="text/javascript">
    var submited = false;
    $(document).ready(function () {
        $('#btnSave').off('click').on('click', function () {
            if (!submited) {
                WEBUTILS.popMask.show();
                document.editForm.submit();
                submited=true;
            }
        });
    });
</script>
    <@c.joddForm bean="hrContractType" scope="request">
    <form action="/hr/contractType!save.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
          onsubmit="return false;">
        <table class="ContractTab">
            <tbody>
            <tr>
                <td class="font14 Themetxt-color" colspan="4">固定期劳动合同</td>
            </tr>
            <tr>
                <th>合同签订期</th>
                <td>
                    <select class="ThemeSelect" id="hrContractType.staticValidMonth"
                            name="hrContractType.staticValidMonth">
                        <option value="12">12个月</option>
                        <option value="24">24个月</option>
                        <option value="36">36个月</option>
                        <option value="48">48个月</option>
                    </select>
                </td>
                <th>预先提醒</th>
                <td>
                    <select class="ThemeSelect" id="hrContractType.staticRemindDay"
                            name="hrContractType.staticRemindDay">
                        <option value="15">15日</option>
                        <option value="30">30日</option>
                        <option value="45">45日</option>
                        <option value="60">60日</option>
                    </select>
                </td>
                <th>入职试用期</th>
                <td>
                    <select class="ThemeSelect" id="hrContractType.staticProbation"
                            name="hrContractType.staticProbation">
                        <option value="1">1个月</option>
                        <option value="2">2个月</option>
                        <option value="3">3个月</option>
                        <option value="4">4个月</option>
                    </select>
                </td>
            </tr>
            </tbody>
        </table>
        <table class="ContractTab mart20">
            <tbody>
            <tr>
                <td class="font14 Themetxt-color" colspan="4">无固定期劳动合同</td>
            </tr>
            <tr>
                <th>合同签订期</th>
                <td>
                    <select class="ThemeSelect" id="hrContractType.validMonth" name="hrContractType.validMonth">
                        <option value="9999">永久</option>
                    </select>
                </td>
                <th>预先提醒</th>
                <td>
                    <select class="ThemeSelect" id="hrContractType.remindDay" name="hrContractType.remindDay">
                        <option value="9999">不提醒</option>
                    </select>
                </td>
                <th>入职试用期</th>
                <td>
                    <select class="ThemeSelect" id="hrContractType.probation" name="hrContractType.probation">
                        <option value="1">1个月</option>
                        <option value="2">2个月</option>
                        <option value="3">3个月</option>
                        <option value="4">4个月</option>
                    </select>
                </td>
            </tr>
            </tbody>
        </table>
        <input type="hidden" name="hrContractType.id" id="hrContractType.id">
    </form>
    </@c.joddForm>
<p class="alignright mart5 marr30"><a class="button" id="btnSave" href="##">保存设置</a></p>
</@setting_common.setting_common>