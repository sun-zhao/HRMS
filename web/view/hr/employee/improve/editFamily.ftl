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
        <li class="noname"><a href="#">个人信息</a></li>
        <li ><a class="current" href="#">家庭信息</a></li>
        <li class="noname"><a href="#">工作经历</a></li>
        <li class="noname"><a href="#">教育经历</a></li>
    </ul>
</div>
<!--左侧类目over-->
<!--右侧详细信息begin-->
    <@c.joddForm bean="hrEmployeeFamily" scope="request">
    <form action="/hr/employee!improveSaveFamily.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
          onsubmit="return false;">
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
                    <th style="width: 75px;">家人姓名：</th>
                    <td style="width: 130px;">
                        <div class="has-general">
                            <input type="text" class="edit"  id="hrEmployeeFamily.familyName" name="hrEmployeeFamily.familyName">
                            <label class="control-label"></label>
                        </div>
                    </td>
                    <th style="width: 75px;">家人关系：</th>
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
                    <th>婚姻状况：</th>
                    <td>
                        <select class="edit" id="hrEmployeeFamily.marry"  name="hrEmployeeFamily.marry" >
                            <option value="0">未婚</option>
                            <option value="1">已婚</option>
                        </select>
                    </td>
                    <th>户口性质：</th>
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
    </form>
    </@c.joddForm>
<p class="alignright mart5">
    <a class="button" href="/hr/employee!improveEdit.dhtml" id="preSave">上一步</a>&nbsp;
    <a class="button" href="##" id="nextSave">下一步</a>
</p>
<!--右侧详细信息over-->
</@common.html>


