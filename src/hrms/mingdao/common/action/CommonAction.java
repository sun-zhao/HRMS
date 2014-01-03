package hrms.mingdao.common.action;

import com.google.inject.Inject;
import com.mingdao.api.entity.User;
import com.mingdao.api.user.RequestUser;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import hrms.mingdao.common.UserInfo;
import hrms.mingdao.common.UserSession;
import hrms.mingdao.common.entity.TempAtt;
import hrms.mingdao.common.service.TempAttService;
import hrms.mingdao.sys.entity.SysCity;
import hrms.mingdao.sys.service.SysCityService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ognl.NoSuchPropertyException;
import org.guiceside.commons.FileIdUtils;
import org.guiceside.commons.FileObject;
import org.guiceside.commons.Page;
import org.guiceside.commons.PageUtils;
import org.guiceside.commons.lang.BeanUtils;
import org.guiceside.commons.lang.DateFormatUtil;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.entity.Tracker;
import org.guiceside.support.file.FileManager;
import org.guiceside.support.limit.ListLimit;
import org.guiceside.support.upload.FileUploadManager;
import org.guiceside.web.action.BaseAction;
import org.guiceside.web.annotation.Action;
import org.guiceside.web.annotation.ReqGet;
import org.guiceside.web.annotation.ReqSet;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhenjiaWang
 * Date: 12-7-12
 * Time: 下午9:49
 * To change this template use File | Settings | File Templates.
 */
@Action(name = "common", namespace = "/common")
public class CommonAction extends BaseAction {

    @Inject
    private TempAttService tempAttService;

    @Inject
    private SysCityService sysCityService;

    @ReqGet
    private String keywords;

    @ReqGet
    private String notMe;

    @ReqGet
    private String variableType;

    @ReqGet
    @ReqSet
    private Integer currentPage;

    @ReqGet
    private String exIds;

    @ReqGet
    private String nowApproval;

    @ReqGet
    @ReqSet
    private String attKey;

    @ReqGet
    @ReqSet
    private String attToken;

    @ReqGet
    private String fileNames;


    @ReqGet
    @ReqSet
    private Long  provinceId;


