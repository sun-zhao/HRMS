package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import com.mingdao.api.company.RequestCompany;
import com.mingdao.api.entity.*;
import com.mingdao.api.user.RequestUser;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.*;
import hrms.mingdao.hr.service.*;
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
@Action(name = "employee", namespace = "/hr")
public class HrEmployeeAction extends ActionSupport<HrEmployee> {

    @Inject
    private HrEmployeeService hrEmployeeService;

    @Inject
    private HrOrgService hrOrgService;

    @Inject
    private HrDutyLevelService hrDutyLevelService;

    @Inject
    private HrOfficeAddrService hrOfficeAddrService;

    @Inject
    private HrJobService hrJobService;

    @Inject
    private HrEmployeeEduService hrEmployeeEduService;

    @Inject
    private HrEmployeeJobService hrEmployeeJobService;

    @Inject
    private HrEmployeeFamilyService hrEmployeeFamilyService;

    @Inject
    private SysCityService sysCityService;

    @Inject
    private SysProvinceService sysProvinceService;

    @Inject
    private SysCodeService sysCodeService;

    @ReqGet
    @ModelDriver
    @ReqSet
    private HrEmployee hrEmployee;

    @ReqGet
    @ModelDriver
    @ReqSet
    private HrEmployeeFamily hrEmployeeFamily;


    @ReqSet
    private List<HrEmployee> employeeList;

    @ReqSet
    private List<String> pyList;

    @ReqSet
    @ReqGet
    private Long id;

    @ReqSet
    @ReqGet
    private String word;

    @ReqSet
    @ReqGet
    private String userName;


    @ReqSet
    private List<HrEmployeeJob> employeeJobList;

    @ReqSet
    private List<HrEmployeeEdu> employeeEduList;

    @ReqSet
    private User user;

    @ReqSet
    private Map<String, User> userMapList = null;

    @ReqSet
    private List<SysCode> contryList;

    @ReqSet
    private List<SysCode> nationalityList;

    @ReqSet
    private List<SysCode> eduLevelList;

    @ReqSet
    private List<SysCode> politicsLevelList;

    @ReqSet
    private List<SysProvince> provinceList;

    @ReqSet
    private List<Department> departmentList;

    @ReqSet
    private List<SysCity> cityList;

    @ReqSet
    private List<HrJob> jobList;

    @ReqSet
    private List<HrDutyLevel> dutyLevelList;

    @ReqSet
    private List<HrOfficeAddr> officeAddrList;

    @ReqSet
    private  List<HrOrg> orgList;

    @ReqSet
    private Integer improveCount;

