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
@Table(name = "HR_CONTRACT_TYPE")
public class HrContractType extends IdEntity implements Tracker {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String companyId;

    private Integer staticRemindDay;

    private Integer staticValidMonth;

    private Integer staticProbation;


    private Integer probation;

    private Integer remindDay;

    private Integer validMonth;

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


    @Column(name = "REMIND_DAY")
    public Integer getRemindDay() {
        return remindDay;
    }

    public void setRemindDay(Integer remindDay) {
        this.remindDay = remindDay;
    }

    @Column(name = "VALID_MONTH")
    public Integer getValidMonth() {
        return validMonth;
    }

    public void setValidMonth(Integer validMonth) {
        this.validMonth = validMonth;
    }

    @Column(name = "STATIC_REMIND_DAY")
    public Integer getStaticRemindDay() {
        return staticRemindDay;
    }

    public void setStaticRemindDay(Integer staticRemindDay) {
        this.staticRemindDay = staticRemindDay;
    }

    @Column(name = "STATIC_VALID_MONTH")
    public Integer getStaticValidMonth() {
        return staticValidMonth;
    }

    public void setStaticValidMonth(Integer staticValidMonth) {
        this.staticValidMonth = staticValidMonth;
    }

    @Column(name = "STATIC_PROBATION")
    public Integer getStaticProbation() {
        return staticProbation;
    }

    public void setStaticProbation(Integer staticProbation) {
        this.staticProbation = staticProbation;
    }

    @Column(name = "PROBATION")
    public Integer getProbation() {
        return probation;
    }

    public void setProbation(Integer probation) {
        this.probation = probation;
    }
}