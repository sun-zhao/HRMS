package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import com.mingdao.api.entity.User;
import com.mingdao.api.user.RequestUser;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.*;
import hrms.mingdao.hr.service.*;
import hrms.mingdao.web.support.ActionSupport;
import net.sf.json.JSONObject;
import org.guiceside.commons.Page;
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
@Action(name = "contract", namespace = "/hr")
public class HrContractAction extends ActionSupport<HrContract> {

    @Inject
    private HrContractService hrContractService;

    @Inject
    private HrContractTypeService hrContractTypeService;

    @Inject
    private HrContractTemplateService hrContractTemplateService;

    @Inject
    private HrEmployeeFamilyService hrEmployeeFamilyService;


    @Inject
    private HrEmployeeService hrEmployeeService;

    @ReqSet
    private Page<HrEmployee> employeePage;

    @ReqGet
    @ModelDriver
    @ReqSet
    private HrContract hrContract;

    @ReqSet
    private HrContractType hrContractType;

    @ReqSet
    private HrEmployee hrEmployee;

    @ReqSet
    private HrContractTemplate hrContractTemplate;

    @ReqSet
    @ReqGet
    private Long id;


    @ReqSet
    @ReqGet
    private Long empId;

    @ReqSet
    @ReqGet
    private String word;

    @ReqSet
    @ReqGet
    private String userName;

    @ReqSet
    private User user;

    @ReqSet
    private Map<String, User> userMapList = null;

    @ReqSet
    private List<HrEmployee> employeeList;

    @ReqSet
    private List<HrContractTemplate> templateList;

    @ReqSet
    private List<String> pyList;

    @ReqSet
    private Date dueDate;

    @ReqSet
    private Date staticDueDate;


    @ReqSet
    private Date renewalDate;

    @ReqSet
    private List<HrContract> contractList;

    public String execute() throws Exception {
        return "success";
    }

