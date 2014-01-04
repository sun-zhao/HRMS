package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrOrg;
import hrms.mingdao.hr.service.HrEmployeeService;
import hrms.mingdao.hr.service.HrOrgService;
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
@Action(name = "org", namespace = "/hr")
public class HrOrgAction extends ActionSupport<HrOrg> {

    @Inject
    private HrOrgService hrOrgService;

    @Inject
    private HrEmployeeService hrEmployeeService;

    @ReqGet
    @ModelDriver
    @ReqSet
    private HrOrg hrOrg;

    @ReqSet
    @ReqGet
    private String name;

    @ReqSet
    @ReqGet
    private Long id;

    @ReqSet
    private List<HrOrg> orgList;

    @ReqSet
    private Integer maxOrder;

    private List<Selector> searchModeCallback() throws Exception {
        List<Selector> selectorList = new ArrayList<Selector>();
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("AppSet");
            userInfo.setMenuCss("org");
            String companyId = userInfo.getCompanyId();
            if (StringUtils.isNotBlank(companyId)) {
                selectorList.add(SelectorUtils.$eq("companyId", companyId));
                if (StringUtils.isNotBlank(name)) {
                    selectorList.add(SelectorUtils.$like("name", name));
                }
                selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                selectorList.add(SelectorUtils.$order("displayOrder"));
            } else {
                selectorList.add(SelectorUtils.$eq("id", 0l));
            }

        }
        return selectorList;
    }


    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/org/list.ftl", type = Dispatcher.FreeMarker)})
    public String execute() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            pageObj = this.hrOrgService.getPageList(getStart(), 14, searchModeCallback());
            if (pageObj != null) {
                orgList = pageObj.getResultList();
                if(orgList!=null&&!orgList.isEmpty()){
                    for(HrOrg org:orgList){
                        Integer count=this.hrEmployeeService.getCountByCompanyOrg(userInfo.getCompanyId(),org.getId());
                        if(count==null){
                            count=0;
                        }
                        org.setCount(count);
                    }
                }
                maxOrder=this.hrOrgService.getMaxDisplayOrder(userInfo.getCompanyId());
                if (maxOrder == null) {
                    maxOrder = 0;
                }
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/org.dhtml", type = Dispatcher.Redirect)})
    public String save() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && hrOrg != null) {
            if (hrOrg.getId() != null) {
                HrOrg old = this.hrOrgService.getById(hrOrg.getId());
                old.setName(hrOrg.getName());
                bind(old);
                hrOrgService.save(old);
            } else {
                 maxOrder = this.hrOrgService.getMaxDisplayOrder(userInfo.getCompanyId());
                if (maxOrder == null) {
                    maxOrder = 0;
                }
                hrOrg.setDisplayOrder(maxOrder + 1);
                hrOrg.setCompanyId(userInfo.getCompanyId());
                hrOrg.setUseYn("Y");
                bind(hrOrg);
                hrOrgService.save(hrOrg);
            }
        }
        return "success";
    }

    public String getOrg() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrOrg = this.hrOrgService.getById(id);
            if (hrOrg != null) {
                root.put("id", hrOrg.getId());
                root.put("name", hrOrg.getName());
                root.put("result", "0");
            }
        }
        writeJsonByAction(root.toString());
        return null;
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/org.dhtml", type = Dispatcher.Redirect)})
    public String up() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        if (id != null) {
            hrOrg = this.hrOrgService.getById(id);
            if (hrOrg != null) {
                this.hrOrgService.up(hrOrg);
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/org.dhtml", type = Dispatcher.Redirect)})
    public String down() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        if (id != null) {
            hrOrg = this.hrOrgService.getById(id);
            if (hrOrg != null) {
                this.hrOrgService.down(hrOrg);
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/org.dhtml", type = Dispatcher.Redirect)})
    public String delete() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrOrg = this.hrOrgService.getById(id);
            if (hrOrg != null) {
                this.hrOrgService.delete(hrOrg);
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
                        Integer row = this.hrOrgService.validateName( userInfo.getCompanyId(),name);
                        if (row.intValue() == 0) {
                            item.put("result", "0");
                        }
                    }
                } else {
                    Integer row = this.hrOrgService.validateName( userInfo.getCompanyId(),name);
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
