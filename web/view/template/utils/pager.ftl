<#macro pagerCommon actionJs="pagerAction"  start=0 max=5 object=pageObj>
    <#if object?exists&&object.totalPage gt 1>
    <script type="text/javascript">
        $(document).ready(function () {
            $('a', '.ch-pagination').each(function () {
                $(this).off('click').on('click', function () {
                    var p = $(this).text();
                    p = parseInt(p);
                    var rows =${object.everyPage?c};
                    rows = parseInt(rows);
                    var start = p * rows - rows;
                    eval(${actionJs}(start, rows));
                });
            });
            $('a[type="next"]', '.ch-pagination').off('click').on('click', function () {
                var p = ${object.currentPage?c};
                p = parseInt(p);
                p += 1;
                var rows =${object.everyPage?c};
                rows = parseInt(rows);
                var start = p * rows - rows;
                eval(${actionJs}(start, rows));
            });
            $('a[type="prev"]', '.ch-pagination').off('click').on('click', function () {
                var p = ${object.currentPage?c};
                p = parseInt(p);
                p -= 1;
                var rows =${object.everyPage?c};
                rows = parseInt(rows);
                var start = p * rows - rows;
                eval(${actionJs}(start, rows));
            });
        });
    </script>
    <ul class="ch-pagination">
        <#assign max=2>
        <#assign tp=object.totalPage>
        <#assign cp=object.currentPage>
        <#assign preFlag=true>
        <#assign nextFlag=true>
        <#list 1..tp as p>
            <#if object.pageBeginIndex gt 0>
                <#if p gt (object.pageBeginIndex*object.everyPage)-3&& p <=((object.pageBeginIndex+1)*object.everyPage)>
                    <#if preFlag>
                        <li><a href="###" type="prev">上一页</a></li>
                        <#assign preFlag=false>
                    </#if>
                    <#if p==object.currentPage>
                        <li class="ch-pagination-current"><a href="###">${p?c}</a></li>
                    <#else >
                        <li><a href="###">${p?c}</a></li>
                    </#if>
                    <#if nextFlag&&p==((object.pageBeginIndex+1)*object.everyPage)>
                        <#if object.totalPage gt ((object.pageBeginIndex+1)*object.everyPage)>
                            <li><a href="###" type="next">下一页</a></li>
                            <#assign nextFlag=false>
                        </#if>
                    </#if>
                </#if>
            <#else>
                <#if p <= max>
                    <#if p==object.currentPage>
                        <li class="ch-pagination-current"><a href="###">${p?c}</a></li>
                    <#else >
                        <li><a href="###">${p?c}</a></li>
                    </#if>
                    <#if nextFlag&&p==max>
                        <#if object.totalPage gt max>
                            <li><a href="###" type="next">下一页</a></li>
                            <#assign nextFlag=false>
                        </#if>
                    </#if>
                </#if>
            </#if>
        </#list>
    </ul>
    </#if>
</#macro>
