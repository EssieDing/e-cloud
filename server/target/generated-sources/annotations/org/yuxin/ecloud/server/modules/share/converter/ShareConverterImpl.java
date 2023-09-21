package org.yuxin.ecloud.server.modules.share.converter;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import org.yuxin.ecloud.server.modules.share.context.CreateShareUrlContext;
import org.yuxin.ecloud.server.modules.share.po.CreateShareUrlPO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-11T10:51:27-0400",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
@Component
public class ShareConverterImpl implements ShareConverter {

    @Override
    public CreateShareUrlContext createShareUrlPO2CreateShareUrlContext(CreateShareUrlPO createShareUrlPO) {
        if ( createShareUrlPO == null ) {
            return null;
        }

        CreateShareUrlContext createShareUrlContext = new CreateShareUrlContext();

        createShareUrlContext.setShareName( createShareUrlPO.getShareName() );
        createShareUrlContext.setShareType( createShareUrlPO.getShareType() );
        createShareUrlContext.setShareDayType( createShareUrlPO.getShareDayType() );

        createShareUrlContext.setUserId( org.yuxin.ecloud.server.common.utils.UserIdUtil.get() );

        return createShareUrlContext;
    }
}
