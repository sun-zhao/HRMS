package hrms.mingdao.hr.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrContract;
import hrms.mingdao.hr.entity.HrContractTemplate;
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
public class HrContractTemplateService extends HQuery {

    @Inject
    private HrEmployeeService hrEmployeeService;

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrContractTemplate getById(Long id) {
        return $(id).get(HrContractTemplate.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Page<HrContractTemplate> getPageList(int start,
                                   int limit, List<Selector> selectorList) {
        return $(selectorList).page(HrContractTemplate.class, start, limit);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<HrContractTemplate> getListByCompanyId(String companyId) {
        return $($eq("companyId",companyId)).list(HrContractTemplate.class);
    }
    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrContractTemplate hrContractTemplate) {
        $(hrContractTemplate).save();
    }


    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<HrContractTemplate> hrContractTemplateList) {
        $(hrContractTemplateList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(HrContractTemplate hrContractTemplate) {
        $(hrContractTemplate).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(HrContractTemplate.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer validateName(String companyId,String name) {
        return $($count("id"),$eq("companyId",companyId), $eq("name", name)).value(HrContractTemplate.class, Integer.class);
    }
}
