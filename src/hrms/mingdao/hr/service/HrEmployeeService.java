package hrms.mingdao.hr.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrEmployee;
import hrms.mingdao.hr.entity.HrEmployeeEdu;
import hrms.mingdao.hr.entity.HrEmployeeJob;
import org.guiceside.commons.Page;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.Date;
import java.util.List;

/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */
@Singleton
public class HrEmployeeService extends HQuery {

    @Inject
    private HrEmployeeEduService hrEmployeeEduService;

    @Inject
    private HrEmployeeJobService hrEmployeeJobService;

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrEmployee getById(Long id) {
        return $(id).get(HrEmployee.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public HrEmployee getByUserId(String companyId, String userId) {
        return $($eq("companyId", companyId), $eq("userId", userId), $eq("useYn", "Y")).get(HrEmployee.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Page<HrEmployee> getPageList(int start,
                                        int limit, List<Selector> selectorList) {
        return $(selectorList).page(HrEmployee.class, start, limit);
    }
    @Transactional(type = TransactionType.READ_ONLY)
    public List<HrEmployee> getList(List<Selector> selectorList) {
        return $(selectorList).list(HrEmployee.class);
    }


    @Transactional(type = TransactionType.READ_ONLY)
    public List<HrEmployee> getListByCompany(String companyId) {
        return $($eq("companyId", companyId), $eq("useYn", "Y")).list(HrEmployee.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<String> getListPyByCompanyEqContractFlag(String companyId, Integer contractFlag) {
        return $($eq("companyId", companyId),$eq("complete",0),$eq("contractFlag",contractFlag), $eq("useYn", "Y"), $distinct("userFirstPy")).list(HrEmployee.class, String.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<String> getListPyByCompanyEqContractFlag(String companyId, Integer contractFlag,Date dueDate) {
        return $($eq("companyId", companyId),$eq("complete",0),$eq("contractFlag",contractFlag), $le("endDate",dueDate),$eq("useYn", "Y"), $distinct("userFirstPy")).list(HrEmployee.class, String.class);
    }


    @Transactional(type = TransactionType.READ_ONLY)
    public List<String> getListPyByCompanyEqComplete(String companyId, Integer complete) {
        return $($eq("companyId", companyId),$eq("complete",complete), $eq("useYn", "Y"), $distinct("userFirstPy")).list(HrEmployee.class, String.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<String> getListPyByCompanyGtComplete(String companyId, Integer complete) {
        return $($eq("companyId", companyId),$gt("complete",complete), $eq("useYn", "Y"), $distinct("userFirstPy")).list(HrEmployee.class, String.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<String> getListPyByCompanyNotEqComplete(String companyId, Integer complete) {
        return $($eq("companyId", companyId),$not($eq("complete",complete)),$eq("useYn", "Y"), $distinct("userFirstPy")).list(HrEmployee.class, String.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getCountByCompanyEqComplete(String companyId, Integer complete) {
        return $($eq("companyId", companyId),$eq("complete",complete),$eq("useYn", "Y"),$count("id")).value(HrEmployee.class, Integer.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<String> getUserIdByCompany(String companyId) {
        return $($eq("companyId", companyId), $eq("useYn", "Y"), $distinct("userId")).list(HrEmployee.class, String.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getCountByCompanyJob(String companyId, Long jobId) {
        return $($eq("companyId", companyId),$eq("jobId.id",jobId),$count("id")).value(HrEmployee.class, Integer.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getCountByCompanyDutyLevel(String companyId, Long dutyLevelId) {
        return $($eq("companyId", companyId),$eq("dutyLevel.id",dutyLevelId),$count("id")).value(HrEmployee.class, Integer.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getCountByCompanyOrg(String companyId, Long orgId) {
        return $($eq("companyId", companyId),$eq("orgId.id",orgId),$count("id")).value(HrEmployee.class, Integer.class);
    }
    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrEmployee hrEmployee) {
        $(hrEmployee).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrEmployee hrEmployee, List<HrEmployeeEdu> employeeEduList, List<HrEmployeeJob> employeeJobList) {
        $(hrEmployee).save();
        if (employeeEduList != null && !employeeEduList.isEmpty()) {
            this.hrEmployeeEduService.save(employeeEduList);
        }
        if (employeeJobList != null && !employeeJobList.isEmpty()) {
            this.hrEmployeeJobService.save(employeeJobList);
        }
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<HrEmployee> hrEmployeeList) {
        $(hrEmployeeList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(HrEmployee hrEmployee) {
        $(hrEmployee).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(HrEmployee.class);
    }
}
