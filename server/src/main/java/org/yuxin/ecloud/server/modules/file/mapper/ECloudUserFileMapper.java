package org.yuxin.ecloud.server.modules.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.yuxin.ecloud.server.modules.file.context.FileSearchContext;
import org.yuxin.ecloud.server.modules.file.context.QueryFileListContext;
import org.yuxin.ecloud.server.modules.file.entity.ECloudUserFile;
import org.yuxin.ecloud.server.modules.file.vo.FileSearchResultVO;
import org.yuxin.ecloud.server.modules.file.vo.ECloudUserFileVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yuxin
 * @description 针对表【e_cloud_user_file(用户文件信息表)】的数据库操作Mapper
 * @createDate 2023-05-20 18:36:41
 * @Entity org.yuxin.ecloud.server.modules.file.entity.ECloudUserFile
 */
public interface ECloudUserFileMapper extends BaseMapper<ECloudUserFile> {

    /**
     * 查询用户的文件列表
     *
     * @param context
     * @return
     */
    List<ECloudUserFileVO> selectFileList(@Param("param") QueryFileListContext context);

    /**
     * 文件搜索
     *
     * @param context
     * @return
     */
    List<FileSearchResultVO> searchFile(@Param("param") FileSearchContext context);

}




