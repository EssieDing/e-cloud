package org.yuxin.ecloud.server.modules.recycle.service;

import org.yuxin.ecloud.server.modules.file.vo.ECloudUserFileVO;
import org.yuxin.ecloud.server.modules.recycle.context.DeleteContext;
import org.yuxin.ecloud.server.modules.recycle.context.QueryRecycleFileListContext;
import org.yuxin.ecloud.server.modules.recycle.context.RestoreContext;

import java.util.List;

public interface IRecycleService {

    /**
     * 查询用户的回收站文件列表
     *
     * @param context
     * @return
     */
    List<ECloudUserFileVO> recycles(QueryRecycleFileListContext context);

    /**
     * 文件还原
     *
     * @param context
     */
    void restore(RestoreContext context);

    /**
     * 文件彻底删除
     *
     * @param context
     */
    void delete(DeleteContext context);

}
