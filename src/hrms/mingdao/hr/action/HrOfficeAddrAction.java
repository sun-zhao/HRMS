package hrms.mingdao.hr.action;

import com.google.inject.Inject;
import com.mingdao.api.entity.Department;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.hr.entity.HrJob;
import hrms.mingdao.hr.entity.HrOfficeAddr;
import hrms.mingdao.hr.service.HrEmployeeService;
import hrms.mingdao.hr.service.HrJobService;
import hrms.mingdao.hr.service.HrOfficeAddrService;
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
@Action(name = "officeAddr", namespace = "/hr")
public class HrOfficeAddrAction extends ActionSupport<HrOfficeAddr> {

    @Inject
    private HrOfficeAddrService hrOfficeAddrService;

    @Inject
    private HrEmployeeService hrEmployeeService;


    @ReqGet
    @ModelDriver
    @ReqSet
    private HrOfficeAddr hrOfficeAddr;

    @ReqSet
    @ReqGet
    private String address;

    @ReqSet
    @ReqGet
    private Long id;

    @ReqSet
    private List<HrOfficeAddr> officeAddrList;


    @ReqSet
    private Integer maxOrder;

    private List<Selector> searchModeCallback() throws Exception {
        List<Selector> selectorList = new ArrayList<Selector>();
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            userInfo.setTopMenuCss("AppSet");
            userInfo.setMenuCss("address");
            String companyId = userInfo.getCompanyId();
            if (StringUtils.isNotBlank(companyId)) {
                selectorList.add(SelectorUtils.$eq("companyId", companyId));
                if (StringUtils.isNotBlank(address)) {
                    selectorList.add(SelectorUtils.$like("address", address));
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
            @Result(name = "success", path = "/view/hr/officeAddr/list.ftl", type = Dispatcher.FreeMarker)})
    public String execute() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            pageObj = this.hrOfficeAddrService.getPageList(getStart(), 14, searchModeCallback());
            if (pageObj != null) {
                officeAddrList = pageObj.getResultList();
                maxOrder=this.hrOfficeAddrService.getMaxDisplayOrder(userInfo.getCompanyId());
                if (maxOrder == null) {
                    maxOrder = 0;
                }
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/officeAddr.dhtml", type = Dispatcher.Redirect)})
    public String save() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && hrOfficeAddr != null) {
            if (hrOfficeAddr.getId() != null) {
                HrOfficeAddr old = this.hrOfficeAddrService.getById(hrOfficeAddr.getId());
                old.setAddress(hrOfficeAddr.getAddress());
                bind(old);
                hrOfficeAddrService.save(old);
            } else {
                maxOrder = this.hrOfficeAddrService.getMaxDisplayOrder(userInfo.getCompanyId());
                if (maxOrder == null) {
                    maxOrder = 0;
                }
                hrOfficeAddr.setDisplayOrder(maxOrder + 1);
                hrOfficeAddr.setCompanyId(userInfo.getCompanyId());
                hrOfficeAddr.setUseYn("Y");
                bind(hrOfficeAddr);
                hrOfficeAddrService.save(hrOfficeAddr);
            }
        }
        return "success";
    }

    public String getAddress() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrOfficeAddr = this.hrOfficeAddrService.getById(id);
            if (hrOfficeAddr != null) {
                root.put("id", hrOfficeAddr.getId());
                root.put("address", hrOfficeAddr.getAddress());
                root.put("result", "0");
            }
        }
        writeJsonByAction(root.toString());
        return null;
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/officeAddr.dhtml", type = Dispatcher.Redirect)})
    public String delete() throws Exception {
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null && id != null) {
            hrOfficeAddr = this.hrOfficeAddrService.getById(id);
            if (hrOfficeAddr != null) {
                this.hrOfficeAddrService.delete(hrOfficeAddr);
            }
        }
        return "success";
    }

    public String validateAddress() throws Exception {
        JSONObject item = new JSONObject();
        item.put("result", "-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
                if (StringUtils.isNotBlank(address)) {
                    String ignore = getParameter("ignore");
                    if (StringUtils.isNotBlank(ignore)) {
                        if (ignore.equals(address)) {
                            item.put("result", "0");
                            writeJsonByAction(item.toString());
                        } else {
                            Integer row = this.hrOfficeAddrService.validateName( userInfo.getCompanyId(),address);
                            if (row.intValue() == 0) {
                                item.put("result", "0");
                            }
                        }
                    } else {
                        Integer row = this.hrOfficeAddrService.validateName( userInfo.getCompanyId(),address);
                        if (row.intValue() == 0) {
                            item.put("result", "0");
                        }
                    }
                }
            }
        writeJsonByAction(item.toString());
        return null;
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/officeAddr.dhtml", type = Dispatcher.Redirect)})
    public String up() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        if (id != null) {
            hrOfficeAddr = this.hrOfficeAddrService.getById(id);
            if (hrOfficeAddr != null) {
                this.hrOfficeAddrService.up(hrOfficeAddr);
            }
        }
        return "success";
    }

    @PageFlow(result = {
            @Result(name = "success", path = "/hr/officeAddr.dhtml", type = Dispatcher.Redirect)})
    public String down() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        if (id != null) {
            hrOfficeAddr = this.hrOfficeAddrService.getById(id);
            if (hrOfficeAddr != null) {
                this.hrOfficeAddrService.down(hrOfficeAddr);
            }
        }
        return "success";
    }
}
