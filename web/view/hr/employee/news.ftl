<#import "/view/template/structure/employee/employeeCommon.ftl" as employee_common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@employee_common.employee_common>
<!--左侧字母检索begin-->
<ul class="word-ser floatleft">
    <li class="current"><a href="##">全部</a></li>
    <li><a href="##">A</a></li>
    <li><a href="##">B</a></li>
    <li><a href="##">C</a></li>
    <li><a href="##">D</a></li>
    <li><a href="##">E</a></li>
    <li><a href="##">F</a></li>
    <li><a href="##">G</a></li>
    <li><a href="##">H</a></li>
    <li><a href="##">I</a></li>
    <li><a href="##">J</a></li>
    <li><a href="##">K</a></li>
    <li><a href="##">L</a></li>
    <li><a href="##">M</a></li>
    <li><a href="##">N</a></li>
    <li><a href="##">O</a></li>
    <li><a href="##">P</a></li>
    <li><a href="##">Q</a></li>
    <li><a href="##">R</a></li>
    <li><a href="##">S</a></li>
    <li><a href="##">T</a></li>
    <li><a href="##">U</a></li>
    <li><a href="##">V</a></li>
    <li><a href="##">W</a></li>
    <li><a href="##">X</a></li>
    <li><a href="##">Y</a></li>
    <li><a href="##">Z</a></li>
</ul>
<!--左侧字母检索over-->
<!--员工卡片begin-->
<div class="PerMain floatleft">
<ul class="PerList clearfix">
<#if employeeList?exists&&employeeList?size gt 0>
<#if userMapList?exists&&userMapList?size gt 0>
    <#list employeeList as employee>
    <#assign user=userMapList[employee.userId]?if_exists>
    <#if user?exists>
    <li>
        <div class="CardTop clearfix">
            <span class="PerImg floatleft"><img src="${user.avstar?if_exists}"></span>
            <dl class="floatleft">
                <dd><span>${user.name?if_exists}</span></dd>
                <dd>${user.department?if_exists}</dd>
                <dd>${user.job?if_exists}</dd>
            </dl>
        </div>
        <p class="alignright">
            <a class="font14 invite block" href="#">邀请TA完善信息 <em class="icon icon-edit"></em></a>
        </p>
    </li>
    </#if>
    </#list>
</#if>
</#if>
</ul>
<!--分页begin-->
    <@pager.pagerCommon object=pageObj?if_exists max=24/>
<!--分页over-->
</div>
<!--员工卡片over-->

</@employee_common.employee_common>