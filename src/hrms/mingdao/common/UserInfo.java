package hrms.mingdao.common;


import com.mingdao.api.entity.Department;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author zhenjia <a href='mailto:zhenjiaWang@gmail.com'>email</a>
 * @version 1.0 2008-10-30
 * @since JDK1.5
 */
public class UserInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_LANGUAGE_PREFERENCE = "zh";

    public static final String DEFAULT_COUNTRY_PREFERENCE = "CN";

    private String languagePreference = DEFAULT_LANGUAGE_PREFERENCE;

    private String countryPreference = DEFAULT_COUNTRY_PREFERENCE;

    public static final Locale DEFAULT_Locale = new Locale(
            DEFAULT_LANGUAGE_PREFERENCE, DEFAULT_COUNTRY_PREFERENCE);

    private boolean authorize = false;

    private boolean loggedIn = false;

    private String appKey;

    private String flag;

    private String appSecret;

    private String redirectUri;

    private String sessionId;

    private String userId;
    
    private String userName;
    
    private String department;
    
    private String job;
    
    private String companyName;
    
    private String companyNameEn;

    private String companyId;
    
    private String workSite;
    
    private String jobNumber;
    
    private String mobilePhone;
    
    private String workPhone;
    
    private String avstar100;
    
    private String avstar;
    
    private String logo;

    private String accessToken;
    
    private String topMenuCss;
    
    private String menuCss;

    private ResourceBundle zhCNResourceBundle;


    private ResourceBundle enUSResourceBundle;

    private List<Department> departmentList;

    private Locale locale;

    private String themeType;

    private boolean admin;

    private String mobileTab;
    
    private String headerHtml;
    
    private String leftHtml;
    
    private String footerHtml;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }


    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public String getCountryPreference() {
        return countryPreference;
    }

    public void setCountryPreference(String countryPreference) {
        this.countryPreference = countryPreference;
    }


    public Locale getLocale() {
        if(locale==null){
            locale=new Locale(this.getLanguagePreference(), this
                    .getCountryPreference());
            return locale;
        }else{
            return locale;
        }
    }


    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTopMenuCss() {
        return topMenuCss;
    }

    public void setTopMenuCss(String topMenuCss) {
        this.topMenuCss = topMenuCss;
    }

    public String getMenuCss() {
        return menuCss;
    }

    public void setMenuCss(String menuCss) {
        this.menuCss = menuCss;
    }

    public String getWorkSite() {
        return workSite;
    }

    public void setWorkSite(String workSite) {
        this.workSite = workSite;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAvstar100() {
        return avstar100;
    }

    public void setAvstar100(String avstar100) {
        this.avstar100 = avstar100;
    }

    public String getAvstar() {
        return avstar;
    }

    public void setAvstar(String avstar) {
        this.avstar = avstar;
    }

    public String getCompanyNameEn() {
        return companyNameEn;
    }

    public void setCompanyNameEn(String companyNameEn) {
        this.companyNameEn = companyNameEn;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public ResourceBundle getZhCNResourceBundle() {
        return zhCNResourceBundle;
    }

    public void setZhCNResourceBundle(ResourceBundle zhCNResourceBundle) {
        this.zhCNResourceBundle = zhCNResourceBundle;
    }

    public ResourceBundle getEnUSResourceBundle() {
        return enUSResourceBundle;
    }

    public void setEnUSResourceBundle(ResourceBundle enUSResourceBundle) {
        this.enUSResourceBundle = enUSResourceBundle;
    }



    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getThemeType() {
        return themeType;
    }

    public void setThemeType(String themeType) {
        this.themeType = themeType;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getMobileTab() {
        return mobileTab;
    }

    public void setMobileTab(String mobileTab) {
        this.mobileTab = mobileTab;
    }

    public String getHeaderHtml() {
        return headerHtml;
    }

    public void setHeaderHtml(String headerHtml) {
        this.headerHtml = headerHtml;
    }

    public String getLeftHtml() {
        return leftHtml;
    }

    public void setLeftHtml(String leftHtml) {
        this.leftHtml = leftHtml;
    }

    public String getFooterHtml() {
        return footerHtml;
    }

    public void setFooterHtml(String footerHtml) {
        this.footerHtml = footerHtml;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
