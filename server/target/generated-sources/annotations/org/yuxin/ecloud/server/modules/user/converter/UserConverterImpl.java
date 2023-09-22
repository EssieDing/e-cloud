package org.yuxin.ecloud.server.modules.user.converter;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import org.yuxin.ecloud.server.modules.file.entity.ECloudUserFile;
import org.yuxin.ecloud.server.modules.user.context.ChangePasswordContext;
import org.yuxin.ecloud.server.modules.user.context.CheckAnswerContext;
import org.yuxin.ecloud.server.modules.user.context.CheckUsernameContext;
import org.yuxin.ecloud.server.modules.user.context.ResetPasswordContext;
import org.yuxin.ecloud.server.modules.user.context.UserLoginContext;
import org.yuxin.ecloud.server.modules.user.context.UserRegisterContext;
import org.yuxin.ecloud.server.modules.user.entity.ECloudUser;
import org.yuxin.ecloud.server.modules.user.po.ChangePasswordPO;
import org.yuxin.ecloud.server.modules.user.po.CheckAnswerPO;
import org.yuxin.ecloud.server.modules.user.po.CheckUsernamePO;
import org.yuxin.ecloud.server.modules.user.po.ResetPasswordPO;
import org.yuxin.ecloud.server.modules.user.po.UserLoginPO;
import org.yuxin.ecloud.server.modules.user.po.UserRegisterPO;
import org.yuxin.ecloud.server.modules.user.vo.UserInfoVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-21T12:02:05-0400",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public UserRegisterContext userRegisterPO2UserRegisterContext(UserRegisterPO userRegisterPO) {
        if ( userRegisterPO == null ) {
            return null;
        }

        UserRegisterContext userRegisterContext = new UserRegisterContext();

        userRegisterContext.setUsername( userRegisterPO.getUsername() );
        userRegisterContext.setPassword( userRegisterPO.getPassword() );
        userRegisterContext.setQuestion( userRegisterPO.getQuestion() );
        userRegisterContext.setAnswer( userRegisterPO.getAnswer() );

        return userRegisterContext;
    }

    @Override
    public ECloudUser userRegisterContext2ECloudUser(UserRegisterContext userRegisterContext) {
        if ( userRegisterContext == null ) {
            return null;
        }

        ECloudUser eCloudUser = new ECloudUser();

        eCloudUser.setUsername( userRegisterContext.getUsername() );
        eCloudUser.setQuestion( userRegisterContext.getQuestion() );
        eCloudUser.setAnswer( userRegisterContext.getAnswer() );

        return eCloudUser;
    }

    @Override
    public UserLoginContext userLoginPO2UserLoginContext(UserLoginPO userLoginPO) {
        if ( userLoginPO == null ) {
            return null;
        }

        UserLoginContext userLoginContext = new UserLoginContext();

        userLoginContext.setUsername( userLoginPO.getUsername() );
        userLoginContext.setPassword( userLoginPO.getPassword() );

        return userLoginContext;
    }

    @Override
    public CheckUsernameContext checkUsernamePO2CheckUsernameContext(CheckUsernamePO checkUsernamePO) {
        if ( checkUsernamePO == null ) {
            return null;
        }

        CheckUsernameContext checkUsernameContext = new CheckUsernameContext();

        checkUsernameContext.setUsername( checkUsernamePO.getUsername() );

        return checkUsernameContext;
    }

    @Override
    public CheckAnswerContext checkAnswerPO2CheckAnswerContext(CheckAnswerPO checkAnswerPO) {
        if ( checkAnswerPO == null ) {
            return null;
        }

        CheckAnswerContext checkAnswerContext = new CheckAnswerContext();

        checkAnswerContext.setUsername( checkAnswerPO.getUsername() );
        checkAnswerContext.setQuestion( checkAnswerPO.getQuestion() );
        checkAnswerContext.setAnswer( checkAnswerPO.getAnswer() );

        return checkAnswerContext;
    }

    @Override
    public ResetPasswordContext resetPasswordPO2ResetPasswordContext(ResetPasswordPO resetPasswordPO) {
        if ( resetPasswordPO == null ) {
            return null;
        }

        ResetPasswordContext resetPasswordContext = new ResetPasswordContext();

        resetPasswordContext.setUsername( resetPasswordPO.getUsername() );
        resetPasswordContext.setPassword( resetPasswordPO.getPassword() );
        resetPasswordContext.setToken( resetPasswordPO.getToken() );

        return resetPasswordContext;
    }

    @Override
    public ChangePasswordContext changePasswordPO2ChangePasswordContext(ChangePasswordPO changePasswordPO) {
        if ( changePasswordPO == null ) {
            return null;
        }

        ChangePasswordContext changePasswordContext = new ChangePasswordContext();

        changePasswordContext.setOldPassword( changePasswordPO.getOldPassword() );
        changePasswordContext.setNewPassword( changePasswordPO.getNewPassword() );

        return changePasswordContext;
    }

    @Override
    public UserInfoVO assembleUserInfoVO(ECloudUser eCloudUser, ECloudUserFile eCloudUserFile) {
        if ( eCloudUser == null && eCloudUserFile == null ) {
            return null;
        }

        UserInfoVO userInfoVO = new UserInfoVO();

        if ( eCloudUser != null ) {
            userInfoVO.setUsername( eCloudUser.getUsername() );
        }
        if ( eCloudUserFile != null ) {
            userInfoVO.setRootFileId( eCloudUserFile.getFileId() );
            userInfoVO.setRootFilename( eCloudUserFile.getFilename() );
        }

        return userInfoVO;
    }
}
