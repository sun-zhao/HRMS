package hrms.mingdao.hr.entity;

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
@Table(name = "HR_EMPLOYEE_FAMILY")
public class HrEmployeeFamily extends IdEntity implements Tracker {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String companyId;

    private HrEmployee empId;

    private Integer familyRelation;

    private String familyName;

    private String familyTel;

    private String familyAddress;

    private String residenceBooklet;

    private Integer residenceBookletType;

    private Integer marry;

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

    @Column(name = "FAMILY_RELATION")
    public Integer getFamilyRelation() {
        return familyRelation;
    }

    public void setFamilyRelation(Integer familyRelation) {
        this.familyRelation = familyRelation;
    }

    @Column(name = "FAMILY_NAME")
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Column(name = "FAMILY_TEL")
    public String getFamilyTel() {
        return familyTel;
    }

    public void setFamilyTel(String familyTel) {
        this.familyTel = familyTel;
    }

    @Column(name = "FAMILY_ADDRESS")
    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    @Column(name = "RESIDENCE_BOOKLET")
    public String getResidenceBooklet() {
        return residenceBooklet;
    }

    public void setResidenceBooklet(String residenceBooklet) {
        this.residenceBooklet = residenceBooklet;
    }

    @Column(name = "RESIDENCE_BOOKLET_TYPE")
    public Integer getResidenceBookletType() {
        return residenceBookletType;
    }

    public void setResidenceBookletType(Integer residenceBookletType) {
        this.residenceBookletType = residenceBookletType;
    }

    @Column(name = "MARRY")
    public Integer getMarry() {
        return marry;
    }

    public void setMarry(Integer marry) {
        this.marry = marry;
    }
}