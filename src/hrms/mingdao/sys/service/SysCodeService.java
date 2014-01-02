package hrms.mingdao.sys.service;

import com.google.inject.Singleton;
import hrms.mingdao.sys.entity.SysArType;
import hrms.mingdao.sys.entity.SysCode;
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
public class SysCodeService extends HQuery {

    /**
     * @param id
     * @return 根据Id获取代码
     */
    @Transactional(type = TransactionType.READ_ONLY)
    public SysCode getById(Long id) {
        return $(id).get(SysCode.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<SysCode> getListByParentId(String codeId) {
        return $($alias("parentId", "parentId"), $eq("parentId.codeId", codeId), $eq("useYn", "Y"), $order("displayOrder")).list(SysCode.class);
    }

    /**
     * 保存对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(SysCode sysCode) {
        $(sysCode).save();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<SysCode> sysCodeList) {
        $(sysCodeList).save();
    }

    /**
     * 删除对象
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(SysCode sysCode) {
        $(sysCode).delete();
    }


    /**
     * 根据id 删除对象
     *
     * @param id
     */
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteById(Long id) {
        $(id).delete(SysCode.class);
    }
}
