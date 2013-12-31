package hrms.mingdao.sys.service;

import com.google.inject.Singleton;
import hrms.mingdao.sys.entity.SysArType;
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
public class SysArTypeService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public SysArType getById(Long id) {
        return $(id).get(SysArType.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<SysArType> getListByUse() {
        return $($eq("useYn","Y"),$order("typePy")).list(SysArType.class);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(SysArType sysArType) {
        $(sysArType).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<SysArType> sysArTypeList) {
        $(sysArTypeList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(SysArType sysArType) {
        $(sysArType).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(SysArType.class);
    }
}
