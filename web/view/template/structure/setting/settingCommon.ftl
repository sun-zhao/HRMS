<#import "/view/template/mdCommon.ftl" as md_common>
<#import "/view/common/core.ftl" as c>
<#macro setting_common>
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
            $('a','.md-p-nav2').poshytip({
                className: 'tip-darkgray',
                bgImageFrameSize: 9
            });
        });
    </script>
    <div class="md-plus">
        <!--知识门户begin-->
        <div class="md-plus-top clearfix">
            <div class="floatleft md-plus-l"><span class="md-ico floatleft md-ico-logo"></span>快捷审批</div>
        </div>
        <!--知识门户over-->
        <div class="md-plus-inner mart30">
            <div class="md-p-nav">
                <ul class="clearfix">
                    <li class="apply"><a class="m-first-a" href="/wf/applyMy.dhtml"><@c.textI18n value="req"/></a></li>
                    <li class="myApply"><a href="/wf/req!ingList.dhtml"><@c.textI18n value="mine"/></a></li>
                    <li class="task"><a href="/wf/reqTask!approveList.dhtml"><@c.textI18n value="approvals"/></a></li>
                    <li class="execute"><a href="/wf/reqExecute!workList.dhtml"><@c.textI18n value="execution"/></a></li>
                    <#if userInfo?exists>
                        <#if userInfo.admin?exists&&userInfo.admin>
                            <li class="admin last-li"><a class="m-last-a"
                                                 href="/wf/apply.dhtml"><@c.textI18n value="environment"/></a>
                            </li>
                        </#if>
                    </#if>
                    <li class="setting last-li"><a href="/wf/remind.dhtml"><@c.textI18n value="personal"/></a></li>
                </ul>
            </div>
            <div class="md-plus-main">
                <!--二级导航 begin-->
                <div class="md-p-nav2">
                    <ul class="clearfix font16">
                        <li class="current remind"><a href="/wf/remind.dhtml"><@c.textI18n value="personalReminders"/></a></li>
                    </ul>
                </div>
                <#nested />
            </div>
        </div>
    </div>
    </@md_common.md_common>
</#macro>