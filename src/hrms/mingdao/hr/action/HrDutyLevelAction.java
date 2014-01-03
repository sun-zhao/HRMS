package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrContractType;
import hrms.mingdao.hr.entity.HrDutyLevel;
import hrms.mingdao.hr.entity.HrEmployee;
import hrms.mingdao.hr.service.HrContractTypeService;
import hrms.mingdao.hr.service.HrDutyLevelService;
import hrms.mingdao.hr.service.HrEmployeeService;
import hrms.mingdao.web.support.ActionSupport;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.DateFormatUtil;
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
@Action(name = "dutyLevel", namespace = "/hr")
public class HrDutyLevelAction extends ActionSupport<HrDutyLevel> {

    @Inject
    private HrDutyLevelService hrDutyLevelService;

    @Inject
    private HrEmployeeService hrEmployeeService;

    @ReqGet
    @ModelDriver
    @ReqSet
    private HrDutyLevel hrDutyLevel;

    @ReqSet
    @ReqGet
    private String name;

    @ReqSet
    @ReqGet
    private Long id;

    @ReqSet
    private List<HrDutyLevel> dutyLevelList;

    @ReqSet
    private Integer maxOrder;

    private List<Selector> searchModeCallback() throws Exception {
        List<Selector> selectorList = new ArrayList<Selector>();
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("AppSet");
            userInfo.setMenuCss("dutyLevel");
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
            @Result(name = "success", path = "/view/hr/dutyLevel/list.ftl", type = Dispatcher.FreeMarker)})
    public String execute() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            pageObj = this.hrDutyLevelService.getPageList(getStart(), 14, searchModeCallback());
            if (pageObj != null) {
                dutyLevelList = pageObj.getResultList();
                if(dutyLevelList!=null&&!dutyLevelList.isEmpty()){
                    for(HrDutyLevel dutyLevel:dutyLevelList){
                        Integer count=this.hrEmployeeService.getCountByCompanyDutyLevel(userInfo.getCompanyId(),dutyLevel.getId());
                        if(count==null){
                            count=0;
                        }
                        dutyLevel.setCount(count);
                    }
                }
                maxOrder=this.hrDutyLevelService.getMaxDisplayOrder(userInfo.getCompanyId());
                if (maxOrder == null) {
                    maxOrder = 0;
                }
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/dutyLevel.dhtml", type = Dispatcher.Redirect)})
    public String save() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && hrDutyLevel != null) {
            if (hrDutyLevel.getId() != null) {
                HrDutyLevel old = this.hrDutyLevelService.getById(hrDutyLevel.getId());
                old.setName(hrDutyLevel.getName());
                bind(old);
                hrDutyLevelService.save(old);
            } else {
                 maxOrder = this.hrDutyLevelService.getMaxDisplayOrder(userInfo.getCompanyId());
                if (maxOrder == null) {
                    maxOrder = 0;
                }
                hrDutyLevel.setDisplayOrder(maxOrder + 1);
                hrDutyLevel.setCompanyId(userInfo.getCompanyId());
                hrDutyLevel.setUseYn("Y");
                bind(hrDutyLevel);
                hrDutyLevelService.save(hrDutyLevel);
            }
        }
        return "success";
    }

    public String getDutyLevel() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrDutyLevel = this.hrDutyLevelService.getById(id);
            if (hrDutyLevel != null) {
                root.put("id", hrDutyLevel.getId());
                root.put("name", hrDutyLevel.getName());
                root.put("result", "0");
            }
        }
        writeJsonByAction(root.toString());
        return null;
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/dutyLevel.dhtml", type = Dispatcher.Redirect)})
    public String up() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        if (id != null) {
            hrDutyLevel = this.hrDutyLevelService.getById(id);
            if (hrDutyLevel != null) {
                this.hrDutyLevelService.up(hrDutyLevel);
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/dutyLevel.dhtml", type = Dispatcher.Redirect)})
    public String down() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        if (id != null) {
            hrDutyLevel = this.hrDutyLevelService.getById(id);
            if (hrDutyLevel != null) {
                this.hrDutyLevelService.down(hrDutyLevel);
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/dutyLevel.dhtml", type = Dispatcher.Redirect)})
    public String delete() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrDutyLevel = this.hrDutyLevelService.getById(id);
            if (hrDutyLevel != null) {
                this.hrDutyLevelService.delete(hrDutyLevel);
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
                        Integer row = this.hrDutyLevelService.validateName( userInfo.getCompanyId(),name);
                        if (row.intValue() == 0) {
                            item.put("result", "0");
                        }
                    }
                } else {
                    Integer row = this.hrDutyLevelService.validateName( userInfo.getCompanyId(),name);
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
