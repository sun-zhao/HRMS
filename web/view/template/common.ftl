<#macro html title="" >
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Content-Language" content="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <title>${title?html}</title>
    <link rel="shortcut icon" href="/view/favicon.ico"/>
    <link rel="stylesheet" href="/css/global.css" type="text/css">
    <link rel="stylesheet" href="/css/styles.css" type="text/css">
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
