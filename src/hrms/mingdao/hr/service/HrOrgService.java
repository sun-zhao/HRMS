package hrms.mingdao.hr.service;

import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrOrg;
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
public class HrOrgService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrOrg getById(Long id) {
        return $(id).get(HrOrg.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<HrOrg> getListByCompanyId(String companyId) {
        return $($eq("companyId", companyId), $eq("useYn", "Y"), $order("displayOrder")).list(HrOrg.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public HrOrg getByCompanyId(String companyId, String name) {
        return $($eq("companyId", companyId), $eq("name", name)).get(HrOrg.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Page<HrOrg> getPageList(int start,
                                   int limit, List<Selector> selectorList) {
        return $(selectorList).page(HrOrg.class, start, limit);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrOrg hrOrg) {
        $(hrOrg).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<HrOrg> orgList) {
        $(orgList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(HrOrg hrOrg) {
        $(hrOrg).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(HrOrg.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getMaxDisplayOrder(String companyId) {
        return $($eq("companyId", companyId), $eq("useYn", "Y"), $max("displayOrder")).value(HrOrg.class, Integer.class);
    }


    @Transactional(type = TransactionType.READ_ONLY)
    public HrOrg getByOrder(String companyId, Integer order) {
        return $($eq("companyId", companyId), $eq("displayOrder", order)).get(HrOrg.class);
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void up(HrOrg hrOrg) {
        HrOrg upObj = this.getByOrder(hrOrg.getCompanyId(), hrOrg.getDisplayOrder() - 1);
        upObj.setDisplayOrder(upObj.getDisplayOrder() + 1);
        $(upObj).save();
        hrOrg.setDisplayOrder(hrOrg.getDisplayOrder() - 1);
        $(hrOrg).save();
    }


    @Transactional(type = TransactionType.READ_WRITE)
    public void down(HrOrg hrOrg) {
        HrOrg downObj = this.getByOrder(hrOrg.getCompanyId(), hrOrg.getDisplayOrder() + 1);
        downObj.setDisplayOrder(downObj.getDisplayOrder() - 1);
        $(downObj).save();
        hrOrg.setDisplayOrder(hrOrg.getDisplayOrder() + 1);
        $(hrOrg).save();
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer validateName(String companyId, String name) {
        return $($count("id"), $eq("companyId", companyId), $eq("name", name)).value(HrOrg.class, Integer.class);
    }
}
