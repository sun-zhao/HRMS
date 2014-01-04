<#import "/view/template/structure/setting/settingCommon.ftl" as setting_common>
<#import "/view/common/core.ftl" as c>
<@setting_common.setting_common>
<script type="text/javascript" charset="utf-8" src="/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/ueditor/editor_api.js"> </script>
<script type="text/javascript" charset="utf-8" src="/view/hr/contract/template/contract.js"> </script>
<script type="text/javascript">
    var submited = false;
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UE.getEditor('editor').hasContents());
        alert(arr.join("\n"));
    }

    $(document).ready(function () {
        UE.getEditor('editor',{
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            toolbars:[['fullscreen', 'source', '|', 'undo', 'redo', '|',
                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                'directionalityltr', 'directionalityrtl', 'indent', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                'link', 'unlink', 'anchor', '|', 'pagebreak', 'template', '|',
                'horizontal', 'date', 'time', 'spechars', '|',
                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                'print', 'preview', 'searchreplace', 'help', 'drafts','|','contract']],
                //启用自动保存
                enableAutoSave: true,
                //自动保存间隔时间， 单位ms
                saveInterval: 5000,
                focus:true,
                autoHeightEnabled:false
            //更多其他参数，请参考ueditor.config.js中的配置项
        });

        $('#btnSave').off('click').on('click', function () {
            if(!UE.getEditor('editor').hasContents()){
                WEBUTILS.msg.alertFail('您还没有设置合同模版,无法进行保存!');
                return false;
            }
            $('#hrContractTemplate\\.template').val(UE.getEditor('editor').getContent());
            WEBUTILS.popMask.show();
            document.editForm.submit();
            submited=true;
        });
    });
</script>
<div class="txt-prompt ">您可以在此创建合同模版，并在合同模版内容里插入合同关键字来完成设置。<a title="不再提示" href="#" class="md-delect"></a></div>
    <@c.joddForm bean="hrContractTemplate" scope="request">
    <form action="/hr/contractTemplate!save.dhtml" method="POST" class="formStyle" name="editForm" id="editForm"
          onsubmit="return false;">
        <div class="mart20">
            <script id="editor" type="text/plain" style="width:745px;height:400px;">
                <#if hrContractTemplate?exists>
                ${hrContractTemplate.template?if_exists}
                </#if>
            </script>
        </div>
        <input type="hidden" name="hrContractTemplate.id" id="hrContractTemplate.id">
        <input type="hidden" name="hrContractTemplate.template" id="hrContractTemplate.template">
    </form>
    </@c.joddForm>
<p class="alignright mart5"><a class="button" href="##" id="btnSave">保存模版</a></p>

</@setting_common.setting_common>