<#import "/view/template/mdCommon.ftl" as md_common>
<#import "/view/common/core.ftl" as c>
<#macro employee_common>
    <@md_common.md_common title="档案合同">
    <link rel="stylesheet" href="/css/per-style.css" type="text/css">
    <script type="text/javascript">
        $(document).ready(function () {
            <#if Session["userSession"]?exists>
                <#assign userInfo=Session["userSession"]?if_exists>
                <#if userInfo?exists>

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
                <li class="current"><a href="#">人事档案</a></li>
                <li><a href="#">合同管理</a></li>
                <li><a href="#">报表统计</a></li>
                <li><a href="#">我的档案</a></li>
                <li class="AppSet"><a href="#"><em class="icon icon-set"></em>应用设置</a></li>
            </ul>
        </div>
        <!--一级导航over-->
        <!--二级导航begin-->
        <div class="AppNavT border-solid border-bottom clearfix">
            <ul class="font16 clearfix floatleft">
                <li class="current"><a href="#">员工档案</a></li>
                <li><a href="/hr/employee!news.dhtml">新增员工</a></li>
                <li><a href="#">离职员工</a></li>
            </ul>
            <!--排序搜索begin-->
            <div class="floatright clearfix">
                <!--排序begin-->
                <div class="AppSequence floatleft marr10"><!--加current下拉框显示-->
                    <a href="#">按时间排序</a>
                    <em class="app-icon ico-toogle"></em>

                    <div class="down-list">
                        <ul>
                            <li>按时间排序</li>
                            <li>按类别排序</li>
                            <li>按紧急排序</li>
                        </ul>
                    </div>
                </div>
                <!--排序over-->
                <!--搜索框begin-->
                <div class="search-area floatleft">
                    <input type="text" class="search-int">
                    <em class="line-y"></em>
                    <a class="search-btn" href="#"><i class="app-icon ico-search"></i></a>
                </div>
                <!--搜索框over-->
            </div>
            <!--排序搜索over-->
        </div>
        <!--二级导航over-->
        <div class="AppMain clearfix">
            <#nested />
        </div>
    </div>
    </@md_common.md_common>
</#macro>