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
@Table(name = "HR_CONTRACT")
public class HrContract extends IdEntity implements Tracker {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String companyId;

    private HrEmployee empId;

    private Integer contractType;

    private String contractNo;

    private Date startDate;

    private Date endDate;

    private Integer renewalFlag;

    private Integer contractState;

    private String workArea;

    private String insuranceArea;

    private String insuranceType;

    private Double probationPay;

    private Double pay;

    private String workTime;

    private HrContractTemplate templateId;

    private String remarks;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMP_ID")
    public HrEmployee getEmpId() {
        return empId;
    }

    public void setEmpId(HrEmployee empId) {
        this.empId = empId;
    }

    @Column(name = "CONTRACT_TYPE")
    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    @Column(name = "CONTRACT_NO")
    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    @Column(name = "START_DATE")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "CONTRACT_STATE")
    public Integer getContractState() {
        return contractState;
    }

    public void setContractState(Integer contractState) {
        this.contractState = contractState;
    }

    @Column(name = "REMARKS")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "RENEWAL_FLAG")
    public Integer getRenewalFlag() {
        return renewalFlag;
    }

    public void setRenewalFlag(Integer renewalFlag) {
        this.renewalFlag = renewalFlag;
    }

    @Column(name = "WORK_AREA")
    public String getWorkArea() {
        return workArea;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }

    @Column(name = "INSURANCE_AREA")
    public String getInsuranceArea() {
        return insuranceArea;
    }

    public void setInsuranceArea(String insuranceArea) {
        this.insuranceArea = insuranceArea;
    }

    @Column(name = "INSURANCE_TYPE")
    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    @Column(name = "PROBATION_PAY")
    public Double getProbationPay() {
        return probationPay;
    }

    public void setProbationPay(Double probationPay) {
        this.probationPay = probationPay;
    }

    @Column(name = "PAY")
    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    @Column(name = "WORK_TIME")
    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_ID")
    public HrContractTemplate getTemplateId() {
        return templateId;
    }

    public void setTemplateId(HrContractTemplate templateId) {
        this.templateId = templateId;
    }
}