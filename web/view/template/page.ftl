<#macro pagerCommon actionJs="pagerAction"  start=0 max=10  object=pageObj>
    <#if object?exists&&object.totalPage gt 1>
    <script type="text/javascript">
        $(document).ready(function () {
            $('a', '.pagenation').each(function () {
                if (!$(this).hasClass('no')&&!$(this).hasClass('next')&&!$(this).hasClass('prev')) {
                    $(this).off('click').on('click', function () {
                        var p = $(this).text();
                        p = parseInt(p);
                        var rows =${object.everyPage?c};
                        rows = parseInt(rows);
                        var start = p * rows - rows;
                        eval(${actionJs}(start, rows));
                    });
                }
            });
            $('.next', '.pagenation').off('click').on('click', function () {
                if (!$(this).hasClass('no')) {
                    var last=$(this).prev();
                    var p = $(last).text();
                    p = parseInt(p);
                    var rows =${object.everyPage?c};
                    rows = parseInt(rows);
                    var start = p * rows;
                    eval(${actionJs}(start, rows));
                }
            });
            $('.prev', '.pagenation').off('click').on('click', function () {
                if (!$(this).hasClass('no')) {
                    var next=$(this).next();
                    var p = $(next).text();
                    p = parseInt(p);
                    p=p-4;
                    var rows =${object.everyPage?c};
                    rows = parseInt(rows);
                    var start = p * rows- rows;
                    eval(${actionJs}(start, rows));
                }
            });

            if($('.md-p-no').size()>0){
                $('.next').removeClass('no');
                $('.next').addClass('no');
            }
        });
    </script>
    <!--分页器begin-->

    <div class="pagenation alignright">
        <div class="pageTop">
            <#assign tp=object.totalPage>
            <#assign cp=object.currentPage>
            <#assign tr=object.totalRecord>
            <#assign ep=object.everyPage>
            <#assign preFlag=false>
            <#assign nextFlag=false>
            <#if cp gt 5>
                <#assign preFlag=true>
            </#if>
            <a class="prev <#if !preFlag>no</#if>" href="##">&lt;</a>
            <#assign mo=(cp%5)?int>
            <#assign divided=(cp/5)?int>
            <#if mo gt 0>
                <#assign divided=(divided+1)?int>
            </#if>
            <#assign endCur=(divided*5)?int>
            <#assign starCur=endCur-4>
            <#if tr gt endCur*ep>
                <#assign nextFlag=true>
            </#if>
            <#list starCur..endCur as p>
                <#if p==object.currentPage>
                    <span class="current">${p}</span>
                <#elseif p gt object.totalPage>
                    <a href="##" class="no md-p-no">${p}</a>
                <#else >
                    <a href="##">${p}</a>
                </#if>
            </#list>
            <a class="next <#if !nextFlag>no</#if>" href="##">&gt;</a>
        </div>
        <p class="pageBt"><span>共<em>${object.totalPage}</em>页</span><a href="#">最后一页</a></p>
    </div>
    <!--分页器bover-->
    </#if>
</#macro>

