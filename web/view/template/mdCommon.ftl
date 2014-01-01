<#import "/view/template/common.ftl" as common>
<#import "/view/common/core.ftl" as c>
<#macro md_common title module>
    <@common.html title=title module=module>
    <script type="text/javascript" src="/js/importJS.js"></script>
    <script type="text/javascript">
        var timer;
        var loadSuccess=false;

        function setLaTitle(title){
            var laName=$('title').text();
            $('title').text(laName+' '+title);
        }
        function startLoad(){
            var FilesArray=[
                "https://api.mingdao.com/css/appstyle.css"
            ];
            Import.LoadFileList(FilesArray,function(loadCount){
                if(loadCount==FilesArray.length){
                    loadSuccess=true;
                }
            },false);
        }

        function loadMdDone(){


        }
        function setURL(url) {
            document.location.href=url;
        }

        $(document).ready(function () {
            $('#Container').hide();
            startLoad();
            timer = window.setInterval(function(){
                if(loadSuccess){
                    window.clearInterval(timer);
                    $('#Container').show();
                    $('body').css('background','url(/images/bg.png)');

                    $('.lastItem','#AccountExpandDiv').find('a').attr('href','/common/authorize!logout.dhtml');
                }
            },500);
        });
    </script>
        <#if Session["userSession"]?exists>
            <#assign userInfo=Session["userSession"]?if_exists>
            <#if userInfo?exists>
            <div id="Container" style="display: none;">
                <div class="head" id="header">
                ${userInfo["headerHtml"]?if_exists}
                </div>
                <div class="content">
                    <div class="innercontent clearfix">
                        <div id="leftMenu" class="widget_Left">
                        ${userInfo["leftHtml"]?if_exists}
                        </div>
                        <div class="main ThemeShaBorder" id="mainIframeDiv">
                            <#nested />
                        </div>
                        <div class="Clear"></div>
                    </div>
                    <div class="Clear"></div>
                </div>
                <!--footer begin-->
                <div id="footer">
                ${userInfo["footerHtml"]?if_exists}
                </div>
                <!--footer over-->
            </div>
            </#if>
        </#if>
    </@common.html>
</#macro>