<#macro html  module title="">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Content-Language" content="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9"/>
    <title>${title?html}</title>
    <link rel="shortcut icon" href="/view/favicon.ico"/>
    <link rel="stylesheet" href="/css/global.css" type="text/css">
    <link rel="stylesheet" href="/css/styles.css" type="text/css">
    <#if module?exists>
    <#if module=="HR">
        <link rel="stylesheet" href="/css/per-style.css" type="text/css">
    </#if>
    </#if>
    <link rel="stylesheet" href="/css/theme1.css" type="text/css">
    <script src="/js/jquery.js"></script>
    <script src="/js/support.js"></script>
    <script src="/js/webutils/webutils.templete.js"></script>
    <script src="/js/webutils/webutils.msg.js"></script>
    <script src="/js/webutils/webutils.dialog.js"></script>
    <script src="/js/webutils/webutils.searchTip.js"></script>
    <script src="/js/webutils/webutils.layout.js"></script>
    <script src="/js/webutils/webutils.status.js"></script>
    <script src="/js/webutils/webutils.dragdrop.js"></script>
    <script src="/js/webutils/webutils.popWindow.js"></script>
    <script src="/js/webutils/webutils.popMask.js"></script>
    <script type="text/javascript">
        function reload(){
            document.location.reload();
        }
        $(document).ready(function () {
            WEBUTILS.popMask.close();
        });
    </script>
</head>
<body>
    <#setting number_format="#">
    <#nested/>
</body>
</html>
</#macro>
