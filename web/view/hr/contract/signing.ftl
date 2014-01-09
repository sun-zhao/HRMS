<#import "/view/template/structure/contract/contractCommon.ftl" as contract_common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@contract_common.contract_common>
<script type="text/javascript">
    var submited = false;
    function printContract(id){
        $('body').append('<a href="" id="printContractA" target="_blank"/>');
        $('#printContractA').attr('href','/hr/contract!print.dhtml?id='+id);
        $('#printContractA').trigger('click');
    }
    function pagerAction(start, rows) {
        var searchUrl = '/hr/contract!signing.dhtml';
        searchUrl += '?start=' + start + '&rows=' + rows;
        <#if word?exists>
            searchUrl += '&word=${word?if_exists}';
        </#if>
        var searchKey = $('#userName').val();
        if(searchKey){
            searchKey=searchKey.trim();
            searchUrl+='&userName='+searchKey;
        }
        searchUrl = encodeURI(searchUrl);
        document.location.href = searchUrl
    }
    $(document).ready(function () {
        <#if word?exists>
            $('li','.word-ser').first().removeClass('current');
            $('li[word="${word?if_exists}"]','.word-ser').removeClass('noname').addClass('current');
        </#if>
        $('#userName').off('keyup').on('keyup', function (e) {
            e = (e) ? e : ((window.event) ? window.event : "")
            var keyCode = e.keyCode ? e.keyCode : (e.which ? e.which : e.charCode);
            if (keyCode ==13) {
                pagerAction(0,20);
            }
        });
        $('a','.word-ser').off('click').on('click',function(){
            var li=$(this).parent();
            if(li){
                var word=$(li).attr('word');
                if(word){
                    document.location.href = '/hr/contract!signing.dhtml?word='+word;
                }else{
                    document.location.href = '/hr/contract!signing.dhtml';
                }
            }
        });

        $('.news').off('click').on('click',function(){
            var uid=$(this).attr('uid');
            var uname=$(this).attr('uname');
            if(uid&&uname){
                WEBUTILS.popWindow.createPopWindow(620, 500, '与'+uname+'签订合同', '/hr/contract!news.dhtml?empId='+uid,true);
                WEBUTILS.popWindow.offset('30%',false);
            }
        });
    });
</script>
<!--排序搜索begin-->
<div class="clearfix gotop">
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
    <!--搜索框begin-->
    <div class="search-area floatleft">
        <input type="text" id="userName" name="userName" class="search-int" placeholder="请输入员工姓名"
               <#if userName?exists>value="${userName?if_exists}"</#if>>
        <em class="line-y"></em>
        <a href="##" class="search-btn"><i class="app-icon ico-search"></i></a>
    </div>
    <!--搜索框over-->
</div>
<!--排序搜索over-->
<!--左侧字母检索begin-->
<ul class="word-ser floatleft">
    <li class="current"><a href="/hr/contract!signing.dhtml">全部</a></li>
    <#if allPyList?exists&&allPyList?size gt 0>
        <#list allPyList as allPy>
            <#if pyList?exists&&pyList?size gt 0>
                <#if pyList?seq_contains(allPy)>
                    <li  word="${allPy}"><a href="/hr/contract!signing.dhtml?word=${allPy?if_exists}">${allPy?if_exists}</a></li>
                <#else >
                    <li class="noname" word="${allPy}"><a href="##">${allPy?if_exists}</a></li>
                </#if>
            <#else >
                <li class="noname" word="${allPy}"><a href="##">${allPy?if_exists}</a></li>
            </#if>
        </#list>
    </#if>
</ul>
<!--左侧字母检索over-->
<!--员工卡片begin-->
<div class="PerMain floatleft">
    <ul class="PerList contract clearfix">
        <#if employeeList?exists&&employeeList?size gt 0>
            <#if userMapList?exists&&userMapList?size gt 0>
                <#list employeeList as employee>
                    <#assign user=userMapList[employee.userId]?if_exists>
                    <#if user?exists>
                        <li>
                            <div class="CardTop clearfix">
                                <span class="PerImg floatleft"><img src="${user.avstar?if_exists}"></span>
                                <dl class="floatleft">
                                    <dd><span>${employee.userName?if_exists}</span></dd>
                                    <dd>${employee.deptName?if_exists}</dd>
                                    <dd>${(employee.jobId.name)?if_exists}</dd>
                                </dl>
                            </div>
                            <div class="CardBt clearfix">
                                <div class="CarTime">
                                    <p class="clearfix">入职日期：${employee.entryDate?string("yyyy-MM-dd")}</p>
                                    <p class="alignright mart5"><a href="##" uid="${employee.id?c}" uname="${employee.userName?if_exists}" class="news"><em class="icon icon-edit"></em>签订合同</a></p>
                                </div>
                            </div>
                        </li>
                    </#if>
                </#list>
            </#if>
        </#if>
    </ul>
<!--分页begin-->
    <@pager.pagerCommon object=employeePage?if_exists max=16/>
<!--分页over-->
</div>
<!--员工卡片over-->

</@contract_common.contract_common>