    public String searchUser() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null) {
            String accessToken = userInfo.getAccessToken();
            if (StringUtils.isNotBlank(accessToken)) {
                List<User> userList = RequestUser.getUserSearch(accessToken, keywords, null, null);
                if (userList != null && !userList.isEmpty()) {
                    ListLimit<User> userListLimit = new ListLimit<User>(userList);
                    if (currentPage == null) {
                        currentPage = 1;
                    }
                    Page page = PageUtils.createPage(10, currentPage, userList.size());
                    if (page != null) {
                        List<User> userPageList = userListLimit.getResult(page.getBeginIndex(), 10);
                        if (userPageList != null && !userPageList.isEmpty()) {
                            root.put("result", "0");
                            JSONArray jsonArray = new JSONArray();
                            boolean flag = true;
                            for (User user : userPageList) {
                                flag = true;
                                if (StringUtils.isNotBlank(notMe)) {
                                    if (userInfo.getUserId().equals(user.getId())) {
                                        flag = false;
                                    }
                                }
                                if (flag) {
                                    JSONObject userObject = new JSONObject();
                                    userObject.put("userId", user.getId());
                                    userObject.put("userName", user.getName());
                                    userObject.put("deptName", user.getDepartment());
                                    userObject.put("jobName", user.getJob());
                                    userObject.put("avStar100", user.getAvstar100());
                                    jsonArray.add(userObject);
                                }
                            }
                            root.put("userList", jsonArray);
                        }
                    }
                }else{
                    root.put("result", "0");
                    root.put("userList", null);
                }
            }
        }
        writeJsonByAction(root.toString());
        return null;
    }



    @Override
    public String execute() throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String cityList() throws Exception {
        JSONObject root = new JSONObject();
        root.put("result", "-1");
        UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
        if (userInfo != null&&provinceId!=null) {
            List<SysCity> cityList=this.sysCityService.getListByProvince(provinceId);
            if(cityList!=null&&!cityList.isEmpty()){
                JSONArray cityArray=new JSONArray();
                for(SysCity city:cityList){
                    JSONObject object=new JSONObject();
                    object.put("id",city.getId());
                    object.put("name",city.getName());
                    cityArray.add(object);
                }
                root.put("cityList", cityArray);
            }
            root.put("result", "0");
        }
        writeJsonByAction(root.toString());
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public String upload() throws Exception {
        if (StringUtils.isNotBlank(attKey) && StringUtils.isNotBlank(attToken)) {
            /*获得当前的用户*/
            UserInfo userInfo = UserSession.getUserInfo(getHttpServletRequest());
            if (userInfo != null) {
                Config.ACCESS_KEY = "He6K4qzOV8pDNuyToCXh7NTy7bbfJ12XIjUhToG_";
                Config.SECRET_KEY = "gQybrcc5rnoCCWsNGwpTVN0t6n1FsuMcBXvSviXd";
                Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
                // 请确保该bucket已经存在
                String bucketName = "approval";
                PutPolicy putPolicy = new PutPolicy(bucketName);

                String uptoken = putPolicy.token(mac);

                /*保存上传的文件*/
                try {
                    FileUploadManager manager = new FileUploadManager(getRootPath());
                    List<FileObject> fileItems = manager.getFiles(this
                            .getHttpServletRequest());
                    Date cud = DateFormatUtil.getCurrentDate(false);
                    if (fileItems != null && !fileItems.isEmpty()) {
                        for (FileObject fileObject : fileItems) {
                            TempAtt att = new TempAtt();
                            int year = DateFormatUtil.getDayInYear(cud);
                            int month = DateFormatUtil.getDayInMonth(cud) + 1;
                            int day = DateFormatUtil.getDayInDay(cud);
                            att.setYear(year);
                            att.setMonth(month);
                            att.setDay(day);
                            att.setTokenId(attToken);
                            att.setOldName(fileObject.getFileName());
                            att.setNewName(FileIdUtils.getFileUnId());
                            String key = userInfo.getCompanyId()+"/"+attKey+"/"+att.getNewName();
                            att.setFileKey(key);
                            // 可选的上传选项，具体说明请参见使用手册。
                            PutExtra extra = new PutExtra();

                            String fileItemStr=fileObject.getFileItem().toString();
                            String fileSrc=null;
                            if(StringUtils.isNotBlank(fileItemStr)){
                                String items[]=fileItemStr.split(",");
                                if(items!=null&&items.length>0){
                                    for(String it:items){
                                        int index=it.indexOf("StoreLocation");
                                        if(index!=-1){
                                            fileSrc=it.substring(index+"StoreLocation".length()+1);
                                            if(StringUtils.isNotBlank(fileSrc)){
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if(StringUtils.isNotBlank(fileSrc)){
                                // 上传文件
                                PutRet ret = IoApi.putFile(uptoken, key, new File(fileSrc), extra);
                                if (ret.ok()) {
                                    String postfix = fileObject.getPostfix();
                                    postfix = postfix.toLowerCase();
                                    att.setPostfix(postfix);
                                    att.setSize(fileObject.getSize());
                                    att.setAttKey(attKey);
                                    att.setSource(fileSrc);
                                    att.setUseYn("Y");
                                    bind(att, userInfo);
                                    att.setUserId(userInfo.getUserId());
                                    att.setCompanyId(userInfo.getCompanyId());
                                    tempAttService.save(att);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String remove() throws Exception {
        JSONObject root = new JSONObject();
        root.put("success", false);
        if (StringUtils.isNotBlank(fileNames) && StringUtils.isNotBlank(attKey) && StringUtils.isNotBlank(attToken)) {
            try {
                int last = fileNames.lastIndexOf(".");
                String oldName = fileNames.substring(0, last);
                String postfix = fileNames.substring(last + 1);
                if (StringUtils.isNotBlank(oldName) && StringUtils.isNotBlank(postfix)) {
                    List<TempAtt> tempAttList = tempAttService.getByFileName(attKey, attToken, oldName, postfix);
                    if(tempAttList!=null&&!tempAttList.isEmpty()){
                        for(TempAtt tempAtt:tempAttList){
                            if (tempAtt != null) {
                                if (StringUtils.isNotBlank(tempAtt.getSource())) {
                                    FileManager.deleteFile(tempAtt.getSource());
                                }
                                Config.ACCESS_KEY = "He6K4qzOV8pDNuyToCXh7NTy7bbfJ12XIjUhToG_";
                                Config.SECRET_KEY = "gQybrcc5rnoCCWsNGwpTVN0t6n1FsuMcBXvSviXd";
                                Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
                                RSClient clientR = new RSClient(mac);
                                clientR.delete("approval",tempAtt.getFileKey());
                                tempAttService.delete(tempAtt);
                            }
                        }
                    }
                }
                root.put("success", true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writeJsonByAction(root.toString());
        return null;
    }

    protected void bind(Object entity,UserInfo userInfo) throws Exception {
        if (entity instanceof Tracker) {
            if (BeanUtils.getValue(entity, "id") == null) {
                BeanUtils.setValue(entity, "created", DateFormatUtil.getCurrentDate(true));
            }
            BeanUtils.setValue(entity, "updated", DateFormatUtil.getCurrentDate(true));
            if(userInfo!=null){
                BeanUtils.setValue(entity, "createdBy", userInfo.getUserId());
                BeanUtils.setValue(entity, "updatedBy", userInfo.getUserId());
            }
        }
        try {
            String useYn = BeanUtils.getValue(entity, "useYn", String.class);
            if (StringUtils.isBlank(useYn)) {
                BeanUtils.setValue(entity, "useYn", "N");
            }
        } catch (NoSuchPropertyException e) {
            BeanUtils.setValue(entity, "useYn", "N");
        }
    }
}
