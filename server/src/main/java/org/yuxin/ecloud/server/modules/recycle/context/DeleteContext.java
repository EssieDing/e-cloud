package org.yuxin.ecloud.server.modules.recycle.context;

import org.yuxin.ecloud.server.modules.file.entity.ECloudUserFile;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 删除文件的上下文实体对象
 */
@Data
public class DeleteContext implements Serializable {

    private static final long serialVersionUID = -588491390915976064L;

    /**
     * 要操作的文件ID的集合
     */
    private List<Long> fileIdList;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

    /**
     * 要被删除的文件记录列表
     */
    private List<ECloudUserFile> records;

    /**
     * 所有要被删除的文件记录列表
     */
    private List<ECloudUserFile> allRecords;

}
