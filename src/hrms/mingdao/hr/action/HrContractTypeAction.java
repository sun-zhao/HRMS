package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import com.mingdao.api.entity.User;
import com.mingdao.api.user.RequestUser;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrContractType;
import hrms.mingdao.hr.entity.HrEmployee;
import hrms.mingdao.hr.entity.HrEmployeeJob;
import hrms.mingdao.hr.service.HrContractTypeService;
import hrms.mingdao.hr.service.HrEmployeeJobService;
import hrms.mingdao.hr.service.HrEmployeeService;
import hrms.mingdao.web.support.ActionSupport;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.DateFormatUtil;
import org.guiceside.web.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhenjiaWang
 * Date: 12-7-12
 * Time: 下午9:49
 * To change this template use File | Settings | File Templates.
 */
@Action(name = "contractType", namespace = "/hr")
public class HrContractTypeAction extends ActionSupport<HrContractType> {

    @Inject
    private HrContractTypeService hrContractTypeService;


    @ReqGet
    @ModelDriver
    @ReqSet
    private HrContractType hrContractType;


    @ReqSet
    @ReqGet
    private Long id;





    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/contract/type/index.ftl", type = Dispatcher.FreeMarker)})
    public String execute() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null ) {
            userInfo.setTopMenuCss("AppSet");
            userInfo.setMenuCss("contractType");
            hrContractType = this.hrContractTypeService.getListByCompanyId(userInfo.getCompanyId());
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/contractType.dhtml", type = Dispatcher.Redirect)})
    public String save() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null&&hrContractType!=null) {
            if(hrContractType.getId()!=null){
                HrContractType old=this.hrContractTypeService.getById(hrContractType.getId());
                old.setStaticProbation(hrContractType.getStaticProbation());
                old.setStaticRemindDay(hrContractType.getStaticRemindDay());
                old.setStaticValidMonth(hrContractType.getStaticValidMonth());
                old.setProbation(hrContractType.getProbation());
                old.setRemindDay(hrContractType.getRemindDay());
                old.setValidMonth(hrContractType.getValidMonth());
                bind(old);
                this.hrContractTypeService.save(old);
            }else{
                hrContractType.setCompanyId(userInfo.getCompanyId());
                hrContractType.setUseYn("Y");
                bind(hrContractType);
                this.hrContractTypeService.save(hrContractType);
            }
        }
        return "success";
    }
}
