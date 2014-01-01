<#import "/view/template/common.ftl" as common>
<#import "/view/common/core.ftl" as c>
<@common.html title="审批流程 明道" module="">
<script type="text/javascript">
    <#if url?exists>
    $(document).ready(function () {
        document.location.href='${url?if_exists}';
    });
    </#if>
</script>
</@common.html>