package hrms.mingdao.hr.service;

import com.google.inject.Singleton;
import hrms.mingdao.hr.entity.HrJob;
import hrms.mingdao.hr.entity.HrOfficeAddr;
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
public class HrOfficeAddrService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public HrOfficeAddr getById(Long id) {
        return $(id).get(HrOfficeAddr.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<HrOfficeAddr> getListByCompanyId(String companyId) {
        return $($eq("companyId", companyId), $eq("useYn", "Y"), $order("displayOrder")).list(HrOfficeAddr.class);
    }


    @Transactional(type = TransactionType.READ_ONLY)
    public Page<HrOfficeAddr> getPageList(int start,
                                          int limit, List<Selector> selectorList) {
        return $(selectorList).page(HrOfficeAddr.class, start, limit);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(HrOfficeAddr hrOfficeAddr) {
        $(hrOfficeAddr).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<HrOfficeAddr> hrOfficeAddrList) {
        $(hrOfficeAddrList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(HrOfficeAddr hrOfficeAddr) {
        $(hrOfficeAddr).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(HrOfficeAddr.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer validateName(String companyId, String address) {
        return $($count("id"), $eq("companyId", companyId), $eq("address", address)).value(HrOfficeAddr.class, Integer.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getMaxDisplayOrder(String companyId) {
        return $($eq("companyId", companyId), $eq("useYn", "Y"), $max("displayOrder")).value(HrOfficeAddr.class, Integer.class);
    }


    @Transactional(type = TransactionType.READ_ONLY)
    public HrOfficeAddr getByOrder(String companyId, Integer order) {
        return $($eq("companyId", companyId), $eq("displayOrder", order)).get(HrOfficeAddr.class);
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void up(HrOfficeAddr hrOfficeAddr) {
        HrOfficeAddr upObj = this.getByOrder(hrOfficeAddr.getCompanyId(), hrOfficeAddr.getDisplayOrder() - 1);
        upObj.setDisplayOrder(upObj.getDisplayOrder() + 1);
        $(upObj).save();
        hrOfficeAddr.setDisplayOrder(hrOfficeAddr.getDisplayOrder() - 1);
        $(hrOfficeAddr).save();
    }


    @Transactional(type = TransactionType.READ_WRITE)
    public void down(HrOfficeAddr hrOfficeAddr) {
        HrOfficeAddr downObj = this.getByOrder(hrOfficeAddr.getCompanyId(), hrOfficeAddr.getDisplayOrder() + 1);
        downObj.setDisplayOrder(downObj.getDisplayOrder() - 1);
        $(downObj).save();
        hrOfficeAddr.setDisplayOrder(hrOfficeAddr.getDisplayOrder() + 1);
        $(hrOfficeAddr).save();
    }

}
