package org.yuxin.ecloud.server.modules.file.converter;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import org.yuxin.ecloud.server.modules.file.context.CreateFolderContext;
import org.yuxin.ecloud.server.modules.file.context.DeleteFileContext;
import org.yuxin.ecloud.server.modules.file.context.FileChunkMergeAndSaveContext;
import org.yuxin.ecloud.server.modules.file.context.FileChunkMergeContext;
import org.yuxin.ecloud.server.modules.file.context.FileChunkSaveContext;
import org.yuxin.ecloud.server.modules.file.context.FileChunkUploadContext;
import org.yuxin.ecloud.server.modules.file.context.FileSaveContext;
import org.yuxin.ecloud.server.modules.file.context.FileUploadContext;
import org.yuxin.ecloud.server.modules.file.context.QueryUploadedChunksContext;
import org.yuxin.ecloud.server.modules.file.context.SecUploadFileContext;
import org.yuxin.ecloud.server.modules.file.context.UpdateFilenameContext;
import org.yuxin.ecloud.server.modules.file.entity.ECloudUserFile;
import org.yuxin.ecloud.server.modules.file.po.CreateFolderPO;
import org.yuxin.ecloud.server.modules.file.po.DeleteFilePO;
import org.yuxin.ecloud.server.modules.file.po.FileChunkMergePO;
import org.yuxin.ecloud.server.modules.file.po.FileChunkUploadPO;
import org.yuxin.ecloud.server.modules.file.po.FileUploadPO;
import org.yuxin.ecloud.server.modules.file.po.QueryUploadedChunksPO;
import org.yuxin.ecloud.server.modules.file.po.SecUploadFilePO;
import org.yuxin.ecloud.server.modules.file.po.UpdateFilenamePO;
import org.yuxin.ecloud.server.modules.file.vo.ECloudUserFileVO;
import org.yuxin.ecloud.server.modules.file.vo.FolderTreeNodeVO;
import org.yuxin.ecloud.storage.engine.core.context.StoreFileChunkContext;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-11T10:51:27-0400",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
@Component
public class FileConverterImpl implements FileConverter {

    @Override
    public CreateFolderContext createFolderPO2CreateFolderContext(CreateFolderPO createFolderPO) {
        if ( createFolderPO == null ) {
            return null;
        }

        CreateFolderContext createFolderContext = new CreateFolderContext();

        createFolderContext.setFolderName( createFolderPO.getFolderName() );

        createFolderContext.setParentId( org.yuxin.ecloud.core.utils.IdUtil.decrypt(createFolderPO.getParentId()) );
        createFolderContext.setUserId( org.yuxin.ecloud.server.common.utils.UserIdUtil.get() );

        return createFolderContext;
    }

    @Override
    public UpdateFilenameContext updateFilenamePO2UpdateFilenameContext(UpdateFilenamePO updateFilenamePO) {
        if ( updateFilenamePO == null ) {
            return null;
        }

        UpdateFilenameContext updateFilenameContext = new UpdateFilenameContext();

        updateFilenameContext.setNewFilename( updateFilenamePO.getNewFilename() );

        updateFilenameContext.setFileId( org.yuxin.ecloud.core.utils.IdUtil.decrypt(updateFilenamePO.getFileId()) );
        updateFilenameContext.setUserId( org.yuxin.ecloud.server.common.utils.UserIdUtil.get() );

        return updateFilenameContext;
    }

    @Override
    public DeleteFileContext deleteFilePO2DeleteFileContext(DeleteFilePO deleteFilePO) {
        if ( deleteFilePO == null ) {
            return null;
        }

        DeleteFileContext deleteFileContext = new DeleteFileContext();

        deleteFileContext.setUserId( org.yuxin.ecloud.server.common.utils.UserIdUtil.get() );

        return deleteFileContext;
    }

    @Override
    public SecUploadFileContext secUploadFilePO2SecUploadFileContext(SecUploadFilePO secUploadFilePO) {
        if ( secUploadFilePO == null ) {
            return null;
        }

        SecUploadFileContext secUploadFileContext = new SecUploadFileContext();

        secUploadFileContext.setFilename( secUploadFilePO.getFilename() );
        secUploadFileContext.setIdentifier( secUploadFilePO.getIdentifier() );

        secUploadFileContext.setParentId( org.yuxin.ecloud.core.utils.IdUtil.decrypt(secUploadFilePO.getParentId()) );
        secUploadFileContext.setUserId( org.yuxin.ecloud.server.common.utils.UserIdUtil.get() );

        return secUploadFileContext;
    }

