package hrms.mingdao.common.action;

import com.mingdao.api.entity.AppConfig;
import com.mingdao.api.utils.AppConfigUtil;
import com.mingdao.api.utils.SignatureUtil;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.DateFormatUtil;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.web.action.BaseAction;
import org.guiceside.web.annotation.Action;
import org.guiceside.web.annotation.ReqGet;

import java.util.Date;

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
        AppConfig appConfig = AppConfigUtil.create();
        if(appConfig!=null&&StringUtils.isNotBlank(signature)&&StringUtils.isNotBlank(timestamp)
                &&StringUtils.isNotBlank(nonce)&&StringUtils.isNotBlank(content)){
            String hrSignature=SignatureUtil.getSignature(timestamp,nonce,content,appConfig.getAppKey(), appConfig.getAppSecret());
            if(StringUtils.isNotBlank(hrSignature)&&hrSignature.equals(signature)){
                JSONObject eventJson=JSONObject.fromObject(content);
                System.out.println(content);
                if(eventJson!=null){
                    String event=eventJson.getString("event");
                    System.out.println("###########event###################"+event);
                    if(StringUtils.isNotBlank(event)){
                        if(event.equals("1")){
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
        if(eventJson!=null){
            String companyId=eventJson.getString("pid");
            String userId=eventJson.getString("uid");
            if (StringUtils.isNotBlank(companyId)&&StringUtils.isNotBlank(userId)) {

            }
        }
        return "success";
    }
}
