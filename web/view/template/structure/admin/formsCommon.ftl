<#import "/view/template/common.ftl" as common>
<#import "/view/common/core.ftl" as c>
<#macro forms_common>
    <@common.html >
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
                </#if>
            </#if>
            var currentMaxHeight = $('.md-plus').height();
            if (currentMaxHeight < parent.getIframeHeight()) {
                $('.md-plus').css('height', parent.getIframeHeight() + 'px');
            }
            parent.resizeIframeHeight(currentMaxHeight);

            var lv1MenuText = $('.current', '.md-p-nav').find('a').text();
            var lv2MenuText = $('.current', '.md-p-nav2').find('a').text();
            if (lv1MenuText && lv2MenuText) {
                parent.setTitle(lv1MenuText + '-' + lv2MenuText);
            }
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
                    <li class="task"><a href="/wf/reqTask!readList.dhtml"><@c.textI18n value="approvals"/></a></li>
                    <li class="setting"><a href="/wf/remind.dhtml"><@c.textI18n value="personal"/></a></li>
                    <#if userInfo?exists>
                        <#if userInfo.admin?exists&&userInfo.admin>
                            <li class="admin"><a class="m-last-a"
                                                 href="/wf/variableGlobal.dhtml"><@c.textI18n value="environment"/></a>
                            </li>
                        </#if>
                    </#if>
                </ul>
            </div>
            <div class="md-plus-main">
                <!--二级导航 begin-->
                <div class="md-p-nav2">
                    <ul class="clearfix font18">
                        <li class="current step1"><a
                                href="/wf/apply!inputStep1.dhtml?id=${id?c}">基本属性</a></li>
                        <li class="step2"><a
                                href="/wf/apply!inputStep2.dhtml?id=${id?c}">元素属性</a></li>
                        <li class="step3"><a href="/wf/apply!inputStep3.dhtml?id=${id?c}">表单布局</a></li>
                        <li class="apply"><a href="/wf/apply.dhtml"><@c.textI18n value="environmentTemplate"/></a></li>
                    </ul>
                </div>
                <#nested />
            </div>
        </div>
    </div>

    </@common.html>
</#macro>