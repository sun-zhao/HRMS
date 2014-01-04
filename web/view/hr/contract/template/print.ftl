<#import "/view/template/common.ftl" as common_common>
<#import "/view/common/core.ftl" as c>
<@common_common.html module="HR" title="${hrContractTemplate.name?if_exists} 打印预览">
<div style="width:760px;margin:0 auto;position: relative;">
   <#if hrContractTemplate?exists &&hrContractTemplate.template?exists>
    ${hrContractTemplate.template?if_exists}
   <#else>
   请先设置模版内容再进行打印预览
   </#if>
</div>
</@common_common.html>