package hrms.mingdao.hr.entity;

import hrms.mingdao.sys.entity.SysCity;
import hrms.mingdao.sys.entity.SysCode;
import hrms.mingdao.sys.entity.SysProvince;
import org.guiceside.persistence.entity.IdEntity;
import org.guiceside.persistence.entity.Tracker;

import javax.persistence.*;
import java.util.Date;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */
@Entity
@Table(name = "HR_EMPLOYEE")
public class HrEmployee extends IdEntity implements Tracker {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String companyId;


    private String userId;

    private String userName;

    private String deptName;

    private Integer dutyLevel;

    private String jobName;

    private Integer userSex;

    private String mobileTel;

    private String officeTel;

    private String userEmail;

    private String officeAddress;

    private Date entryDate;

    private Date birthDay;

    private String userFirstPy;

    private SysCode contryId;

    private SysCode eduLevel;

    private SysCode nationalityId;

    private SysProvince provinceId;

    private SysCity cityId;

    private SysCode  politicsLevel;

    private String idCard;

    private String bankCard;

    private Integer complete;

    private Integer workState;

    private Integer empType;

    private Integer contractFlag;

    private String completeMessageId;

    private Date created;

    private String createdBy;

    private Date updated;

    private String updatedBy;

    private String useYn;


    @Column(name = "CREATED", updatable = false)
    public Date getCreated() {
        return created;
    }

    @Column(name = "CREATED_BY", updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    @Column(name = "UPDATED")
    public Date getUpdated() {
        return updated;
    }

    @Column(name = "UPDATED_BY")
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Column(name = "USE_YN")
    public String getUseYn() {
        return useYn;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "COMPANY_ID")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRY_ID")
    public SysCode getContryId() {
        return contryId;
    }

    public void setContryId(SysCode contryId) {
        this.contryId = contryId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NATIONALITY_ID")
    public SysCode getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(SysCode nationalityId) {
        this.nationalityId = nationalityId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVINCE_ID")
    public SysProvince getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(SysProvince provinceId) {
        this.provinceId = provinceId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_ID")
    public SysCity getCityId() {
        return cityId;
    }

    public void setCityId(SysCity cityId) {
        this.cityId = cityId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POLITICS_LEVEL")
    public SysCode getPoliticsLevel() {
        return politicsLevel;
    }

    public void setPoliticsLevel(SysCode politicsLevel) {
        this.politicsLevel = politicsLevel;
    }

    @Column(name = "ID_CARD")
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Column(name = "BANK_CARD")
    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    @Column(name = "USER_FIRST_PY")
    public String getUserFirstPy() {
        return userFirstPy;
    }

    public void setUserFirstPy(String userFirstPy) {
        this.userFirstPy = userFirstPy;
    }

    @Column(name = "COMPLETE")
    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "COMPLETE_MESSAGE_ID")
    public String getCompleteMessageId() {
        return completeMessageId;
    }

    public void setCompleteMessageId(String completeMessageId) {
        this.completeMessageId = completeMessageId;
    }

    @Column(name = "DEPT_NAME")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Column(name = "DUTY_LEVEL")
    public Integer getDutyLevel() {
        return dutyLevel;
    }

    public void setDutyLevel(Integer dutyLevel) {
        this.dutyLevel = dutyLevel;
    }

    @Column(name = "JOB_NAME")
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Column(name = "USER_SEX")
    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    @Column(name = "MOBILE_TEL")
    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    @Column(name = "OFFICE_TEL")
    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    @Column(name = "USER_EMAIL")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Column(name = "OFFICE_ADDRESS")
    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    @Column(name = "ENTRY_DATE")
    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    @Column(name = "BIRTHDAY")
    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDU_LEVEL")
    public SysCode getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(SysCode eduLevel) {
        this.eduLevel = eduLevel;
    }

    @Column(name = "WORK_STATE")
    public Integer getWorkState() {
        return workState;
    }

    public void setWorkState(Integer workState) {
        this.workState = workState;
    }

    @Column(name = "EMP_TYPE")
    public Integer getEmpType() {
        return empType;
    }

    public void setEmpType(Integer empType) {
        this.empType = empType;
    }

    @Column(name = "CONTRACT_FLAG")
    public Integer getContractFlag() {
        return contractFlag;
    }

    public void setContractFlag(Integer contractFlag) {
        this.contractFlag = contractFlag;
    }
}