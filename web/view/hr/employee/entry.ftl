<#import "/view/template/structure/employee/employeeCommon.ftl" as employee_common>
<#import "/view/template/page.ftl" as pager>
<#import "/view/common/core.ftl" as c>
<@employee_common.employee_common>
<script type="text/javascript">
    var submited = false;
    function pagerAction(start, rows) {
        var searchUrl = '/hr/employee!entry.dhtml';
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
       <#if pyList?exists&&pyList?size gt 0>
           <#list pyList as py>
           $('li[word="${py?if_exists}"]','.word-ser').removeClass('noname');
       </#list>
           <#if word?exists>
               $('li','.word-ser').first().removeClass('current');
               $('li[word="${word?if_exists}"]','.word-ser').removeClass('noname').addClass('current');
           </#if>
       </#if>
        $('a','.word-ser').off('click').on('click',function(){
           var li=$(this).parent();
            if(li){
                var word=$(li).attr('word');
                if(word){
                    document.location.href = '/hr/employee!entry.dhtml?word='+word;
                }else{
                    document.location.href = '/hr/employee!entry.dhtml';
                }
            }
        });

        $('#userName').off('keyup').on('keyup', function (e) {
            e = (e) ? e : ((window.event) ? window.event : "")
            var keyCode = e.keyCode ? e.keyCode : (e.which ? e.which : e.charCode);
            if (keyCode ==13) {
                pagerAction(0,20);
            }
        });
        $('.sendImprove').off('click').on('click',function(){
            var li=$(this).parent().parent();
            var uid=$(this).attr('uid');
            var aObj=$(this);
            if(uid&&aObj.hasClass('invite')){
                $.ajax({
                    type:'POST',
                    url:'/hr/employee!sendImprove.dhtml',
                    data:{id:uid},
                    dataType:'json',
                    success:function (jsonData) {
                        if (jsonData) {
                            if (jsonData['result'] == '0') {
                                WEBUTILS.msg.alertSuccess('已向'+jsonData['userName']+'发送完善信息邀请!',1500,function(){
                                    $(aObj).css({'cursor':'default',color:'#999999'});
                                    $(aObj).removeClass('invite');
                                    $(aObj).text('等待TA完善信息');
                                    $(aObj).off('click');
                                });
                            }
                        }
                    },
                    error:function (jsonData) {

                    }
                });
            }
        });
        <#if improveCount?exists&&improveCount gt 0>
            $('#allImprove').off('click').on('click',function(){
                var r=confirm("此操作将邀请该列表下${improveCount?c}名员工完善人事档案,是否继续?");
                if(r==true){
                    WEBUTILS.popMask.show();
                    document.location.href='/hr/employee!sendImproveAll.dhtml';
                }
            });
        </#if>
    });
</script>

<!--排序搜索begin-->
<div class="clearfix gotop">
    <#if improveCount?exists&&improveCount gt 0>
        <div class="floatleft marr10"><!--加current下拉框显示-->
            <a class="button" id="allImprove">一键邀请</a>
        </div>
    </#if>
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
    <li class="current"><a href="##">全部</a></li>
    <li class="noname" word="A"><a href="##">A</a></li>
    <li class="noname" word="B"><a href="##">B</a></li>
    <li class="noname" word="C"><a  href="##">C</a></li>
    <li class="noname" word="D"><a href="##">D</a></li>
    <li class="noname" word="E"><a href="##">E</a></li>
    <li class="noname" word="F"><a href="##">F</a></li>
    <li class="noname" word="G"><a href="##">G</a></li>
    <li class="noname" word="H"><a href="##">H</a></li>
    <li class="noname" word="I"><a href="##">I</a></li>
    <li class="noname" word="J"><a href="##">J</a></li>
    <li class="noname" word="K"><a href="##">K</a></li>
    <li class="noname" word="L"><a href="##">L</a></li>
    <li class="noname" word="M"><a href="##">M</a></li>
    <li class="noname" word="N"><a href="##">N</a></li>
    <li class="noname" word="O"><a href="##">O</a></li>
    <li class="noname" word="P"><a href="##">P</a></li>
    <li class="noname" word="Q"><a href="##">Q</a></li>
    <li class="noname" word="R"><a href="##">R</a></li>
    <li class="noname" word="S"><a href="##">S</a></li>
    <li class="noname" word="T"><a href="##">T</a></li>
    <li class="noname" word="U"><a href="##">U</a></li>
    <li class="noname" word="V"><a href="##">V</a></li>
    <li class="noname" word="W"><a href="##">W</a></li>
    <li class="noname" word="X"><a href="##">X</a></li>
    <li class="noname" word="Y"><a href="##">Y</a></li>
    <li class="noname" word="Z"><a href="##">Z</a></li>
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
                <dd><span>${employee.userName?if_exists}</span></dd>
                <dd>${employee.deptName?if_exists}</dd>
                <dd>${(employee.jobId.name)?if_exists}</dd>
            </dl>
        </div>
        <p class="alignright">
            <#if employee.complete==1>
                <a class="font14 invite block sendImprove" href="##" uid="${employee.id?c}">邀请TA完善信息 <em class="icon icon-edit"></em></a>
            <#elseif employee.complete==2>
                    <a class="font14  block sendImprove" href="##" uid="${employee.id?c}" style="cursor: default;color: #999999;">等待TA完善信息 </a>
            </#if>
        </p>
    </li>
    </#if>
    </#list>
</#if>
</#if>
</ul>
    <!--分页begin-->
    <@pager.pagerCommon object=pageObj?if_exists max=20/>
    <!--分页over-->
</div>
<!--员工卡片over-->
</@employee_common.employee_common>