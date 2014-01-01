<#import "/view/template/mdCommon.ftl" as md_common>
<#import "/view/common/core.ftl" as c>
<#macro sys_common>
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
                </#if>
            </#if>

            var lv1MenuText=$('.current','.md-p-nav').find('a').text();
            var lv2MenuText=$('.current','.md-p-nav2').find('a').text();
            if(lv1MenuText&&lv2MenuText){
                setLaTitle(lv1MenuText+'-'+lv2MenuText+' ${userInfo.companyName?if_exists}');
            }
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
                    <li class="sys"><a href="/sys/apply.dhtml">LA后台管理</a></li>
                </ul>
            </div>
            <div class="md-p-nav2 clearfix">
                <ul class="clearfix floatleft font16">
                    <li class="apply"><a href="/sys/apply.dhtml"><@c.textI18n value="environmentTemplate"/></a></li>
                    <li class="flow"><a href="/wf/req!ingList.dhtml">返回LA</a></li>
                </ul>
            </div>
            <#nested >
        </div>
    </div>
    </@md_common.md_common>
</#macro>