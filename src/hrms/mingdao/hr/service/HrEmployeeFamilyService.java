package hrms.mingdao.hr.service;

import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrEmployeeEdu;
import hrms.mingdao.hr.entity.HrEmployeeFamily;
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
public class HrEmployeeFamilyService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrEmployeeFamily getById(Long id) {
        return $(id).get(HrEmployeeFamily.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public HrEmployeeFamily getByEmpId(String companyId, Long empId) {
        return $($eq("companyId", companyId), $eq("empId.id", empId), $eq("useYn", "Y")).get(HrEmployeeFamily.class);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrEmployeeFamily hrEmployeeFamily) {
        $(hrEmployeeFamily).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<HrEmployeeFamily> employeeFamilyList) {
        $(employeeFamilyList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(HrEmployeeFamily hrEmployeeFamily) {
        $(hrEmployeeFamily).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(HrEmployeeFamily.class);
    }
}
