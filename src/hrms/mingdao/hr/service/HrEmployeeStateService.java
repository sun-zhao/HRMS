package hrms.mingdao.hr.service;

import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrEmployeeState;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;

import java.util.List;

/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */
@Singleton
public class HrEmployeeStateService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrEmployeeState getById(Long id) {
        return $(id).get(HrEmployeeState.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public HrEmployeeState getByEmpId(String companyId, Long empId) {
        return $($eq("companyId", companyId), $eq("empId.id", empId), $eq("useYn", "Y")).get(HrEmployeeState.class);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrEmployeeState hrEmployeeState) {
        $(hrEmployeeState).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<HrEmployeeState> employeeStateList) {
        $(employeeStateList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(HrEmployeeState hrEmployeeState) {
        $(hrEmployeeState).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(HrEmployeeState.class);
    }
}