    @Override
    public FileUploadContext fileUploadPO2FileUploadContext(FileUploadPO fileUploadPO) {
        if ( fileUploadPO == null ) {
            return null;
        }

        FileUploadContext fileUploadContext = new FileUploadContext();

        fileUploadContext.setFilename( fileUploadPO.getFilename() );
        fileUploadContext.setIdentifier( fileUploadPO.getIdentifier() );
        fileUploadContext.setTotalSize( fileUploadPO.getTotalSize() );
        fileUploadContext.setFile( fileUploadPO.getFile() );

        fileUploadContext.setParentId( org.yuxin.ecloud.core.utils.IdUtil.decrypt(fileUploadPO.getParentId()) );
        fileUploadContext.setUserId( org.yuxin.ecloud.server.common.utils.UserIdUtil.get() );

        return fileUploadContext;
    }

    @Override
    public FileSaveContext fileUploadContext2FileSaveContext(FileUploadContext context) {
        if ( context == null ) {
            return null;
        }

        FileSaveContext fileSaveContext = new FileSaveContext();

        fileSaveContext.setFilename( context.getFilename() );
        fileSaveContext.setIdentifier( context.getIdentifier() );
        fileSaveContext.setTotalSize( context.getTotalSize() );
        fileSaveContext.setFile( context.getFile() );
        fileSaveContext.setUserId( context.getUserId() );

        return fileSaveContext;
    }

    @Override
    public FileChunkUploadContext fileChunkUploadPO2FileChunkUploadContext(FileChunkUploadPO fileChunkUploadPO) {
        if ( fileChunkUploadPO == null ) {
            return null;
        }

        FileChunkUploadContext fileChunkUploadContext = new FileChunkUploadContext();

        fileChunkUploadContext.setFilename( fileChunkUploadPO.getFilename() );
        fileChunkUploadContext.setIdentifier( fileChunkUploadPO.getIdentifier() );
        fileChunkUploadContext.setTotalChunks( fileChunkUploadPO.getTotalChunks() );
        fileChunkUploadContext.setChunkNumber( fileChunkUploadPO.getChunkNumber() );
        fileChunkUploadContext.setCurrentChunkSize( fileChunkUploadPO.getCurrentChunkSize() );
        fileChunkUploadContext.setTotalSize( fileChunkUploadPO.getTotalSize() );
        fileChunkUploadContext.setFile( fileChunkUploadPO.getFile() );

        fileChunkUploadContext.setUserId( org.yuxin.ecloud.server.common.utils.UserIdUtil.get() );

        return fileChunkUploadContext;
    }

    @Override
    public FileChunkSaveContext fileChunkUploadContext2FileChunkSaveContext(FileChunkUploadContext context) {
        if ( context == null ) {
            return null;
        }

        FileChunkSaveContext fileChunkSaveContext = new FileChunkSaveContext();

        fileChunkSaveContext.setFilename( context.getFilename() );
        fileChunkSaveContext.setIdentifier( context.getIdentifier() );
        fileChunkSaveContext.setTotalChunks( context.getTotalChunks() );
        fileChunkSaveContext.setChunkNumber( context.getChunkNumber() );
        fileChunkSaveContext.setCurrentChunkSize( context.getCurrentChunkSize() );
        fileChunkSaveContext.setTotalSize( context.getTotalSize() );
        fileChunkSaveContext.setFile( context.getFile() );
        fileChunkSaveContext.setUserId( context.getUserId() );

        return fileChunkSaveContext;
    }

    @Override
    public StoreFileChunkContext fileChunkSaveContext2StoreFileChunkContext(FileChunkSaveContext context) {
        if ( context == null ) {
            return null;
        }

        StoreFileChunkContext storeFileChunkContext = new StoreFileChunkContext();

        storeFileChunkContext.setFilename( context.getFilename() );
        storeFileChunkContext.setIdentifier( context.getIdentifier() );
        storeFileChunkContext.setTotalSize( context.getTotalSize() );
        storeFileChunkContext.setTotalChunks( context.getTotalChunks() );
        storeFileChunkContext.setChunkNumber( context.getChunkNumber() );
        storeFileChunkContext.setCurrentChunkSize( context.getCurrentChunkSize() );
        storeFileChunkContext.setUserId( context.getUserId() );

        return storeFileChunkContext;
    }

