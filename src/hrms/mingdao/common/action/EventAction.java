package hrms.mingdao.common.action;

import com.mingdao.api.utils.SignatureUtil;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.web.action.BaseAction;
import org.guiceside.web.annotation.Action;
import org.guiceside.web.annotation.ReqGet;

/**
 * Created by IntelliJ IDEA.
 * User: zhenjiaWang
 * Date: 12-7-12
 * Time: 下午9:49
 * To change this template use File | Settings | File Templates.
 */
@Action(name = "event", namespace = "/common")
public class EventAction extends BaseAction {



    @ReqGet
    private String signature;

    @ReqGet
    private String timestamp;

    @ReqGet
    private String nonce;

    @ReqGet
    private String content;

    public String execute() throws Exception {
        System.out.println("##############################");
        System.out.println("###########signature###################"+signature);
        System.out.println("###########timestamp###################"+timestamp);
        System.out.println("###########nonce###################"+nonce);
        System.out.println("###########content###################"+content);
        if(StringUtils.isNotBlank(signature)&&StringUtils.isNotBlank(timestamp)
                &&StringUtils.isNotBlank(nonce)&&StringUtils.isNotBlank(content)){
            String laSignature=SignatureUtil.getSignature(timestamp,nonce,content,"289F758D5422B63944A331A5025CC5", "65A48A87C1D9F2B8860A036DCD71B14");
            if(StringUtils.isNotBlank(laSignature)&&laSignature.equals(signature)){
                JSONObject eventJson=JSONObject.fromObject(content);
                System.out.println(content);
                if(eventJson!=null){
                    String event=eventJson.getString("event");
                    System.out.println("###########event###################"+event);
                    if(StringUtils.isNotBlank(event)){
                        if(event.equals('1')){
                            initSysApply(eventJson);
                        }else if(event.equals("2")){

                        }else if(event.equals("3")){

                        }
                    }
                }
            }else{
                System.out.println("签名失败");
            }
        }
        return null;
    }

    private String initSysApply(JSONObject eventJson) throws Exception {

        return "success";
    }
}
