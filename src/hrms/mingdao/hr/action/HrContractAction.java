package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import com.mingdao.api.entity.User;
import com.mingdao.api.user.RequestUser;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrContract;
import hrms.mingdao.hr.entity.HrContractType;
import hrms.mingdao.hr.entity.HrEmployee;
import hrms.mingdao.hr.entity.HrEmployeeJob;
import hrms.mingdao.hr.service.HrContractService;
import hrms.mingdao.hr.service.HrContractTypeService;
import hrms.mingdao.hr.service.HrEmployeeJobService;
import hrms.mingdao.hr.service.HrEmployeeService;
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
            hrContractType = hrContractTypeService.getListByCompanyId(userInfo.getCompanyId());
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
            @Result(name = "success", path = "/view/hr/contract/news.ftl", type = Dispatcher.FreeMarker)})
    public String news() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && empId != null) {
            hrEmployee = hrEmployeeService.getById(empId);
            if (hrEmployee != null) {
                user = RequestUser.getUserDetail(userInfo.getAccessToken(), hrEmployee.getUserId());
                hrContractType = hrContractTypeService.getListByCompanyId(userInfo.getCompanyId());
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
                hrContract.setUseYn("Y");
                bind(hrContract);
                this.hrContractService.save(hrContract, hrEmployee);
            }
        }
        return "saveSuccess";
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
                    hrContractType = hrContractTypeService.getListByCompanyId(userInfo.getCompanyId());
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
        if (userInfo != null  && hrContract != null) {
            HrContract old=this.hrContractService.getById(hrContract.getId());
            old.setRenewalFlag(1);
            bind(old);
            renewalDate = DateFormatUtil.addDay(old.getEndDate(), 1);

            HrContract renewalContract=new HrContract();
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

            contractList=new ArrayList<HrContract>();
            contractList.add(old);
            contractList.add(renewalContract);
            this.hrContractService.save(contractList);
        }
        return "saveSuccess";
    }

}
