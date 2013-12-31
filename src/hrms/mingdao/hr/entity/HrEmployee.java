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

    private SysCode contryId;

    private SysCode nationalityId;

    private SysProvince provinceId;

    private SysCity cityId;

    private SysCode  politicsLevelId;

    private String idCard;

    private String bankCard;

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
    @JoinColumn(name = "POLITICS_LEVEL_ID")
    public SysCode getPoliticsLevelId() {
        return politicsLevelId;
    }

    public void setPoliticsLevelId(SysCode politicsLevelId) {
        this.politicsLevelId = politicsLevelId;
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
}