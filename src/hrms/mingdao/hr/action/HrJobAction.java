package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import com.mingdao.api.entity.Department;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrDutyLevel;
import hrms.mingdao.hr.entity.HrJob;
import hrms.mingdao.hr.service.HrDutyLevelService;
import hrms.mingdao.hr.service.HrEmployeeService;
import hrms.mingdao.hr.service.HrJobService;
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
@Action(name = "job", namespace = "/hr")
public class HrJobAction extends ActionSupport<HrJob> {

    @Inject
    private HrJobService hrJobService;

    @Inject
    private HrEmployeeService hrEmployeeService;


    @ReqGet
    @ModelDriver
    @ReqSet
    private HrJob hrJob;

    @ReqSet
    @ReqGet
    private String name;

    @ReqSet
    @ReqGet
    private Long id;

    @ReqSet
    private List<HrJob> jobList;



    private List<Selector> searchModeCallback() throws Exception {
        List<Selector> selectorList = new ArrayList<Selector>();
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("AppSet");
            userInfo.setMenuCss("job");
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
            @Result(name = "success", path = "/view/hr/job/list.ftl", type = Dispatcher.FreeMarker)})
    public String execute() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            pageObj = this.hrJobService.getPageList(getStart(), 14, searchModeCallback());
            if (pageObj != null) {
                jobList = pageObj.getResultList();
                if(jobList!=null&&!jobList.isEmpty()){
                    for(HrJob job:jobList){
                        Integer count=this.hrEmployeeService.getCountByCompanyJob(userInfo.getCompanyId(),job.getId());
                        if(count==null){
                            count=0;
                        }
                        job.setCount(count);
                    }
                }
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/job.dhtml", type = Dispatcher.Redirect)})
    public String save() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && hrJob != null) {
            if (hrJob.getId() != null) {
                HrJob old = this.hrJobService.getById(hrJob.getId());
                old.setName(hrJob.getName());
                bind(old);
                hrJobService.save(old);
            } else {
                hrJob.setCompanyId(userInfo.getCompanyId());
                hrJob.setUseYn("Y");
                bind(hrJob);
                hrJobService.save(hrJob);
            }
        }
        return "success";
    }

    public String getJob() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrJob = this.hrJobService.getById(id);
            if (hrJob != null) {
                root.put("id", hrJob.getId());
                root.put("name", hrJob.getName());
                root.put("result", "0");
            }
        }
        writeJsonByAction(root.toString());
        return null;
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/job.dhtml", type = Dispatcher.Redirect)})
    public String delete() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrJob = this.hrJobService.getById(id);
            if (hrJob != null) {
                this.hrJobService.delete(hrJob);
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
                            Integer row = this.hrJobService.validateName( userInfo.getCompanyId(),name);
                            if (row.intValue() == 0) {
                                item.put("result", "0");
                            }
                        }
                    } else {
                        Integer row = this.hrJobService.validateName( userInfo.getCompanyId(),name);
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
