/**
 * 插入代码插件
 * @file
 * @since 1.2.6.1
 */

UE.plugins['contract'] = function() {
    var me = this;
    me.setOpt('contract',{
            '<span style="color:#FF0000;">[员工姓名]</span>':'员工姓名',
            '<span style="color:#FF0000;">[员工性别]</span>':'员工性别',
            '<span style="color:#FF0000;">[身份证号]</span>':'身份证号',
            '<span style="color:#FF0000;">[家庭地址]</span>':'家庭地址',
            '<span style="color:#FF0000;">[联系方式]</span>':'联系方式',
            '<span style="color:#FF0000;">[岗位]</span>':'岗位',
            '<span style="color:#FF0000;">[合同编号]</span>':'合同编号',
            '<span style="color:#FF0000;">[合同类型]</span>':'合同类型',
            '<span style="color:#FF0000;">[合同开始日期]</span>':'合同开始日期',
            '<span style="color:#FF0000;">[合同截止日期]</span>':'合同截止日期',
            '<span style="color:#FF0000;">[合同有效期]</span>':'合同有效期',
            '<span style="color:#FF0000;">[合同期限]</span>':'合同期限',
            '<span style="color:#FF0000;">[合同签订日]</span>':'合同签订日',
            '<span style="color:#FF0000;">[试用期]</span>':'试用期',
            '<span style="color:#FF0000;">[试用期开始日期]</span>':'试用期开始日期',
            '<span style="color:#FF0000;">[试用期结束日期]</span>':'试用期结束日期'
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
