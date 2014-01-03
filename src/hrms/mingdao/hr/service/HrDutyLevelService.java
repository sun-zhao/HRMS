package hrms.mingdao.hr.service;

import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrContractType;
import hrms.mingdao.hr.entity.HrDutyLevel;
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
public class HrDutyLevelService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrDutyLevel getById(Long id) {
        return $(id).get(HrDutyLevel.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public HrDutyLevel getListByCompanyId(String companyId) {
        return $($eq("companyId", companyId), $eq("useYn", "Y"),$order("displayOrder")).get(HrDutyLevel.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Page<HrDutyLevel> getPageList(int start,
                                        int limit, List<Selector> selectorList) {
        return $(selectorList).page(HrDutyLevel.class, start, limit);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrDutyLevel hrDutyLevel) {
        $(hrDutyLevel).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<HrDutyLevel> hrDutyLevelList) {
        $(hrDutyLevelList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(HrDutyLevel hrDutyLevel) {
        $(hrDutyLevel).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(HrDutyLevel.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getMaxDisplayOrder(String companyId) {
        return $($eq("companyId", companyId), $eq("useYn", "Y"), $max("displayOrder")).value(HrDutyLevel.class, Integer.class);
    }



    @Transactional(type = TransactionType.READ_ONLY)
    public HrDutyLevel getByOrder(String companyId, Integer order) {
        return $($eq("companyId", companyId), $eq("displayOrder", order)).get(HrDutyLevel.class);
    }
    @Transactional(type = TransactionType.READ_WRITE)
    public void up(HrDutyLevel hrDutyLevel) {
        HrDutyLevel upObj = this.getByOrder(hrDutyLevel.getCompanyId(), hrDutyLevel.getDisplayOrder() - 1);
        upObj.setDisplayOrder(upObj.getDisplayOrder() + 1);
        $(upObj).save();
        hrDutyLevel.setDisplayOrder(hrDutyLevel.getDisplayOrder() - 1);
        $(hrDutyLevel).save();
    }


    @Transactional(type = TransactionType.READ_WRITE)
    public void down(HrDutyLevel hrDutyLevel) {
        HrDutyLevel downObj = this.getByOrder(hrDutyLevel.getCompanyId(), hrDutyLevel.getDisplayOrder() + 1);
        downObj.setDisplayOrder(downObj.getDisplayOrder() - 1);
        $(downObj).save();
        hrDutyLevel.setDisplayOrder(hrDutyLevel.getDisplayOrder() + 1);
        $(hrDutyLevel).save();
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer validateName(String companyId,String name) {
        return $($count("id"),$eq("companyId",companyId), $eq("name", name)).value(HrDutyLevel.class, Integer.class);
    }

}
