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
            modes:[
                {
                    id:'hrEmployee\\.entryDate',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入日期'}
                    ]
                },
                {
                    id:'hrEmployee\\.birthDay',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入日期'}
                    ]
                },
                {
                    id:'hrEmployee\\.idCard',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入身份证号'}
                    ]
                },
                {
                    id:'hrEmployee\\.mobileTel',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入移动电话'}
                    ]
                },
                {
                    id:'hrEmployee\\.officeTel',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入办公电话'}
                    ]
                },
                {
                    id:'hrEmployee\\.userEmail',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入电子邮件'}
                    ]
                }
            ]
        }, true);
    }

    function showCity(){
        $.ajax({
            type:'POST',
            url:'/common/common!cityList.dhtml',
            data:{provinceId:$('#hrEmployee\\.provinceId\\.id').val()},
            dataType:'json',
            success:function (jsonData) {
                if (jsonData) {
                    if (jsonData['result'] == '0') {
                        var cityList=jsonData['cityList'];
                        if(cityList&&$(cityList).size()>0){
                            $('#hrEmployee\\.cityId\\.id').empty();
                            $(cityList).each(function(i,o){
                                $('#hrEmployee\\.cityId\\.id').append('<option value="'+o["id"]+'">'+o["name"]+'</option>')
                            });
                        }
                    }
                }
            },
            error:function (jsonData) {

            }
        });
    }
    $(document).ready(function () {
        initValidator();
        $('#hrEmployee\\.provinceId\\.id').off('change').on('change',function(){
           showCity();
        });
        $('#hrEmployee\\.birthDay').off('focus').on('focus', function () {
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
        $('#hrEmployee\\.entryDate').off('focus').on('focus', function () {
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

        $('#nextSave').off('click').on('click', function () {
            if (!submited) {
                WEBUTILS.validator.checkAll();
                window.setTimeout(function () {
                    var passed = WEBUTILS.validator.isPassed();
                    if (passed) {
                        WEBUTILS.popMask.show();
                        document.editForm.submit();
                        submited=true;
                    } else {
                        WEBUTILS.validator.showErrors();
                    }
                }, 500);
            }
        });
    });
</script>
<!--左侧类目begin-->
<div class="PopLeft floatleft">
    <ul>
        <li><a class="current" href="#">个人信息</a></li>
        <li><a href="#">家庭信息</a></li>
        <li><a href="#">工作经历</a></li>
        <li><a href="#">教育经历</a></li>
    </ul>
</div>
<!--左侧类目over-->
<!--右侧详细信息begin-->
    <@c.joddForm bean="hrEmployee" scope="request">
    <form action="/hr/employee!improveSaveInfo.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
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
                        <td width="100">
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
            <table width="100%" class="UserTbale nomar Info mart5">
                <tbody>
                <tr>
                    <th width="60">用工类型：</th>
                    <td width="130">
                        <select class="edit" id="hrEmployee.empType"  name="hrEmployee.empType" disabled="disabled">
                            <option value="1">劳务</option>
                            <option value="0">实习</option>
                        </select>
                    </td>
                    <th width="60">工作状态：</th>
                    <td>
                        <select class="edit" id="hrEmployee.workState"  name="hrEmployee.workState" disabled="disabled">
                            <option value="0">在职</option>
                            <option value="1">离职</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>入职日期：</th>
                    <td colspan="3">
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployee.entryDate" name="hrEmployee.entryDate" readonly="readonly">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            <table width="100%" class="UserTbale nomar Info mart5">
                <tbody><tr>
                    <th width="60">移动电话：</th>
                    <td width="130">
                        <div class="has-general">
                            <input type="text" class="edit" id="hrEmployee.mobileTel" name="hrEmployee.mobileTel">
                            <label class="control-label"></label>
                        </div>
                    </td>
                    <th width="60">办公电话：</th>
                    <td>
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployee.officeTel" name="hrEmployee.officeTel">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>电子邮件：</th>
                    <td colspan="3">
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployee.userEmail" name="hrEmployee.userEmail">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>工作地点：</th>
                    <td colspan="3">
                        <select class="edit" id="hrEmployee.officeAddress.id"  name="hrEmployee.officeAddress.id" >
                            <#if officeAddrList?exists&&officeAddrList?size gt 0>
                                <#list officeAddrList as addr>
                                    <option value="${addr.id?c}">${addr.address}</option>
                                </#list>
                            </#if>
                        </select>
                    </td>
                </tr>
                </tbody>
            </table>
            <table width="100%" class="UserTbale nomar Info mart5">
                <tbody>
                <tr>
                    <th width="60">性别：</th>
                    <td width="100">
                        <select class="edit" id="hrEmployee.userSex"  name="hrEmployee.userSex">
                            <option value="1">高富帅</option>
                            <option value="2">软妹纸</option>
                        </select>
                    </td>
                    <th width="50">学历：</th>
                    <td width="100">
                        <select class="edit" id="hrEmployee.eduLevel.id"  name="hrEmployee.eduLevel.id">
                            <#if eduLevelList?exists&&eduLevelList?size gt 0>
                            <#list eduLevelList as edu>
                                <option value="${edu.id?c}">${edu.codeName?if_exists}</option>
                            </#list>
                            </#if>
                        </select>
                    </td>
                    <th width="50">民族：</th>
                    <td>
                        <select class="edit" id="hrEmployee.nationalityId.id"  name="hrEmployee.nationalityId.id">
                            <#if nationalityList?exists&&nationalityList?size gt 0>
                                <#list nationalityList as nationality>
                                    <option value="${nationality.id?c}">${nationality.codeName?if_exists}</option>
                                </#list>
                            </#if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>国籍：</th>
                    <td>
                        <select class="edit" id="hrEmployee.contryId.id"  name="hrEmployee.contryId.id">
                            <#if contryList?exists&&contryList?size gt 0>
                                <#list contryList as contry>
                                    <option value="${contry.id?c}">${contry.codeName?if_exists}</option>
                                </#list>
                            </#if>
                        </select>
                    </td>
                    <th>省份：</th>
                    <td>
                        <select class="edit" id="hrEmployee.provinceId.id"  name="hrEmployee.provinceId.id">
                            <#if provinceList?exists&&provinceList?size gt 0>
                                <#list provinceList as province>
                                    <option value="${province.id?c}">${province.name?if_exists}</option>
                                </#list>
                            </#if>
                        </select>
                    </td>
                    <th>城市：</th>
                    <td>
                        <select class="edit" id="hrEmployee.cityId.id"  name="hrEmployee.cityId.id">
                            <#if cityList?exists&&cityList?size gt 0>
                                <#list cityList as city>
                                    <option value="${city.id?c}">${city.name?if_exists}</option>
                                </#list>
                            </#if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>政治面貌：</th>
                    <td colspan="2">
                        <select class="edit" id="hrEmployee.politicsLevel.id"  name="hrEmployee.politicsLevel.id">
                            <#if politicsLevelList?exists&&politicsLevelList?size gt 0>
                                <#list politicsLevelList as politicsLevel>
                                    <option value="${politicsLevel.id?c}">${politicsLevel.codeName?if_exists}</option>
                                </#list>
                            </#if>
                        </select>
                    </td>
                    <th>身份证号：</th>
                    <td colspan="2">
                        <div class="has-general">
                            <input type="text" class="edit" id="hrEmployee.idCard"  name="hrEmployee.idCard">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>出生日期：</th>
                    <td width="70" colspan="2">
                        <div class="has-general">
                            <input type="text" class="edit" id="hrEmployee.birthDay"  name="hrEmployee.birthDay">
                            <label class="control-label"></label>
                        </div>
                    </td>
                    <th width="70">银行卡号：</th>
                    <td colspan="2">
                        <div class="has-general">
                            <input type="text" class="edit" id="hrEmployee.bankCard"  name="hrEmployee.bankCard">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <input type="hidden" name="hrEmployee.id" id="hrEmployee.id">
    </form>
    </@c.joddForm>
<p class="alignright mart5"><a class="button" href="##" id="nextSave">下一步</a></p>
<!--右侧详细信息over-->
</@common.html>


