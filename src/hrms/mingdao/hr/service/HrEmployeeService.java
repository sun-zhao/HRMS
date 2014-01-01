package hrms.mingdao.hr.service;

import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrEmployee;
import org.guiceside.commons.Page;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;

/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */
@Singleton
public class HrEmployeeService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrEmployee getById(Long id) {
        return $(id).get(HrEmployee.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Page<HrEmployee> getPageList(int start,
                                   int limit, List<Selector> selectorList) {
        return $(selectorList).page(HrEmployee.class, start, limit);
    }


    @Transactional(type = TransactionType.READ_ONLY)
    public List<HrEmployee> getListByCompany(String companyId) {
        return $($eq("companyId",companyId),$eq("useYn","Y")).list(HrEmployee.class);
    }


    @Transactional(type = TransactionType.READ_ONLY)
    public List<String> getUserIdByCompany(String companyId) {
        return $($eq("companyId",companyId),$eq("useYn","Y"),$distinct("userId")).list(HrEmployee.class,String.class);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrEmployee hrEmployee) {
        $(hrEmployee).save();
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