    @Override
    public QueryUploadedChunksContext queryUploadedChunksPO2QueryUploadedChunksContext(QueryUploadedChunksPO queryUploadedChunksPO) {
        if ( queryUploadedChunksPO == null ) {
            return null;
        }

        QueryUploadedChunksContext queryUploadedChunksContext = new QueryUploadedChunksContext();

        queryUploadedChunksContext.setIdentifier( queryUploadedChunksPO.getIdentifier() );

        queryUploadedChunksContext.setUserId( org.yuxin.ecloud.server.common.utils.UserIdUtil.get() );

        return queryUploadedChunksContext;
    }

    @Override
    public FileChunkMergeContext fileChunkMergePO2FileChunkMergeContext(FileChunkMergePO fileChunkMergePO) {
        if ( fileChunkMergePO == null ) {
            return null;
        }

        FileChunkMergeContext fileChunkMergeContext = new FileChunkMergeContext();

        fileChunkMergeContext.setFilename( fileChunkMergePO.getFilename() );
        fileChunkMergeContext.setIdentifier( fileChunkMergePO.getIdentifier() );
        fileChunkMergeContext.setTotalSize( fileChunkMergePO.getTotalSize() );

        fileChunkMergeContext.setUserId( org.yuxin.ecloud.server.common.utils.UserIdUtil.get() );
        fileChunkMergeContext.setParentId( org.yuxin.ecloud.core.utils.IdUtil.decrypt(fileChunkMergePO.getParentId()) );

        return fileChunkMergeContext;
    }

    @Override
    public FileChunkMergeAndSaveContext fileChunkMergeContext2FileChunkMergeAndSaveContext(FileChunkMergeContext context) {
        if ( context == null ) {
            return null;
        }

        FileChunkMergeAndSaveContext fileChunkMergeAndSaveContext = new FileChunkMergeAndSaveContext();

        fileChunkMergeAndSaveContext.setFilename( context.getFilename() );
        fileChunkMergeAndSaveContext.setIdentifier( context.getIdentifier() );
        fileChunkMergeAndSaveContext.setTotalSize( context.getTotalSize() );
        fileChunkMergeAndSaveContext.setParentId( context.getParentId() );
        fileChunkMergeAndSaveContext.setUserId( context.getUserId() );
        fileChunkMergeAndSaveContext.setRecord( context.getRecord() );

        return fileChunkMergeAndSaveContext;
    }

    @Override
    public FolderTreeNodeVO eCloudUserFile2FolderTreeNodeVO(ECloudUserFile record) {
        if ( record == null ) {
            return null;
        }

        FolderTreeNodeVO folderTreeNodeVO = new FolderTreeNodeVO();

        folderTreeNodeVO.setLabel( record.getFilename() );
        folderTreeNodeVO.setId( record.getFileId() );
        folderTreeNodeVO.setParentId( record.getParentId() );

        folderTreeNodeVO.setChildren( com.google.common.collect.Lists.newArrayList() );

        return folderTreeNodeVO;
    }

    @Override
    public ECloudUserFileVO eCloudUserFile2ECloudUserFileVO(ECloudUserFile record) {
        if ( record == null ) {
            return null;
        }

        ECloudUserFileVO eCloudUserFileVO = new ECloudUserFileVO();

        eCloudUserFileVO.setFileId( record.getFileId() );
        eCloudUserFileVO.setParentId( record.getParentId() );
        eCloudUserFileVO.setFilename( record.getFilename() );
        eCloudUserFileVO.setFileSizeDesc( record.getFileSizeDesc() );
        eCloudUserFileVO.setFolderFlag( record.getFolderFlag() );
        eCloudUserFileVO.setFileType( record.getFileType() );
        eCloudUserFileVO.setUpdateTime( record.getUpdateTime() );

        return eCloudUserFileVO;
    }
}
