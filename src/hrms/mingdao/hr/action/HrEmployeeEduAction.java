package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import com.mingdao.api.entity.User;
import com.mingdao.api.user.RequestUser;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrEmployee;
import hrms.mingdao.hr.entity.HrEmployeeEdu;
import hrms.mingdao.hr.entity.HrEmployeeJob;
import hrms.mingdao.hr.service.HrEmployeeEduService;
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
@Action(name = "employeeEdu", namespace = "/hr")
public class HrEmployeeEduAction extends ActionSupport<HrEmployeeEdu> {

    @Inject
    private HrEmployeeService hrEmployeeService;


    @Inject
    private HrEmployeeEduService hrEmployeeEduService;

    @ReqGet
    @ModelDriver
    @ReqSet
    private HrEmployee hrEmployee;


    @ReqGet
    @ModelDriver
    @ReqSet
    private HrEmployeeEdu hrEmployeeEdu;

    @ReqSet
    @ReqGet
    private Long id;

    @ReqSet
    @ReqGet
    private Long empId;

    @ReqSet
    private User user;

    @ReqSet
    private List<HrEmployeeEdu> employeeEduList;




    public String execute() throws Exception {
        return "success";
    }


    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/improveEditEdu.ftl", type = Dispatcher.FreeMarker)})
    public String improveEditEdu() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null ) {
            hrEmployee = this.hrEmployeeService.getByUserId(userInfo.getCompanyId(), userInfo.getUserId());
            if (hrEmployee.getComplete().intValue() == 3) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                employeeEduList=this.hrEmployeeEduService.getListByEmpId(userInfo.getCompanyId(),hrEmployee.getId());
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/employeeEdu!improveEditEdu.dhtml", type = Dispatcher.Redirect)})
    public String improveDelete() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null&&id!=null) {
            hrEmployeeEdu=this.hrEmployeeEduService.getById(id);
            if(hrEmployeeEdu!=null){
                this.hrEmployeeEduService.delete(hrEmployeeEdu);
            }
        }
        return "success";
    }

    public String getEdu() throws Exception {
        JSONObject root=new JSONObject();
        root.put("result","-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null&&id!=null) {
            hrEmployeeEdu=this.hrEmployeeEduService.getById(id);
            if(hrEmployeeEdu!=null){
                root.put("id",hrEmployeeEdu.getId());
                root.put("name",hrEmployeeEdu.getName());
                root.put("title",hrEmployeeEdu.getTitle());
                root.put("startDate", DateFormatUtil.format(hrEmployeeEdu.getStartDate(), DateFormatUtil.YEAR_MONTH_DAY_PATTERN));
                root.put("endDate",DateFormatUtil.format(hrEmployeeEdu.getEndDate(),DateFormatUtil.YEAR_MONTH_DAY_PATTERN));
                root.put("description",hrEmployeeEdu.getDescription());
                root.put("result","0");
            }
        }
        writeJsonByAction(root.toString());
        return null;
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/employeeEdu!improveEditEdu.dhtml", type = Dispatcher.Redirect)})
    public String improveSaveEdu() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null&&hrEmployeeEdu!=null) {
            hrEmployee = this.hrEmployeeService.getByUserId(userInfo.getCompanyId(), userInfo.getUserId());
            if (hrEmployee.getComplete().intValue() == 3) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                if(hrEmployeeEdu.getId()!=null){
                    HrEmployeeEdu old=this.hrEmployeeEduService.getById(hrEmployeeEdu.getId());
                    old.setName(hrEmployeeEdu.getName());
                    old.setTitle(hrEmployeeEdu.getTitle());
                    old.setStartDate(hrEmployeeEdu.getStartDate());
                    old.setEndDate(hrEmployeeEdu.getEndDate());
                    old.setDescription(hrEmployeeEdu.getDescription());
                    bind(old);
                    this.hrEmployeeEduService.save(old);
                }else{
                    hrEmployeeEdu.setEmpId(hrEmployee);
                    hrEmployeeEdu.setCompanyId(userInfo.getCompanyId());
                    hrEmployeeEdu.setUseYn("Y");
                    bind(hrEmployeeEdu);
                    this.hrEmployeeEduService.save(hrEmployeeEdu);
                }
            }
        }
        return "success";
    }

}
