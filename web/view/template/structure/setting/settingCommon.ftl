<#import "/view/template/mdCommon.ftl" as md_common>
<#import "/view/common/core.ftl" as c>
<#macro setting_common>
    <@md_common.md_common title="档案合同" module="HR">
    <script type="text/javascript">
        $(document).ready(function () {
            <#if Session["userSession"]?exists>
                <#assign userInfo=Session["userSession"]?if_exists>
                <#if userInfo?exists>
                    $('li', '.AppNav').removeClass('current');
                    $('.${userInfo['topMenuCss']?if_exists}', '.AppNav').addClass('current');

                    $('li', '.AppNavT').removeClass('current');
                    $('.${userInfo['menuCss']?if_exists}', '.AppNavT').addClass('current');
                </#if>
            </#if>
        });
    </script>
    <div class="AppLayout border-solid">
        <!--app title begin-->
        <div class="AppTopBar border-solid border-bottom clearfix">
            <div class="TopBarl floatleft">
                <span class="AppLogo"><img src="../images/ico-8.png"></span>
                <em class="font18">人力资源</em>
            </div>
        </div>
        <!--app title over-->
        <!--一级导航begin-->
        <div class="AppNav border-solid border-bottom clearfix">
            <ul class="Nav-ul font14 clearfix">
                <li class="employee"><a href="/hr/employee.dhtml">人事档案</a></li>
                <li class="contract"><a href="/hr/contract!signing.dhtml">合同管理</a></li>
                <li><a href="#">报表统计</a></li>
                <li class="myEmployee"><a href="#">我的档案</a></li>
                <li class="AppSet"><a href="/hr/contractType.dhtml"><em class="icon icon-set"></em>应用设置</a></li>
            </ul>
        </div>
        <!--一级导航over-->
        <!--二级导航begin-->
        <div class="AppNavT border-solid border-bottom clearfix">
            <ul class="font16 clearfix floatleft">
                <li class="type"><a href="/hr/contractType.dhtml">合同设置</a></li>
                <li class="signing"><a href="/hr/contract!signing.dhtml">职级设置</a></li>
                <li class="dm"><a href="#">岗位设置</a></li>
            </ul>
        </div>
        <!--二级导航over-->
        <div class="AppMain clearfix">
            <#nested />
        </div>
    </div>
    </@md_common.md_common>
</#macro>