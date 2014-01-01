package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import com.mingdao.api.billing.RequestBilling;
import com.mingdao.api.entity.AppConfig;
import com.mingdao.api.entity.Billing;
import com.mingdao.api.entity.User;
import com.mingdao.api.user.RequestUser;
import com.mingdao.api.utils.AppConfigUtil;
import com.mingdao.api.utils.SignatureUtil;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrEmployee;
import hrms.mingdao.hr.service.HrEmployeeService;
import hrms.mingdao.util.PinyinUtils;
import hrms.mingdao.web.support.ActionSupport;
import net.sf.json.JSONObject;
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

    @ReqGet
    @ModelDriver
    @ReqSet
    private HrEmployee hrEmployee;


    @ReqSet
    private List<HrEmployee> employeeList;

    @ReqSet
    private Map<String, User> userMapList = null;

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/index.ftl", type = Dispatcher.FreeMarker)})
    public String execute() throws Exception {
        return "success";
    }

    private List<Selector> searchModeCallbackNews() throws Exception {
        List<Selector> selectorList = new ArrayList<Selector>();
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("employee");
            userInfo.setMenuCss("news");
            String companyId = userInfo.getCompanyId();
            if (StringUtils.isNotBlank(companyId)) {
                selectorList.add(SelectorUtils.$eq("companyId", companyId));
                selectorList.add(SelectorUtils.$eq("useYn", "Y"));
                selectorList.add(SelectorUtils.$eq("complete", 1));
                selectorList.add(SelectorUtils.$order("userFirstPy"));
            } else {
                selectorList.add(SelectorUtils.$eq("id", 0l));
            }

        }
        return selectorList;
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/view/hr/employee/news.ftl", type = Dispatcher.FreeMarker)})
    public String news() throws Exception {
        UserInfo userInfo= UserSession.getUserInfo(getHttpServletRequest());
        if(userInfo!=null){
            List<HrEmployee> addEmployeeList=null;
            List<User> userList=RequestUser.getUserAll(userInfo.getAccessToken());
            if(userList!=null&&!userList.isEmpty()){
               List<String> userIdList= hrEmployeeService.getUserIdByCompany(userInfo.getCompanyId());
                addEmployeeList=new ArrayList<HrEmployee>();
                for(User user:userList){
                    if(userIdList!=null&&!userIdList.isEmpty()){
                        if(userIdList.contains(user.getId())){
                            continue;
                        }
                    }
                    hrEmployee=new HrEmployee();
                    hrEmployee.setCompanyId(userInfo.getCompanyId());
                    hrEmployee.setUserId(user.getId());
                    if(StringUtils.isNotBlank(user.getName())){
                        String pys[]=PinyinUtils.getHeadByString(user.getName());
                        if(pys!=null&&pys.length>0){
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
            pageObj = this.hrEmployeeService.getPageList(getStart(), 24, searchModeCallbackNews());
            if(pageObj!=null){
                employeeList=pageObj.getResultList();
                if(employeeList!=null&&!employeeList.isEmpty()){
                    Set<String> userIdSet = new HashSet<String>();
                    userMapList=new HashMap<String, User>();
                    for(HrEmployee employee:employeeList){
                        userIdSet.add(employee.getUserId());
                    }
                    if (userIdSet != null && !userIdSet.isEmpty()) {
                        userMapList = RequestUser.getMapUserList(userInfo.getAccessToken(), userIdSet);
                    }
                }
            }
        }
        return "success";
    }
}
