package hrms.mingdao.common.action;

import com.google.inject.Inject;
import com.mingdao.api.app.RequestApp;
import com.mingdao.api.company.RequestCompany;
import com.mingdao.api.entity.*;
import com.mingdao.api.oauth2.RequestOauth2;
import com.mingdao.api.passport.RequestPassport;
import com.mingdao.api.user.RequestUser;
import com.mingdao.api.utils.AppConfigUtil;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrEmployee;
import hrms.mingdao.hr.service.HrEmployeeService;
import hrms.mingdao.util.HttpRequestDeviceUtils;
import hrms.mingdao.util.HttpUtils;
import net.sf.json.JSONObject;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.web.action.BaseAction;
import org.guiceside.web.annotation.*;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhenjiaWang
 * Date: 12-7-12
 * Time: 下午9:49
 * To change this template use File | Settings | File Templates.
 */
@Action(name = "authorize", namespace = "/common")
public class AuthorizeAction extends BaseAction {

    @Inject
    private HrEmployeeService hrEmployeeService;

    @ReqGet
    @ReqSet
    private String code;

    @ReqSet
    private Long id;

    @ReqGet
    private String state;

    @ReqSet
    private String url;



    @ReqSet
    private String authorizeUrl;

    @Override
    @PageFlow(result = {@Result(name = "success", path = "/common/authorize!index.dhtml", type = Dispatcher.Redirect),
            @Result(name = "login", path = "/view/authorize.ftl", type = Dispatcher.FreeMarker),
            @Result(name = "improve", path = "/hr/employee!improve.dhtml", type = Dispatcher.Redirect),
            @Result(name = "empInfo", path = "/hr/employee!info.dhtml", type = Dispatcher.Redirect)})
    public String execute() throws Exception {
        if (StringUtils.isNotBlank(code)) {
            UserInfo userInfo = UserSession.create(getHttpServletRequest());
            userInfo.setLoggedIn(true);
            OAuth2Object oAuth2Object = RequestOauth2.getAccessToken(code);
            if (oAuth2Object != null) {
                userInfo.setAccessToken(oAuth2Object.getAccessToken());
                NetWork netWork = RequestCompany.getDetail(oAuth2Object.getAccessToken());
                if (netWork != null) {
                    userInfo.setCompanyId(netWork.getId());
                    userInfo.setCompanyName(netWork.getName());
                    userInfo.setCompanyNameEn(netWork.getNameEn());
                    userInfo.setLogo(netWork.getLogo());
                }
                User user = RequestPassport.detail(oAuth2Object.getAccessToken());
                if (user != null) {
                    userInfo.setUserId(user.getId());
                    userInfo.setCompanyName(user.getCompany());
                    userInfo.setWorkPhone(user.getWork_phone());
                    userInfo.setMobilePhone(user.getMobilePhone());
                    userInfo.setUserName(user.getName());
                    userInfo.setJobNumber(user.getJob_number());
                    userInfo.setWorkSite(user.getWork_site());
                    userInfo.setAvstar100(user.getAvstar100());
                    userInfo.setAvstar(user.getAvstar());
                    userInfo.setDepartment(user.getDepartment());
                    userInfo.setJob(user.getJob());

                    int admin = RequestApp.isAdmin(userInfo.getAccessToken(), userInfo.getUserId());
                    userInfo.setAdmin(admin == 1);

                    HrEmployee employee=this.hrEmployeeService.getByUserId(userInfo.getCompanyId(),userInfo.getUserId());
                    if(employee!=null){
                        if(employee.getJobId()!=null){
                            userInfo.setJobId(employee.getJobId().getId());
                            userInfo.setJobNumber(employee.getJobId().getName());
                        }
                        if(employee.getDutyLevel()!=null){
                            userInfo.setDutyLevel(employee.getDutyLevel().getId());
                            userInfo.setDutyLevelName(employee.getDutyLevel().getName());
                        }
                        if(employee.getOrgId()!=null){
                            userInfo.setOrgId(employee.getOrgId().getId());
                            userInfo.setOrgName(employee.getOrgId().getName());
                        }
                    }
                }
                List<Department> departmentList = RequestUser.getUserDepartment(userInfo.getAccessToken());
                if (departmentList != null && !departmentList.isEmpty()) {
                    userInfo.setDepartmentList(departmentList);
                }
                AppConfig appConfig = AppConfigUtil.create();
                if (appConfig != null) {
                    userInfo.setAppKey(appConfig.getAppKey());
                    userInfo.setAppSecret(appConfig.getAppSecret());
                    userInfo.setRedirectUri(appConfig.getRedirectUri());
                }


                userInfo.setTopMenuCss("apply");
                userInfo.setThemeType("1");
                String localeAndTheme = RequestPassport.getLocaleAndTheme(oAuth2Object.getAccessToken());
                if (StringUtils.isNotBlank(localeAndTheme)) {
                    String lt[] = localeAndTheme.split(",");
                    if (lt != null && lt.length == 2) {
                        String local = lt[0];
                        String theme = lt[1];
                        if (StringUtils.isNotBlank(local)) {
                            String locals[] = local.split("_");
                            if (locals != null && locals.length == 2) {
                                userInfo.setLanguagePreference(locals[0]);
                                userInfo.setCountryPreference(locals[1]);
                            }
                        }
                        if (StringUtils.isNotBlank(theme)) {
                            userInfo.setThemeType(theme);
                        }
                    }
                }
            }
            userInfo.setAuthorize(true);

            String domHtml = "https://api.mingdao.com/md/all.aspx??jsoncallback=?&u_key=" + userInfo.getAccessToken();
            String allHtml = HttpUtils.sendRequest(domHtml, "utf-8");
            if (StringUtils.isNotBlank(allHtml)) {
                int start = allHtml.indexOf("(");
                int end = allHtml.lastIndexOf(")");
                if (start != -1 && end != -1) {
                    allHtml = allHtml.substring(start + 1, end);
                }
                JSONObject jsonObject = JSONObject.fromObject(allHtml);
                if (jsonObject != null) {
                    String headerHtml = jsonObject.getString("header");
                    String leftHtml = jsonObject.getString("leftMenu");
                    String footerHtml = jsonObject.getString("footer");
                    userInfo.setHeaderHtml(headerHtml);
                    userInfo.setLeftHtml(leftHtml);
                    userInfo.setFooterHtml(footerHtml);
                }
            }
            if(StringUtils.isNotBlank(state)){
                if(state.equals("improve")){
                    return "improve";
                }
            }
            if(!userInfo.isAdmin()){
                return "empInfo";
            }
            return "success";
        } else {
            UserInfo userInfo = null;
            try {
                userInfo = UserSession.getUserInfo(getHttpServletRequest());
                if (userInfo != null) {
                    if (StringUtils.isNotBlank(userInfo.getAccessToken()) && StringUtils.isNotBlank(userInfo.getUserId()) && userInfo.isAuthorize()) {
                        return "success";
                    }
                }
            } catch (Exception e) {
                userInfo = null;
            }
            String mdURI = "https://api.mingdao.com/auth2/authorize";
            AppConfig appConfig = AppConfigUtil.create();
            mdURI += "?app_key=" + appConfig.getAppKey();
            mdURI += "&redirect_uri=" + URLEncoder.encode(appConfig.getRedirectUri(), "UTF-8");
            url = mdURI;
            return "login";
        }
    }

    @PageFlow(result = {@Result(name = "success", path = "/hr/employee.dhtml", type = Dispatcher.Redirect)})
    public String index() throws Exception {
        String result = "success";
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        boolean mobileDevice = HttpRequestDeviceUtils.isMobileDevice(getHttpServletRequest());
        if (mobileDevice) {
            result = "mobile";
        }
        return result;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @PageFlow(result = {@Result(name = "success", path = "https://www.mingdao.com/logout", type = Dispatcher.Redirect)})
    public String logout() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            UserSession.invalidate(getHttpServletRequest());
        }
        return "success";
    }
}
