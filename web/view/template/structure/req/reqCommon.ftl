<#import "/view/template/mdCommon.ftl" as md_common>
<#import "/view/common/core.ftl" as c>
<#macro req_common>
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
                        $('.myApply','.md-p-nav').find('a').append('<em class="md-plus-news"></em>');
                        <#if userInfo.reqPassed gt 0>
                            $('.pass','.md-p-nav2').find('a').append('<em class="md-plus-news">${userInfo.reqPassed?c}</em>');
                        </#if>
                        <#if userInfo.reqRejected gt 0>
                            $('.overrule','.md-p-nav2').find('a').append('<em class="md-plus-news">${userInfo.reqRejected?c}</em>');
                        </#if>
                    </#if>

                    <#if userInfo.taskUnRead gt 0 ||userInfo.taskUnApprove gt 0>
                        $('.task','.md-p-nav').find('a').append('<em class="md-plus-news"></em>');
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
                    WEBUTILS.popMask.show('快捷审批正在为您初始化您需要使用的申请单据，请您等待');
                    $.ajax({
                        type:'GET',
                        url:'/wf/applyMy!initApply.dhtml',
                        dataType:'json',
                        success:function (jsonData) {
                            if (jsonData) {
                                if (jsonData['result'] == '0') {
                                    WEBUTILS.popMask.close();
                                    WEBUTILS.msg.alertSuccess("已经成功初始化您需要使用的申请单据,请等待页面自动跳转!",3000,function(){
                                        document.location.reload();
                                    });
                                }else{
                                    WEBUTILS.popMask.close();
                                    WEBUTILS.msg.alertFail("初始化您需要使用的申请单据失败!");
                                }
                            }
                        },
                        error:function (jsonData) {

                        }
                    });
                </#if>
            });
        });
    </script>
    <div class="md-plus">
        <!--知识门户begin-->
        <div class="md-plus-top clearfix">
            <div class="floatleft md-plus-l"><span class="md-ico floatleft md-ico-logo"></span>快捷审批</div>
        </div>
        <div class="md-plus-inner">
            <!--the inner nav begin-->
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
            <!--the inner nav over-->
            <!--二级导航 begin-->
            <div class="md-p-nav2  clearfix">
                <ul class="clearfix font16 floatleft">
                    <li class="current ing"><a href="/wf/req!ingList.dhtml"><@c.textI18n value="mineUnApproved"/></a></li>
                    <li class="pass"><a href="/wf/req!passList.dhtml"><@c.textI18n value="minePassed"/></a></li>
                    <li class="overrule"><a href="/wf/req!overruleList.dhtml"><@c.textI18n value="mineRejected"/></a></li>
                </ul>
            </div>
            <#nested >
        </div>
    </div>
    </@md_common.md_common>
</#macro>