    private List<Selector> searchModeCallbackBySigning() throws Exception {
        List<Selector> selectorList = new ArrayList<Selector>();
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("contract");
            userInfo.setMenuCss("signing");
            String companyId = userInfo.getCompanyId();
            if (StringUtils.isNotBlank(companyId)) {
                selectorList.add(SelectorUtils.$eq("companyId", companyId));
                selectorList.add(SelectorUtils.$eq("complete", 0));
                selectorList.add(SelectorUtils.$eq("contractFlag", 0));
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

    private List<Selector> searchModeCallbackByDue(Date endDate) throws Exception {
        List<Selector> selectorList = new ArrayList<Selector>();
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("contract");
            userInfo.setMenuCss("due");
            String companyId = userInfo.getCompanyId();
            if (StringUtils.isNotBlank(companyId)) {
                selectorList.add(SelectorUtils.$alias("empId", "empId"));
                selectorList.add(SelectorUtils.$eq("companyId", companyId));
                selectorList.add(SelectorUtils.$eq("renewalFlag", 0));
                selectorList.add(SelectorUtils.$lt("endDate", endDate));
                if (StringUtils.isNotBlank(word)) {
                    selectorList.add(SelectorUtils.$eq("empId.userFirstPy", word));
                }
                if (StringUtils.isNotBlank(userName)) {
                    selectorList.add(SelectorUtils.$like("empId.userName", userName));
                }
                selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                selectorList.add(SelectorUtils.$order("empId.userFirstPy"));
            } else {
                selectorList.add(SelectorUtils.$eq("id", 0l));
            }

        }
        return selectorList;
    }


    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/contract/signing.ftl", type = Dispatcher.FreeMarker)})
    public String signing() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            employeePage = this.hrEmployeeService.getPageList(getStart(), 16, searchModeCallbackBySigning());
            if (employeePage != null) {
                employeeList = employeePage.getResultList();
                if (employeeList != null && !employeeList.isEmpty()) {
                    Set<String> userIdSet = new HashSet<String>();
                    userMapList = new HashMap<String, User>();
                    for (HrEmployee employee : employeeList) {
                        userIdSet.add(employee.getUserId());
                    }
                    if (userIdSet != null && !userIdSet.isEmpty()) {
                        userMapList = RequestUser.getMapUserList(userInfo.getAccessToken(), userIdSet);
                    }
                    pyList = this.hrEmployeeService.getListPyByCompanyEqContractFlag(userInfo.getCompanyId(), 0);
                }
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/contract/due.ftl", type = Dispatcher.FreeMarker)})
    public String due() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            hrContractType = hrContractTypeService.getByCompanyId(userInfo.getCompanyId());
            if (hrContractType != null) {
                Date cudDate = DateFormatUtil.getCurrentDate(false);
                cudDate = DateFormatUtil.addDay(cudDate, hrContractType.getStaticRemindDay());
                pageObj = this.hrContractService.getPageList(getStart(), 16, searchModeCallbackByDue(cudDate));
                if (pageObj != null) {
                    contractList = pageObj.getResultList();
                    if (contractList != null && !contractList.isEmpty()) {
                        Set<String> userIdSet = new HashSet<String>();
                        userMapList = new HashMap<String, User>();
                        for (HrContract contract : contractList) {
                            userIdSet.add(contract.getEmpId().getUserId());
                        }
                        if (userIdSet != null && !userIdSet.isEmpty()) {
                            userMapList = RequestUser.getMapUserList(userInfo.getAccessToken(), userIdSet);
                        }
                        pyList = this.hrContractService.getListPyByCompanyEqContractFlag(userInfo.getCompanyId(), cudDate);
                    }
                }
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/view/contract.ftl", type = Dispatcher.FreeMarker)})
    public String view() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && empId != null) {
            hrEmployee = hrEmployeeService.getById(empId);
            if (hrEmployee != null) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                List<Selector> selectorList = new ArrayList<Selector>();
                selectorList.add(SelectorUtils.$eq("companyId", userInfo.getCompanyId()));
                selectorList.add(SelectorUtils.$eq("empId.id", hrEmployee.getId()));
                selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                selectorList.add(SelectorUtils.$order("contractState", true));
                selectorList.add(SelectorUtils.$order("startDate", true));
                contractList = hrContractService.getList(selectorList);
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/contract/news.ftl", type = Dispatcher.FreeMarker),
            @Result(name = "viewContract", path = "/hr/contract!view.dhtml?empId=${hrEmployee}", type = Dispatcher.Redirect)})
    public String news() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && empId != null) {
            hrEmployee = hrEmployeeService.getById(empId);
            if (hrEmployee != null) {
                if (hrEmployee.getContractFlag().intValue() == 1) {
                    return "viewContract";
                }
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                hrContractType = hrContractTypeService.getByCompanyId(userInfo.getCompanyId());
                templateList = this.hrContractTemplateService.getListByCompanyId(userInfo.getCompanyId());
                if (hrContractType != null) {
                    Date entryDate = hrEmployee.getEntryDate();
                    if (entryDate != null) {
                        staticDueDate = DateFormatUtil.addMonth(entryDate, hrContractType.getStaticValidMonth());
                        staticDueDate = DateFormatUtil.addDay(staticDueDate, -1);
                        dueDate = DateFormatUtil.parse("9999-12-31", DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                    }
                }
            }
        }
        return "success";
    }