    private List<Selector> searchModeCallback() throws Exception {
        List<Selector> selectorList = new ArrayList<Selector>();
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("employee");
            userInfo.setMenuCss("list");
            String companyId = userInfo.getCompanyId();
            if (StringUtils.isNotBlank(companyId)) {
                selectorList.add(SelectorUtils.$eq("companyId", companyId));
                selectorList.add(SelectorUtils.$eq("complete", 0));
                if (StringUtils.isNotBlank(word)) {
                    selectorList.add(SelectorUtils.$eq("userFirstPy", word));
                }
                if (StringUtils.isNotBlank(userName)) {
                    selectorList.add(SelectorUtils.$like("userName", userName));
                }
                selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                selectorList.add(SelectorUtils.$order("userFirstPy"));
            } else {
                selectorList.add(SelectorUtils.$eq("id", 0l));
            }

        }
        return selectorList;
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/list.ftl", type = Dispatcher.FreeMarker)})
    public String execute() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            pageObj = this.hrEmployeeService.getPageList(getStart(), 20, searchModeCallback());
            if (pageObj != null) {
                employeeList = pageObj.getResultList();
                if (employeeList != null && !employeeList.isEmpty()) {
                    Set<String> userIdSet = new HashSet<String>();
                    userMapList = new HashMap<String, User>();
                    for (HrEmployee employee : employeeList) {
                        userIdSet.add(employee.getUserId());
                    }
                    if (userIdSet != null && !userIdSet.isEmpty()) {
                        userMapList = RequestUser.getMapUserList(userInfo.getAccessToken(), userIdSet);
                    }
                    pyList = this.hrEmployeeService.getListPyByCompanyEqComplete(userInfo.getCompanyId(), 0);
                }
            }
        }
        return "success";
    }


    private List<Selector> searchModeCallbackEntry() throws Exception {
        List<Selector> selectorList = new ArrayList<Selector>();
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("employee");
            userInfo.setMenuCss("entry");
            String companyId = userInfo.getCompanyId();
            if (StringUtils.isNotBlank(companyId)) {
                selectorList.add(SelectorUtils.$eq("companyId", companyId));
                selectorList.add(SelectorUtils.$gt("complete", 0));
                if (StringUtils.isNotBlank(word)) {
                    selectorList.add(SelectorUtils.$eq("userFirstPy", word));
                }
                if (StringUtils.isNotBlank(userName)) {
                    selectorList.add(SelectorUtils.$like("userName", userName));
                }
                selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                selectorList.add(SelectorUtils.$order("userFirstPy"));
            } else {
                selectorList.add(SelectorUtils.$eq("id", 0l));
            }

        }
        return selectorList;
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/entry.ftl", type = Dispatcher.FreeMarker)})
    public synchronized String entry() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            List<HrEmployee> addEmployeeList = null;
            List<User> userList = RequestUser.getUserAll(userInfo.getAccessToken());
            orgList = hrOrgService.getListByCompanyId(userInfo.getCompanyId());
            if (orgList == null || orgList.isEmpty()) {
                NetWork netWork = RequestCompany.getDetail(userInfo.getAccessToken());
                if (netWork != null) {
                    HrOrg org = new HrOrg();
                    org.setCompanyId(userInfo.getCompanyId());
                    org.setDisplayOrder(1);
                    org.setName(netWork.getName().toUpperCase());
                    org.setUseYn("Y");
                    bind(org);
                    this.hrOrgService.save(org);
                }
            }
            if (userList != null && !userList.isEmpty()) {

                List<String> jobNameList = this.hrJobService.getNameListByCompanyId(userInfo.getCompanyId());
                Set<String> newJobNameList = new HashSet<String>();
                if (jobNameList != null && !jobNameList.isEmpty()) {
                    for (User user : userList) {
                        if (StringUtils.isNotBlank(user.getJob())) {
                            String userJob = user.getJob().toUpperCase();
                            if (!jobNameList.contains(userJob)) {
                                newJobNameList.add(userJob);
                            }
                        }
                    }
                } else {
                    for (User user : userList) {
                        if (StringUtils.isNotBlank(user.getJob())) {
                            newJobNameList.add(user.getJob().toUpperCase());
                        }
                    }
                }

                List<HrJob> addJobList = null;
                if (newJobNameList != null && !newJobNameList.isEmpty()) {
                    addJobList = new ArrayList<HrJob>();
                    for (String jobName : newJobNameList) {
                        HrJob hrJob = new HrJob();
                        hrJob.setCompanyId(userInfo.getCompanyId());
                        hrJob.setName(jobName);
                        hrJob.setUseYn("Y");
                        bind(hrJob);
                        addJobList.add(hrJob);
                    }
                    this.hrJobService.save(addJobList);
                }
                List<String> userIdList = hrEmployeeService.getUserIdByCompany(userInfo.getCompanyId());
                addEmployeeList = new ArrayList<HrEmployee>();
                HrOrg hrOrg=null;
                orgList= this.hrOrgService.getListByCompanyId(userInfo.getCompanyId());
                if (orgList != null && !orgList.isEmpty()) {
                    hrOrg = orgList.get(0);
                }
                for (User user : userList) {
                    if (userIdList != null && !userIdList.isEmpty()) {
                        if (userIdList.contains(user.getId())) {
                            continue;
                        }
                    }
                    hrEmployee = new HrEmployee();
                    hrEmployee.setCompanyId(userInfo.getCompanyId());
                    hrEmployee.setUserId(user.getId());
                    hrEmployee.setContractFlag(0);
                    hrEmployee.setUserName(user.getName());
                    hrEmployee.setDeptName(user.getDepartment());
                    HrJob hrJob = null;
                    if (StringUtils.isNotBlank(user.getJob())) {
                        List<HrJob> jobs = this.hrJobService.getListByCompanyId(userInfo.getCompanyId(), user.getJob());
                        if (jobs != null && !jobs.isEmpty()) {
                            hrJob = jobs.get(0);
                        }
                    }
                    hrEmployee.setOrgId(hrOrg);
                    hrEmployee.setJobId(hrJob);
                    if (StringUtils.isNotBlank(user.getName())) {
                        String pys[] = PinyinUtils.getHeadByString(user.getName());
                        if (pys != null && pys.length > 0) {
                            hrEmployee.setUserFirstPy(pys[0].toUpperCase());
                        }
                    }
                    hrEmployee.setComplete(1);
                    hrEmployee.setUseYn("Y");
                    bind(hrEmployee);
                    addEmployeeList.add(hrEmployee);
                }
                this.hrEmployeeService.save(addEmployeeList);
            }
            pageObj = this.hrEmployeeService.getPageList(getStart(), 20, searchModeCallbackEntry());
            if (pageObj != null) {
                employeeList = pageObj.getResultList();
                if (employeeList != null && !employeeList.isEmpty()) {
                    Set<String> userIdSet = new HashSet<String>();
                    userMapList = new HashMap<String, User>();
                    for (HrEmployee employee : employeeList) {
                        userIdSet.add(employee.getUserId());
                    }
                    if (userIdSet != null && !userIdSet.isEmpty()) {
                        userMapList = RequestUser.getMapUserList(userInfo.getAccessToken(), userIdSet);
                    }
                    pyList = this.hrEmployeeService.getListPyByCompanyNotEqComplete(userInfo.getCompanyId(), 0);
                }
                improveCount = hrEmployeeService.getCountByCompanyEqComplete(userInfo.getCompanyId(), 1);
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/employee!news.dhtml", type = Dispatcher.Redirect)})
    public String sendImproveAll() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            List<Selector> selectorList = new ArrayList<Selector>();
            selectorList.add(SelectorUtils.$eq("companyId", userInfo.getCompanyId()));
            selectorList.add(SelectorUtils.$eq("useYn", "Y"));
            selectorList.add(SelectorUtils.$eq("complete", 1));
            employeeList = this.hrEmployeeService.getList(selectorList);
            if (employeeList != null && !employeeList.isEmpty()) {
                for (HrEmployee employee : employeeList) {
                    if (employee.getComplete().intValue() == 1) {
                        employee.setComplete(2);
                        bind(employee);
                    }
                }
                this.hrEmployeeService.save(employeeList);
            }
        }
        return "success";
    }

    public String sendImprove() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrEmployee = this.hrEmployeeService.getById(id);
            if (hrEmployee.getComplete().intValue() == 1) {
                hrEmployee.setComplete(2);

//                String msgContent = "#完善个人资料# " + userInfo.getUserName() + "邀请您完善个人资料，请您点击链接进行操作";
//                msgContent += "<a target=\"_blank\" href=\"" + getMdURI() + "&action=improve\">完善个人资料</a>";
//                String messageId = null;
//                try {
//                    messageId = RequestMessage.createSys(userInfo.getAccessToken(), hrEmployee.getUserId(), userInfo.getCompanyId(),
//                            msgContent, userInfo.getAppKey(), userInfo.getAppSecret());
//                    hrEmployee.setCompleteMessageId(messageId);
//                } catch (Exception e) {
//                    hrEmployee.setCompleteMessageId(messageId);
//                }
                bind(hrEmployee);
                this.hrEmployeeService.save(hrEmployee);
                root.put("userName", hrEmployee.getUserName());
                root.put("result", "0");
            }
        }
        writeJsonByAction(root.toString());
        return null;
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/improve.ftl", type = Dispatcher.FreeMarker),
            @Result(name = "info", path = "/hr/employee!info.dhtml", type = Dispatcher.Redirect)})
    public synchronized String improve() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("myEmployee");
            hrEmployee = this.hrEmployeeService.getByUserId(userInfo.getCompanyId(), userInfo.getUserId());
            if (hrEmployee.getComplete().intValue() == 2) {
                try {
                    user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                    if (user != null) {
                        hrEmployee.setUserSex(BeanUtils.convertValue(user.getGender(), Integer.class));
                        hrEmployee.setMobileTel(user.getMobilePhone());
                        hrEmployee.setOfficeTel(user.getWork_phone());
                        hrEmployee.setUserEmail(user.getEmail());
                        if (StringUtils.isNotBlank(user.getBirth())) {
                            Date birthDayDate = DateFormatUtil.parse(user.getBirth(), DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                            if (birthDayDate != null) {
                                hrEmployee.setBirthDay(birthDayDate);
                            }
                        }
                        List<Job> jobList = user.getJobList();
                        if (jobList != null && !jobList.isEmpty()) {
                            employeeJobList = new ArrayList<HrEmployeeJob>();
                            for (Job job : jobList) {
                                HrEmployeeJob employeeJob = new HrEmployeeJob();
                                employeeJob.setCompanyId(userInfo.getCompanyId());
                                employeeJob.setEmpId(hrEmployee);
                                employeeJob.setName(job.getName());
                                employeeJob.setTitle(job.getTitle());
                                employeeJob.setDescription(job.getDescription());
                                if (StringUtils.isNotBlank(job.getStartDate())) {
                                    Date startDate = DateFormatUtil.parse(job.getStartDate(), DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                                    if (startDate != null) {
                                        employeeJob.setStartDate(startDate);
                                    }
                                }
                                if (StringUtils.isNotBlank(job.getEndDate())) {
                                    Date endDate = DateFormatUtil.parse(job.getEndDate(), DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                                    if (endDate != null) {
                                        employeeJob.setEndDate(endDate);
                                    }
                                }
                                employeeJob.setUseYn("Y");
                                bind(employeeJob);
                                employeeJobList.add(employeeJob);
                            }
                        }

                        List<Education> eduList = user.getEducationList();
                        if (eduList != null && !eduList.isEmpty()) {
                            employeeEduList = new ArrayList<HrEmployeeEdu>();
                            for (Education edu : eduList) {
                                HrEmployeeEdu employeeEdu = new HrEmployeeEdu();
                                employeeEdu.setCompanyId(userInfo.getCompanyId());
                                employeeEdu.setEmpId(hrEmployee);
                                employeeEdu.setName(edu.getName());
                                employeeEdu.setTitle(edu.getTitle());
                                employeeEdu.setDescription(edu.getDescription());
                                if (StringUtils.isNotBlank(edu.getStartDate())) {
                                    Date startDate = DateFormatUtil.parse(edu.getStartDate(), DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                                    if (startDate != null) {
                                        employeeEdu.setStartDate(startDate);
                                    }
                                }
                                if (StringUtils.isNotBlank(edu.getEndDate())) {
                                    Date endDate = DateFormatUtil.parse(edu.getEndDate(), DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                                    if (endDate != null) {
                                        employeeEdu.setEndDate(endDate);
                                    }
                                }
                                employeeEdu.setUseYn("Y");
                                bind(employeeEdu);
                                employeeEduList.add(employeeEdu);
                            }
                        }
                        hrEmployee.setComplete(3);
                        this.hrEmployeeService.save(hrEmployee, employeeEduList, employeeJobList);
                        employeeEduList = this.hrEmployeeEduService.getListByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
                        employeeJobList = this.hrEmployeeJobService.getListByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
                    }
                } catch (Exception e) {

                }
            } else if (hrEmployee.getComplete().intValue() == 3) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                employeeEduList = this.hrEmployeeEduService.getListByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
                employeeJobList = this.hrEmployeeJobService.getListByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
            } else if (hrEmployee.getComplete().intValue() == 0) {
                return "info";
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/improve/edit.ftl", type = Dispatcher.FreeMarker)})
    public String improveEdit() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            hrEmployee = this.hrEmployeeService.getByUserId(userInfo.getCompanyId(), userInfo.getUserId());
            if (hrEmployee.getComplete().intValue() == 3) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                eduLevelList = this.sysCodeService.getListByParentId("EDU_LEVEL");
                contryList = this.sysCodeService.getListByParentId("CONTRY");
                nationalityList = this.sysCodeService.getListByParentId("NATIONALITY");
                politicsLevelList = this.sysCodeService.getListByParentId("POLITICS_LEVEL");
                officeAddrList = this.hrOfficeAddrService.getListByCompanyId(userInfo.getCompanyId());
                provinceList = this.sysProvinceService.getList();
                departmentList = userInfo.getDepartmentList();
                if (provinceList != null && !provinceList.isEmpty()) {
                    SysProvince province = provinceList.get(0);
                    if (province != null) {
                        cityList = this.sysCityService.getListByProvince(province.getId());
                    }
                }
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/employee!improveEditFamily.dhtml", type = Dispatcher.Redirect)})
    public String improveSaveInfo() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && hrEmployee != null) {
            HrEmployee old = this.hrEmployeeService.getById(hrEmployee.getId());
            if (old != null) {
                old.setEntryDate(hrEmployee.getEntryDate());
                old.setMobileTel(hrEmployee.getMobileTel());
                old.setOfficeTel(hrEmployee.getOfficeTel());
                old.setUserEmail(hrEmployee.getUserEmail());
                old.setOfficeAddress(hrEmployee.getOfficeAddress());
                old.setBirthDay(hrEmployee.getBirthDay());
                old.setUserSex(hrEmployee.getUserSex());
                if (hrEmployee.getEduLevel() != null && hrEmployee.getEduLevel().getId() != null) {
                    old.setEduLevel(hrEmployee.getEduLevel());
                }
                if (hrEmployee.getNationalityId() != null && hrEmployee.getNationalityId().getId() != null) {
                    old.setNationalityId(hrEmployee.getNationalityId());
                }
                if (hrEmployee.getContryId() != null && hrEmployee.getContryId().getId() != null) {
                    old.setContryId(hrEmployee.getContryId());
                }
                if (hrEmployee.getProvinceId() != null && hrEmployee.getProvinceId().getId() != null) {
                    old.setProvinceId(hrEmployee.getProvinceId());
                }
                if (hrEmployee.getCityId() != null && hrEmployee.getCityId().getId() != null) {
                    old.setCityId(hrEmployee.getCityId());
                }
                if (hrEmployee.getPoliticsLevel() != null && hrEmployee.getPoliticsLevel().getId() != null) {
                    old.setPoliticsLevel(hrEmployee.getPoliticsLevel());
                }
                old.setIdCard(hrEmployee.getIdCard());
                old.setBankCard(hrEmployee.getBankCard());
                bind(old);
                this.hrEmployeeService.save(old);
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/improve/editFamily.ftl", type = Dispatcher.FreeMarker)})
    public String improveEditFamily() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            hrEmployee = this.hrEmployeeService.getByUserId(userInfo.getCompanyId(), userInfo.getUserId());
            if (hrEmployee.getComplete().intValue() == 3) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                hrEmployeeFamily = this.hrEmployeeFamilyService.getByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/employeeJob!improveEditJob.dhtml", type = Dispatcher.Redirect)})
    public String improveSaveFamily() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && hrEmployeeFamily != null) {
            hrEmployee = this.hrEmployeeService.getByUserId(userInfo.getCompanyId(), userInfo.getUserId());
            if (hrEmployee != null) {
                HrEmployeeFamily old = this.hrEmployeeFamilyService.getByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
                if (old == null) {
                    old = new HrEmployeeFamily();
                    old.setCompanyId(userInfo.getCompanyId());
                    old.setEmpId(hrEmployee);
                }
                if (old != null) {
                    old.setFamilyName(hrEmployeeFamily.getFamilyName());
                    old.setFamilyTel(hrEmployeeFamily.getFamilyTel());
                    old.setFamilyAddress(hrEmployeeFamily.getFamilyAddress());
                    old.setFamilyRelation(hrEmployeeFamily.getFamilyRelation());
                    old.setMarry(hrEmployeeFamily.getMarry());
                    old.setResidenceBooklet(hrEmployeeFamily.getResidenceBooklet());
                    old.setResidenceBookletType(hrEmployeeFamily.getResidenceBookletType());
                    old.setUseYn("Y");
                    bind(old);
                    this.hrEmployeeFamilyService.save(old);
                }
            }
        }
        return "success";
    }

    public String improveDone() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            hrEmployee = this.hrEmployeeService.getByUserId(userInfo.getCompanyId(), userInfo.getUserId());
            if (hrEmployee.getComplete().intValue() == 3) {
                hrEmployee.setComplete(0);
                bind(hrEmployee);
                this.hrEmployeeService.save(hrEmployee);
            }
        }
        return "saveSuccess";
    }


    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/info.ftl", type = Dispatcher.FreeMarker)})
    public String info() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("myEmployee");
            hrEmployee = this.hrEmployeeService.getByUserId(userInfo.getCompanyId(), userInfo.getUserId());
            if (hrEmployee != null) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                employeeEduList = this.hrEmployeeEduService.getListByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
                employeeJobList = this.hrEmployeeJobService.getListByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
            }
        }
        return "success";
    }


    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/view/info.ftl", type = Dispatcher.FreeMarker)})
    public String viewInfo() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrEmployee = this.hrEmployeeService.getById(id);
            if (hrEmployee != null) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/edit/info.ftl", type = Dispatcher.FreeMarker)})
    public String editInfo() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrEmployee = this.hrEmployeeService.getById(id);
            if (hrEmployee != null) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                eduLevelList = this.sysCodeService.getListByParentId("EDU_LEVEL");
                contryList = this.sysCodeService.getListByParentId("CONTRY");
                nationalityList = this.sysCodeService.getListByParentId("NATIONALITY");
                politicsLevelList = this.sysCodeService.getListByParentId("POLITICS_LEVEL");
                jobList = this.hrJobService.getListByCompanyId(userInfo.getCompanyId());
                dutyLevelList = this.hrDutyLevelService.getListByCompanyId(userInfo.getCompanyId());
                officeAddrList = this.hrOfficeAddrService.getListByCompanyId(userInfo.getCompanyId());
                orgList=this.hrOrgService.getListByCompanyId(userInfo.getCompanyId());
                provinceList = this.sysProvinceService.getList();
                departmentList = userInfo.getDepartmentList();
                if (provinceList != null && !provinceList.isEmpty()) {
                    SysProvince province = provinceList.get(0);
                    if (province != null) {
                        cityList = this.sysCityService.getListByProvince(province.getId());
                    }
                }
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/employee!viewInfo.dhtml?id=${hrEmployee.id}", type = Dispatcher.Redirect)})
    public String saveInfo() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && hrEmployee != null) {
            HrEmployee old = this.hrEmployeeService.getById(hrEmployee.getId());
            if (old != null) {
                old.setDeptName(hrEmployee.getDeptName());
                old.setWorkState(hrEmployee.getWorkState());
                old.setEmpType(hrEmployee.getEmpType());
                if (hrEmployee.getOrgId() != null && hrEmployee.getOrgId().getId() != null) {
                    old.setOrgId(hrEmployee.getOrgId());
                }
                if (hrEmployee.getDutyLevel() != null && hrEmployee.getDutyLevel().getId() != null) {
                    old.setDutyLevel(hrEmployee.getDutyLevel());
                }
                if (hrEmployee.getJobId() != null && hrEmployee.getJobId().getId() != null) {
                    old.setJobId(hrEmployee.getJobId());
                }

                old.setEntryDate(hrEmployee.getEntryDate());
                old.setMobileTel(hrEmployee.getMobileTel());
                old.setOfficeTel(hrEmployee.getOfficeTel());
                old.setUserEmail(hrEmployee.getUserEmail());
                old.setBirthDay(hrEmployee.getBirthDay());
                old.setUserSex(hrEmployee.getUserSex());
                if (hrEmployee.getOfficeAddress() != null && hrEmployee.getOfficeAddress().getId() != null) {
                    old.setOfficeAddress(hrEmployee.getOfficeAddress());
                }
                if (hrEmployee.getEduLevel() != null && hrEmployee.getEduLevel().getId() != null) {
                    old.setEduLevel(hrEmployee.getEduLevel());
                }
                if (hrEmployee.getNationalityId() != null && hrEmployee.getNationalityId().getId() != null) {
                    old.setNationalityId(hrEmployee.getNationalityId());
                }
                if (hrEmployee.getContryId() != null && hrEmployee.getContryId().getId() != null) {
                    old.setContryId(hrEmployee.getContryId());
                }
                if (hrEmployee.getProvinceId() != null && hrEmployee.getProvinceId().getId() != null) {
                    old.setProvinceId(hrEmployee.getProvinceId());
                }
                if (hrEmployee.getCityId() != null && hrEmployee.getCityId().getId() != null) {
                    old.setCityId(hrEmployee.getCityId());
                }
                if (hrEmployee.getPoliticsLevel() != null && hrEmployee.getPoliticsLevel().getId() != null) {
                    old.setPoliticsLevel(hrEmployee.getPoliticsLevel());
                }
                old.setIdCard(hrEmployee.getIdCard());
                old.setBankCard(hrEmployee.getBankCard());
                bind(old);
                this.hrEmployeeService.save(old);
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/view/family.ftl", type = Dispatcher.FreeMarker)})
    public String viewFamily() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrEmployee = this.hrEmployeeService.getById(id);
            if (hrEmployee != null) {
                hrEmployeeFamily = this.hrEmployeeFamilyService.getByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/edit/family.ftl", type = Dispatcher.FreeMarker)})
    public String editFamily() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrEmployee = this.hrEmployeeService.getById(id);
            if (hrEmployee != null) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                hrEmployeeFamily = this.hrEmployeeFamilyService.getByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/employee!viewFamily.dhtml?id=${hrEmployee.id}", type = Dispatcher.Redirect)})
    public String saveFamily() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && hrEmployeeFamily != null & id != null) {
            hrEmployee = this.hrEmployeeService.getById(id);
            if (hrEmployee != null) {
                HrEmployeeFamily old = this.hrEmployeeFamilyService.getByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
                if (old == null) {
                    old = new HrEmployeeFamily();
                    old.setCompanyId(userInfo.getCompanyId());
                    old.setEmpId(hrEmployee);
                }
                if (old != null) {
                    old.setFamilyName(hrEmployeeFamily.getFamilyName());
                    old.setFamilyTel(hrEmployeeFamily.getFamilyTel());
                    old.setFamilyAddress(hrEmployeeFamily.getFamilyAddress());
                    old.setFamilyRelation(hrEmployeeFamily.getFamilyRelation());
                    old.setMarry(hrEmployeeFamily.getMarry());
                    old.setResidenceBooklet(hrEmployeeFamily.getResidenceBooklet());
                    old.setResidenceBookletType(hrEmployeeFamily.getResidenceBookletType());
                    old.setUseYn("Y");
                    bind(old);
                    this.hrEmployeeFamilyService.save(old);
                }
            }
        }
        return "success";
    }

}
