<#import "/view/template/common.ftl" as common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@common.html module="HR">
<script type="text/javascript" src="/js/webutils/webutils.validator.js"></script>
<script type="text/javascript" src="/js/webutils/reg.js"></script>
<script type="text/javascript">
    var submited = false;
    function initValidator() {
        WEBUTILS.validator.init({
            modes:[
                {
                    id:'hrEmployeeFamily\\.familyName',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入家人姓名'}
                    ]
                },
                {
                    id:'hrEmployeeFamily\\.familyTel',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入家人电话'}
                    ]
                },
                {
                    id:'hrEmployeeFamily\\.familyAddress',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入家庭住址'}
                    ]
                },
                {
                    id:'hrEmployeeFamily\\.residenceBooklet',
                    required:true,
                    pattern:[
                        {type:'blank', exp:'!=', msg:'请输入户口所在地'}
                    ]
                }
            ]
        }, true);
    }
    $(document).ready(function () {
        initValidator();
        $('#btnSave').off('click').on('click', function () {
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
        <li class="noname"><a href="##">个人信息</a></li>
        <li><a class="current" href="##">家庭信息</a></li>
        <li class="noname"><a href="##">工作经历</a></li>
        <li class="noname"><a href="##">教育经历</a></li>
    </ul>
</div>
<!--左侧类目over-->
<!--右侧详细信息begin-->
    <@c.joddForm bean="hrEmployeeFamily" scope="request">
    <form action="/hr/employee!saveFamily.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
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
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployeeFamily.familyName" name="hrEmployeeFamily.familyName">
                            <label class="control-label"></label>
                        </div>
                    </td>
                    <th width="60">家人关系：</th>
                    <td>
                        <select class="edit" id="hrEmployeeFamily.familyRelation"  name="hrEmployeeFamily.familyRelation" >
                            <option value="0">配偶</option>
                            <option value="1">父亲</option>
                            <option value="2">母亲</option>
                            <option value="3">爷爷</option>
                            <option value="4">奶奶</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>家人电话：</th>
                    <td colspan="3">
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployeeFamily.familyTel" name="hrEmployeeFamily.familyTel">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>家庭住址：</th>
                    <td colspan="3">
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployeeFamily.familyAddress" name="hrEmployeeFamily.familyAddress">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th width="75">婚姻状况：</th>
                    <td width="130">
                        <select class="edit" id="hrEmployeeFamily.marry"  name="hrEmployeeFamily.marry" >
                            <option value="0">未婚</option>
                            <option value="1">已婚</option>
                        </select>
                    </td>
                    <th width="60">户口性质：</th>
                    <td>
                        <select class="edit" id="hrEmployeeFamily.residenceBookletType"  name="hrEmployeeFamily.residenceBookletType" >
                            <option value="0">城镇户口</option>
                            <option value="1">农村户口</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>户口所在地：</th>
                    <td colspan="3">
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployeeFamily.residenceBooklet" name="hrEmployeeFamily.residenceBooklet">
                            <label class="control-label"></label>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <input type="hidden" name="id" id="id" value="${hrEmployee.id?c}">
    </form>
    </@c.joddForm>
<p class="alignright mart5"><a class="button" href="##" id="btnSave">保存信息</a></p>
<!--右侧详细信息over-->
</@common.html>