    public String saveNews() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && empId != null && hrContract != null) {
            hrEmployee = hrEmployeeService.getById(empId);
            if (hrEmployee != null) {

                hrEmployee.setContractFlag(1);
                hrContract.setEmpId(hrEmployee);
                hrContract.setRenewalFlag(0);
                hrContract.setCompanyId(userInfo.getCompanyId());
                hrContract.setStartDate(hrEmployee.getEntryDate());
                hrContract.setContractState(0);
                String insuranceTypeList[] = getHttpServletRequest().getParameterValues("insuranceType");
                String types = "";
                if (insuranceTypeList != null && insuranceTypeList.length > 0) {
                    for (String insuranceType : insuranceTypeList) {
                        types += insuranceType + ",";
                    }
                    if (StringUtils.isNotBlank(types)) {
                        types = types.substring(0, types.length() - 1);
                    }
                } else {
                    types = getParameter("insuranceType");
                }
                hrContract.setInsuranceType(types);
                hrContract.setUseYn("Y");
                bind(hrContract);
                this.hrContractService.save(hrContract, hrEmployee);
                script = "parent.printContract('" + hrContract.getId() + "');parent.reload();";
            }
        }
        return "saveSuccess";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/contract/print.ftl", type = Dispatcher.FreeMarker)})
    public String print() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrContract = hrContractService.getById(id);
            hrContractType=this.hrContractTypeService.getByCompanyId(userInfo.getCompanyId());
            if (hrContract != null) {
                hrEmployee = hrContract.getEmpId();
                HrEmployeeFamily hrEmployeeFamily = this.hrEmployeeFamilyService.getByEmpId(userInfo.getCompanyId(), hrEmployee.getId());
                if (hrEmployee != null) {
                    hrContractTemplate = this.hrContract.getTemplateId();
                    String content = hrContractTemplate.getTemplate();
                    List<String> templateKeyList = new ArrayList<String>();
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"userName\">#员工姓名#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"orgName\">#员工公司#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"userSex\">#员工性别#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"idCard\">#身份证号#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"familyAddress\">#家庭地址#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"jobName\">#岗位#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"contractNo\">#合同编号#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"contractType\">#合同类型#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"contractStartDate\">#合同开始日期#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"contractEndDate\">#合同截止日期#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"contractYear\">#合同有效期#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"probationMonth\">#试用期长度#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"probationStartDate\">#试用期开始日期#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"probationEndDate\">#试用期结束日期#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"probationPay\">#试用期薪资#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"pay\">#转正后薪资#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"workArea\">#工作地点#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"workTime\">#工时制度#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"insuranceArea\">#保险缴纳地#</span>");
                    templateKeyList.add("<span style=\"color:#FF0000;\" uid=\"insuranceType\">#保险种类#</span>");
                    for (String key : templateKeyList) {
                        if (key.indexOf("contractNo") != -1) {
                            content = content.replaceAll(key, get(hrContract, "contractNo"));
                        } else if (key.indexOf("userName") != -1) {
                            content = content.replaceAll(key, get(hrEmployee, "userName"));
                        } else if (key.indexOf("orgName") != -1) {
                            content = content.replaceAll(key, get(hrEmployee, "orgId.name"));
                        } else if (key.indexOf("userSex") != -1) {
                            if (hrEmployee.getUserSex() != null) {
                                if (hrEmployee.getUserSex().intValue() == 1) {
                                    content = content.replaceAll(key, "男");
                                } else if (hrEmployee.getUserSex().intValue() == 2) {
                                    content = content.replaceAll(key, "女");
                                }
                            } else {
                                content = content.replaceAll(key, "");
                            }
                        } else if (key.indexOf("idCard") != -1) {
                            content = content.replaceAll(key, get(hrEmployee, "idCard"));
                        }else if (key.indexOf("familyAddress") != -1) {
                            if(hrEmployeeFamily!=null){
                                content = content.replaceAll(key, get(hrEmployeeFamily, "familyAddress"));
                            }else {
                                content = content.replaceAll(key, "");
                            }
                        }else if (key.indexOf("jobName") != -1) {
                            content = content.replaceAll(key, get(hrEmployee, "jobId.name"));
                        }else if (key.indexOf("contractType") != -1) {
                            if (hrContract.getContractType() != null) {
                                if (hrContract.getContractType().intValue() == 1) {
                                    content = content.replaceAll(key, "固定期劳动合同");
                                } else if (hrContract.getContractType().intValue() == 0) {
                                    content = content.replaceAll(key, "无固定期劳动合同");
                                }
                            } else {
                                content = content.replaceAll(key, "");
                            }
                        }else if (key.indexOf("contractStartDate") != -1) {
                            if(hrContract.getStartDate()!=null){
                                String dateStr=DateFormatUtil.format(hrContract.getStartDate(),DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                                content = content.replaceAll(key, dateStr);
                            }else{
                                content = content.replaceAll(key, "");
                            }
                        }else if (key.indexOf("contractEndDate") != -1) {
                            if(hrContract.getEndDate()!=null){
                                String dateStr=DateFormatUtil.format(hrContract.getEndDate(),DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                                content = content.replaceAll(key, dateStr);
                            }else{
                                content = content.replaceAll(key, "");
                            }
                        }else if (key.indexOf("contractYear") != -1) {
                            if (hrContract.getContractType() != null&&hrContractType!=null) {
                                if (hrContract.getContractType().intValue() == 1) {
                                    Integer year=hrContractType.getStaticValidMonth()/12;
                                    content = content.replaceAll(key, year+"年");
                                } else if (hrContract.getContractType().intValue() == 0) {
                                    content = content.replaceAll(key, "永久");
                                }
                            }else{
                                content = content.replaceAll(key, "");
                            }

                        }else if (key.indexOf("probationMonth") != -1) {
                            if(hrContractType!=null){
                                if (hrContract.getContractType().intValue() == 1) {
                                    content = content.replaceAll(key, hrContractType.getStaticValidMonth()+"月");
                                }else{
                                    content = content.replaceAll(key, hrContractType.getValidMonth()+"月");
                                }
                            }else{
                                content = content.replaceAll(key, "");
                            }
                        }else if (key.indexOf("probationStartDate") != -1) {
                            if(hrEmployee.getEntryDate()!=null){
                                String dateStr=DateFormatUtil.format(hrEmployee.getEntryDate(),DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                                content = content.replaceAll(key, dateStr);
                            }else{
                                content = content.replaceAll(key, "");
                            }
                        }else if (key.indexOf("probationEndDate") != -1) {
                            if(hrContractType!=null&&hrEmployee.getEntryDate()!=null){
                                if (hrContract.getContractType().intValue() == 1) {
                                    Date probationEndDate=DateFormatUtil.addMonth(hrEmployee.getEntryDate(),hrContractType.getStaticValidMonth());
                                    probationEndDate=DateFormatUtil.addDay(probationEndDate,-1);
                                    String dateStr=DateFormatUtil.format(probationEndDate,DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                                    content = content.replaceAll(key, dateStr);
                                }else{
                                    Date probationEndDate=DateFormatUtil.addMonth(hrEmployee.getEntryDate(),hrContractType.getValidMonth());
                                    probationEndDate=DateFormatUtil.addDay(probationEndDate,-1);
                                    String dateStr=DateFormatUtil.format(probationEndDate,DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                                    content = content.replaceAll(key, dateStr);
                                }
                            }else{
                                content = content.replaceAll(key, "");
                            }
                            content = content.replaceAll(key, get(hrEmployee, "jobId.name"));
                        }else if (key.indexOf("probationPay") != -1) {
                            content = content.replaceAll(key, get(hrContract, "probationPay"));
                        }else if (key.indexOf("pay") != -1) {
                            content = content.replaceAll(key, get(hrContract, "pay"));
                        }else if (key.indexOf("workArea") != -1) {
                            content = content.replaceAll(key, get(hrContract, "workArea"));
                        }else if (key.indexOf("workTime") != -1) {
                            content = content.replaceAll(key, get(hrContract, "workTime"));
                        }else if (key.indexOf("insuranceArea") != -1) {
                            content = content.replaceAll(key, get(hrContract, "insuranceArea"));
                        }else if (key.indexOf("insuranceType") != -1) {
                            content = content.replaceAll(key, get(hrContract, "insuranceType"));
                        }
                    }
                    hrContractTemplate.setTemplate(content);
                }
            }
        }
        return "success";
    }


    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/contract/renewal.ftl", type = Dispatcher.FreeMarker)})
    public String renewal() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrContract = hrContractService.getById(id);
            if (hrContract != null) {
                hrEmployee = hrContract.getEmpId();
                if (hrEmployee != null) {
                    user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                    renewalDate = DateFormatUtil.addDay(hrContract.getEndDate(), 1);
                    hrContractType = hrContractTypeService.getByCompanyId(userInfo.getCompanyId());
                    if (hrContractType != null && renewalDate != null) {
                        staticDueDate = DateFormatUtil.addMonth(renewalDate, hrContractType.getStaticValidMonth());
                        staticDueDate = DateFormatUtil.addDay(staticDueDate, -1);
                        dueDate = DateFormatUtil.parse("9999-12-31", DateFormatUtil.YEAR_MONTH_DAY_PATTERN);
                    }
                }
            }
        }
        return "success";
    }

    public String saveRenewal() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && hrContract != null) {
            HrContract old = this.hrContractService.getById(hrContract.getId());
            old.setRenewalFlag(1);
            bind(old);
            renewalDate = DateFormatUtil.addDay(old.getEndDate(), 1);

            HrContract renewalContract = new HrContract();
            renewalContract.setCompanyId(userInfo.getCompanyId());
            renewalContract.setEmpId(old.getEmpId());
            renewalContract.setContractNo(hrContract.getContractNo());
            renewalContract.setContractState(1);
            renewalContract.setRenewalFlag(0);
            renewalContract.setStartDate(renewalDate);
            renewalContract.setEndDate(hrContract.getEndDate());
            renewalContract.setContractType(hrContract.getContractType());
            renewalContract.setRemarks(hrContract.getRemarks());
            renewalContract.setUseYn("Y");
            bind(renewalContract);

            contractList = new ArrayList<HrContract>();
            contractList.add(old);
            contractList.add(renewalContract);
            this.hrContractService.save(contractList);
        }
        return "saveSuccess";
    }

}
