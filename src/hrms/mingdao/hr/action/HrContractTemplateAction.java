package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrContractTemplate;
import hrms.mingdao.hr.entity.HrContractType;
import hrms.mingdao.hr.service.HrContractTemplateService;
import hrms.mingdao.hr.service.HrContractTypeService;
import hrms.mingdao.web.support.ActionSupport;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.entity.search.SelectorUtils;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.web.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @ReqSet
    @ReqGet
    private String name;


    @ReqSet
    private List<HrContractTemplate> templateList;



    private List<Selector> searchModeCallback() throws Exception {
        List<Selector> selectorList = new ArrayList<Selector>();
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("AppSet");
            userInfo.setMenuCss("template");
            String companyId = userInfo.getCompanyId();
            if (StringUtils.isNotBlank(companyId)) {
                selectorList.add(SelectorUtils.$eq("companyId", companyId));
                if (StringUtils.isNotBlank(name)) {
                    selectorList.add(SelectorUtils.$like("name", name));
                }
                selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                selectorList.add(SelectorUtils.$order("name"));
            } else {
                selectorList.add(SelectorUtils.$eq("id", 0l));
            }

        }
        return selectorList;
    }
    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/contract/template/list.ftl", type = Dispatcher.FreeMarker)})
    public String execute() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            pageObj = this.hrContractTemplateService.getPageList(getStart(), 14, searchModeCallback());
            if (pageObj != null) {
                templateList = pageObj.getResultList();
            }
        }
        return "success";
    }


    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/contract/template/index.ftl", type = Dispatcher.FreeMarker)})
    public String index() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null&&id!=null) {
            userInfo.setTopMenuCss("AppSet");
            userInfo.setMenuCss("template");
            hrContractTemplate=this.hrContractTemplateService.getById(id);
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/contract/template/print.ftl", type = Dispatcher.FreeMarker)})
    public String print() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null&&id!=null) {
            userInfo.setTopMenuCss("AppSet");
            userInfo.setMenuCss("template");
            hrContractTemplate=this.hrContractTemplateService.getById(id);
        }
        return "success";
    }


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
        return "saveSuccess";
    }

    public String getTemplate() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrContractTemplate = this.hrContractTemplateService.getById(id);
            if (hrContractTemplate != null) {
                root.put("id", hrContractTemplate.getId());
                root.put("name", hrContractTemplate.getName());
                root.put("result", "0");
            }
        }
        writeJsonByAction(root.toString());
        return null;
    }
    @PageFlow(result = {
            @Result(name = "success", path = "/hr/contractTemplate.dhtml", type = Dispatcher.Redirect)})
    public String saveName() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && hrContractTemplate != null) {
            if (hrContractTemplate.getId() != null) {
                HrContractTemplate old = this.hrContractTemplateService.getById(hrContractTemplate.getId());
                old.setName(hrContractTemplate.getName());
                bind(old);
                hrContractTemplateService.save(old);
            } else {
                hrContractTemplate.setCompanyId(userInfo.getCompanyId());
                hrContractTemplate.setUseYn("Y");
                bind(hrContractTemplate);
                hrContractTemplateService.save(hrContractTemplate);
            }
        }
        return "success";
    }


    public String validateName() throws Exception {
        JSONObject item = new JSONObject();
        item.put("result", "-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            if (StringUtils.isNotBlank(name)) {
                String ignore = getParameter("ignore");
                if (StringUtils.isNotBlank(ignore)) {
                    if (ignore.equals(name)) {
                        item.put("result", "0");
                        writeJsonByAction(item.toString());
                    } else {
                        Integer row = this.hrContractTemplateService.validateName( userInfo.getCompanyId(),name);
                        if (row.intValue() == 0) {
                            item.put("result", "0");
                        }
                    }
                } else {
                    Integer row = this.hrContractTemplateService.validateName( userInfo.getCompanyId(),name);
                    if (row.intValue() == 0) {
                        item.put("result", "0");
                    }
                }
            }
        }
        writeJsonByAction(item.toString());
        return null;
    }
}
