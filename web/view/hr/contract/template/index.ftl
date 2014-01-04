<#import "/view/template/common.ftl" as common_common>
<#import "/view/common/core.ftl" as c>
<@common_common.html module="HR">
<script type="text/javascript" charset="utf-8" src="/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/ueditor/editor_api.js"> </script>
<script type="text/javascript">

    UE.plugins['contract'] = function() {
        var me = this;
        me.setOpt('contract',{
            '<span style="color:#FF0000;" uid="userName">#员工姓名#</span>':'员工姓名',
            '<span style="color:#FF0000;" uid="orgName">#员工公司#</span>':'员工公司',
            '<span style="color:#FF0000;" uid="userSex">#员工性别#</span>':'员工性别',
            '<span style="color:#FF0000;" uid="idCard">#身份证号#</span>':'身份证号',
            '<span style="color:#FF0000;" uid="familyAddress">#家庭地址#</span>':'家庭地址',
            '<span style="color:#FF0000;" uid="mobileTel">#联系方式#</span>':'联系方式',
            '<span style="color:#FF0000;" uid="jobName">#岗位#</span>':'岗位',
            '<span style="color:#FF0000;" uid="contractNo">#合同编号#</span>':'合同编号',
            '<span style="color:#FF0000;" uid="contractType">#合同类型#</span>':'合同类型',
            '<span style="color:#FF0000;" uid="contractStartDate">#合同开始日期#</span>':'合同开始日期',
            '<span style="color:#FF0000;" uid="contractEndDate">#合同截止日期#</span>':'合同截止日期',
            '<span style="color:#FF0000;" uid="contractYear">#合同有效期#</span>':'合同有效期',
            '<span style="color:#FF0000;" uid="probationMonth">#试用期长度#</span>':'试用期长度',
            '<span style="color:#FF0000;" uid="probationStartDate">#试用期开始日期#</span>':'试用期开始日期',
            '<span style="color:#FF0000;" uid="probationEndDate">#试用期结束日期#</span>':'试用期结束日期',
            '<span style="color:#FF0000;" uid="probationPay">#试用期薪资#</span>':'试用期薪资',
            '<span style="color:#FF0000;" uid="pay">#转正后薪资#</span>':'转正后薪资',
            '<span style="color:#FF0000;" uid="workArea">#工作地点#</span>':'工作地点',
            '<span style="color:#FF0000;" uid="workTime">#工时制度#</span>':'工时制度',
            '<span style="color:#FF0000;" uid="insuranceArea">#保险缴纳地#</span>':'保险缴纳地',
            '<span style="color:#FF0000;" uid="insuranceType">#保险种类#</span>':'保险种类'
        });

        /**
         * 插入代码
         * @command insertcode
         * @method execCommand
         * @param { String } cmd 命令字符串
         * @param { String } lang 插入代码的语言
         * @example
         * ```javascript
         * editor.execCommand( 'insertcode', 'javascript' );
         * ```
         */

        /**
         * 如果选区所在位置是插入插入代码区域，返回代码的语言
         * @command insertcode
         * @method queryCommandValue
         * @param { String } cmd 命令字符串
         * @return { String } 返回代码的语言
         * @example
         * ```javascript
         * editor.queryCommandValue( 'insertcode' );
         * ```
         */

        me.commands['contract'] = {
            execCommand : function(cmd,text){
                var me = this;
                me.execCommand('insertHtml', text);
            }
        };
    };
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
        <input type="hidden" name="hrContractTemplate.name" id="hrContractTemplate.name">
        <input type="hidden" name="hrContractTemplate.template" id="hrContractTemplate.template">
    </form>
    </@c.joddForm>
<p class="alignright mart5"><a class="button" href="##" id="btnSave">保存模版</a></p>

</@common_common.html>