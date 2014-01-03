package hrms.mingdao.hr.service;

import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrJob;
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
public class HrJobService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrJob getById(Long id) {
        return $(id).get(HrJob.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<HrJob> getListByCompanyId(String companyId) {
        return $($eq("companyId", companyId), $eq("useYn", "Y"),$order("name")).list(HrJob.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<HrJob> getListByCompanyId(String companyId,String name) {
        return $($eq("companyId", companyId), $eq("name", name)).list(HrJob.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public HrJob getByCompanyId(String companyId,String name) {
        return $($eq("companyId", companyId), $eq("name", name)).get(HrJob.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<String> getNameListByCompanyId(String companyId) {
        return $($eq("companyId", companyId), $eq("useYn", "Y"),$distinct("name")).list(HrJob.class,String.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Page<HrJob> getPageList(int start,
                                        int limit, List<Selector> selectorList) {
        return $(selectorList).page(HrJob.class, start, limit);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrJob hrJob) {
        $(hrJob).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<HrJob> hrJobList) {
        $(hrJobList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(HrJob hrJob) {
        $(hrJob).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(HrJob.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer validateName(String companyId,String name) {
        return $($count("id"),$eq("companyId",companyId), $eq("name", name)).value(HrJob.class, Integer.class);
    }

}
