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
@Table(name = "HR_EMPLOYEE_STATE")
public class HrEmployeeState extends IdEntity implements Tracker {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String companyId;

    private HrEmployee empId;

   private Integer workState;

    private Integer empType;

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

    @Column(name = "WORK_SATTE")
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
}