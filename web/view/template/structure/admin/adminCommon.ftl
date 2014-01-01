<#import "/view/template/mdCommon.ftl" as md_common>
<#import "/view/common/core.ftl" as c>
<#macro admin_common>
    <@md_common.md_common title="快捷审批">
    <script type="text/javascript">
        $(document).ready(function () {
            <#if Session["userSession"]?exists>
                <#assign userInfo=Session["userSession"]?if_exists>
                <#if userInfo?exists>
                    $('li', '.md-p-nav').removeClass('current');
                    $('.${userInfo['topMenuCss']?if_exists}', '.md-p-nav').addClass('current');

                    $('li', '.md-p-nav2').removeClass('current');
                    $('.${userInfo['menuCss']?if_exists}', '.md-p-nav2').addClass('current');

                    <#if userInfo.reqPassed gt 0 ||userInfo.reqRejected gt 0>
                        $('.myApply', '.md-p-nav').find('a').append('<em class="md-plus-news"></em>');
                    </#if>
                    <#if userInfo.taskUnRead gt 0 ||userInfo.taskUnApprove gt 0>
                        $('.task', '.md-p-nav').find('a').append('<em class="md-plus-news"></em>');
                    </#if>
                    <#if userInfo.executeUnRead gt 0 ||userInfo.executeUnState gt 0>
                        $('.execute','.md-p-nav').find('a').append('<em class="md-plus-news"></em>');
                    </#if>
                </#if>
            </#if>

            var lv1MenuText=$('.current','.md-p-nav').find('a').text();
            var lv2MenuText=$('.current','.md-p-nav2').find('a').text();
            if(lv1MenuText&&lv2MenuText){
                setLaTitle(lv1MenuText+'-'+lv2MenuText+' ${userInfo.companyName?if_exists}');
            }

            $('.newReq').off('click').on('click',function(){
                <#if userInfo.initApply==-1>
                    WEBUTILS.popWindow.createPopWindow(780, <#if userInfo.applyCount gt 0>${100*userInfo.applyCount+100}<#else>200</#if>, '提交新申请', '/wf/applyMy.dhtml',true);
                <#elseif userInfo.initApply==0>
                    WEBUTILS.popWindow.createPopWindow(780, <#if userInfo.applyCount gt 0>${100*userInfo.applyCount+100}<#else>200</#if>, '提交新申请', '/wf/applyMy!init.dhtml',true);
                </#if>
            });
        });
    </script>
    <div class="md-plus">
        <!--知识门户begin-->
        <div class="md-plus-top clearfix">
            <div class="floatleft md-plus-l"><span class="md-ico floatleft md-ico-logo"></span>快捷审批</div>
        </div>
        <!--知识门户over-->
        <div class="md-plus-inner">
            <div class="md-p-nav clearfix">
                <ul class="floatleft clearfix">
                    <li class="myApply"><a href="/wf/req!ingList.dhtml">我申请的项目</a></li>
                    <li class="task"><a href="/wf/reqTask!approveList.dhtml">我审批的项目</a></li>
                    <li class="execute"><a href="/wf/reqExecute!workList.dhtml">我经办的项目</a></li>
                    <#if userInfo?exists>
                        <#if userInfo.admin?exists&&userInfo.admin>
                            <li class="app-set"><a href="/wf/apply.dhtml"><em class="md-mi-ico2 md-mi-set"></em>应用管理</a></li>
                        </#if>
                    </#if>
                </ul>
                <a class="md-plus-btn floatright color-white marr30 newReq" href="##" >提交新申请<em class="md-plus-btn-r"></em></a>
            </div>
            <div class="md-p-nav2 clearfix">
                <ul class="clearfix floatleft font16">
                    <li class="apply"><a href="/wf/apply.dhtml"><@c.textI18n value="environmentTemplate"/></a></li>
                    <li class="flow"><a href="/wf/applyFlow.dhtml"><@c.textI18n value="environmentWorkflow"/></a></li>
                    <li class="req"><a href="/wf/req!adminList.dhtml"><@c.textI18n value="environmentRequisition"/></a></li>
                    <#--<li class="variableGlobal"><a href="/wf/variableGlobal.dhtml"><@c.textI18n value="environmentPosition"/></a></li>-->
                </ul>
            </div>
            <#nested >
        </div>
    </div>
    </@md_common.md_common>
</#macro>