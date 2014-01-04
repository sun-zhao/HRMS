package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrContractTemplate;
import hrms.mingdao.hr.entity.HrContractType;
import hrms.mingdao.hr.service.HrContractTemplateService;
import hrms.mingdao.hr.service.HrContractTypeService;
import hrms.mingdao.web.support.ActionSupport;
import org.guiceside.web.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhenjiaWang
 * Date: 12-7-12
 * Time: 下午9:49
 * To change this template use File | Settings | File Templates.
 */
@Action(name = "contractTemplate", namespace = "/hr")
public class HrContractTemplateAction extends ActionSupport<HrContractTemplate> {

    @Inject
    private HrContractTemplateService hrContractTemplateService;


    @ReqGet
    @ModelDriver
    @ReqSet
    private HrContractTemplate hrContractTemplate;


    @ReqSet
    @ReqGet
    private Long id;


    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/contract/template/index.ftl", type = Dispatcher.FreeMarker)})
    public String execute() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("AppSet");
            userInfo.setMenuCss("template");
            hrContractTemplate=this.hrContractTemplateService.getByCompanyId(userInfo.getCompanyId());
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/contractTemplate.dhtml", type = Dispatcher.Redirect)})
    public String save() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && hrContractTemplate != null) {
            if (hrContractTemplate.getId() != null) {
                HrContractTemplate old = this.hrContractTemplateService.getById(hrContractTemplate.getId());
                hrContractTemplate = this.copy(hrContractTemplate, old);
            }
            hrContractTemplate.setCompanyId(userInfo.getCompanyId());
            hrContractTemplate.setUseYn("Y");
            bind(hrContractTemplate);
            this.hrContractTemplateService.save(hrContractTemplate);
        }
        return "success";
    }
}
