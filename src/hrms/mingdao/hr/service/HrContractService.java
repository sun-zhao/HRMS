package hrms.mingdao.hr.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrContract;
import hrms.mingdao.hr.entity.HrEmployee;
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
public class HrContractService extends HQuery {

    @Inject
    private HrEmployeeService hrEmployeeService;

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrContract getById(Long id) {
        return $(id).get(HrContract.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<HrContract> getList( List<Selector> selectorList) {
        return $(selectorList).list(HrContract.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Page<HrContract> getPageList(int start,
                                        int limit, List<Selector> selectorList) {
        return $(selectorList).page(HrContract.class, start, limit);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<String> getListPyByCompanyEqContractFlag(String companyId, Date dueDate) {
        return $($alias("empId","empId"),$eq("companyId", companyId),$eq("renewalFlag", 0),$le("endDate",dueDate),$eq("useYn", "Y"), $distinct("empId.userFirstPy")).list(HrContract.class, String.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<String> getListPyByCompanyEqContractFlagOrg(String companyId, Long orgId,Date dueDate) {
        return $($alias("empId","empId"),$eq("companyId", companyId),$eq("empId.orgId.id", orgId),$eq("renewalFlag", 0),$le("endDate",dueDate),$eq("useYn", "Y"), $distinct("empId.userFirstPy")).list(HrContract.class, String.class);
    }
    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrContract hrContract) {
        $(hrContract).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrContract hrContract,HrEmployee hrEmployee) {
        $(hrContract).save();
        if(hrEmployee!=null){
            hrEmployeeService.save(hrEmployee);
        }
    }


    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<HrContract> hrContractList) {
        $(hrContractList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(HrContract hrContract) {
        $(hrContract).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(HrContract.class);
    }
}
