package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import com.mingdao.api.entity.Department;
import com.mingdao.api.entity.Education;
import com.mingdao.api.entity.Job;
import com.mingdao.api.entity.User;
import com.mingdao.api.user.RequestUser;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrEmployee;
import hrms.mingdao.hr.entity.HrEmployeeEdu;
import hrms.mingdao.hr.entity.HrEmployeeFamily;
import hrms.mingdao.hr.entity.HrEmployeeJob;
import hrms.mingdao.hr.service.HrEmployeeEduService;
import hrms.mingdao.hr.service.HrEmployeeFamilyService;
import hrms.mingdao.hr.service.HrEmployeeJobService;
import hrms.mingdao.hr.service.HrEmployeeService;
import hrms.mingdao.sys.entity.SysCity;
import hrms.mingdao.sys.entity.SysCode;
import hrms.mingdao.sys.entity.SysProvince;
import hrms.mingdao.sys.service.SysCityService;
import hrms.mingdao.sys.service.SysCodeService;
import hrms.mingdao.sys.service.SysProvinceService;
import hrms.mingdao.util.PinyinUtils;
import hrms.mingdao.web.support.ActionSupport;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.DateFormatUtil;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.entity.search.SelectorUtils;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.web.annotation.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhenjiaWang
 * Date: 12-7-12
 * Time: 下午9:49
 * To change this template use File | Settings | File Templates.
 */
@Action(name = "employeeJob", namespace = "/hr")
public class HrEmployeeJobAction extends ActionSupport<HrEmployeeJob> {

    @Inject
    private HrEmployeeService hrEmployeeService;


    @Inject
    private HrEmployeeJobService hrEmployeeJobService;

    @ReqGet
    @ModelDriver
    @ReqSet
    private HrEmployee hrEmployee;


    @ReqGet
    @ModelDriver
    @ReqSet
    private HrEmployeeJob hrEmployeeJob;

    @ReqSet
    @ReqGet
    private Long id;

    @ReqSet
    @ReqGet
    private Long empId;

    @ReqSet
    private User user;

    @ReqSet
    private List<HrEmployeeJob> employeeJobList;




    public String execute() throws Exception {
        return "success";
    }


    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/improveEditJob.ftl", type = Dispatcher.FreeMarker)})
    public String improveEditJob() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null ) {
            hrEmployee = this.hrEmployeeService.getByUserId(userInfo.getCompanyId(), userInfo.getUserId());
            if (hrEmployee.getComplete().intValue() == 3) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                employeeJobList=this.hrEmployeeJobService.getListByEmpId(userInfo.getCompanyId(),hrEmployee.getId());
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/employeeJob!improveEditJob.dhtml", type = Dispatcher.Redirect)})
    public String improveDelete() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null&&id!=null) {
            hrEmployeeJob=this.hrEmployeeJobService.getById(id);
            if(hrEmployeeJob!=null){
                this.hrEmployeeJobService.delete(hrEmployeeJob);
            }
        }
        return "success";
    }

    public String getJob() throws Exception {
        JSONObject root=new JSONObject();
        root.put("result","-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null&&id!=null) {
            hrEmployeeJob=this.hrEmployeeJobService.getById(id);
            if(hrEmployeeJob!=null){
                root.put("id",hrEmployeeJob.getId());
                root.put("name",hrEmployeeJob.getName());
                root.put("title",hrEmployeeJob.getTitle());
                root.put("startDate",DateFormatUtil.format(hrEmployeeJob.getStartDate(),DateFormatUtil.YEAR_MONTH_DAY_PATTERN));
                root.put("endDate",DateFormatUtil.format(hrEmployeeJob.getEndDate(),DateFormatUtil.YEAR_MONTH_DAY_PATTERN));
                root.put("description",hrEmployeeJob.getDescription());
                root.put("result","0");
            }
        }
        writeJsonByAction(root.toString());
        return null;
    }
    @PageFlow(result = {
            @Result(name = "success", path = "/hr/employeeJob!improveEditJob.dhtml", type = Dispatcher.Redirect)})
    public String improveSaveJob() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null&&hrEmployeeJob!=null) {
            hrEmployee = this.hrEmployeeService.getByUserId(userInfo.getCompanyId(), userInfo.getUserId());
            if (hrEmployee.getComplete().intValue() == 3) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                if(hrEmployeeJob.getId()!=null){
                    HrEmployeeJob old=this.hrEmployeeJobService.getById(hrEmployeeJob.getId());
                    old.setName(hrEmployeeJob.getName());
                    old.setTitle(hrEmployeeJob.getTitle());
                    old.setStartDate(hrEmployeeJob.getStartDate());
                    old.setEndDate(hrEmployeeJob.getEndDate());
                    old.setDescription(hrEmployeeJob.getDescription());
                    bind(old);
                    this.hrEmployeeJobService.save(old);
                }else{
                    hrEmployeeJob.setEmpId(hrEmployee);
                    hrEmployeeJob.setCompanyId(userInfo.getCompanyId());
                    hrEmployeeJob.setUseYn("Y");
                    bind(hrEmployeeJob);
                    this.hrEmployeeJobService.save(hrEmployeeJob);
                }
            }
        }
        return "success";
    }

}
