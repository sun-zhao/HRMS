package hrms.mingdao.hr.service;

import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrEmployee;
import hrms.mingdao.hr.entity.HrEmployeeEdu;
import hrms.mingdao.hr.entity.HrEmployeeJob;
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
public class HrEmployeeEduService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrEmployeeEdu getById(Long id) {
        return $(id).get(HrEmployeeEdu.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<HrEmployeeEdu> getListByEmpId(String companyId, Long empId) {
        return $($eq("companyId", companyId), $eq("empId.id", empId), $eq("useYn", "Y")).list(HrEmployeeEdu.class);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrEmployeeEdu hrEmployeeEdu) {
        $(hrEmployeeEdu).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<HrEmployeeEdu> employeeEduList) {
        $(employeeEduList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(HrEmployeeEdu hrEmployeeEdu) {
        $(hrEmployeeEdu).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(HrEmployeeEdu.class);
    }
}
