package hrms.mingdao.hr.service;

import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrContractType;
import hrms.mingdao.hr.entity.HrEmployeeEdu;
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
public class HrContractTypeService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrContractType getById(Long id) {
        return $(id).get(HrContractType.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public HrContractType getListByCompanyId(String companyId) {
        return $($eq("companyId", companyId), $eq("useYn", "Y")).get(HrContractType.class);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrContractType hrContractType) {
        $(hrContractType).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<HrContractType> hrContractTypeList) {
        $(hrContractTypeList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(HrContractType hrContractType) {
        $(hrContractType).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(HrContractType.class);
    }
}
