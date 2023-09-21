//package org.yuxin.ecloud.server.common.event.file;
//
//import org.yuxin.ecloud.server.modules.file.entity.ECloudUserFile;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.context.ApplicationEvent;
//
//import java.util.List;
//
///**
// * 文件被物理删除的事件实体
// */
//@Getter
//@Setter
//@EqualsAndHashCode
//@ToString
//public class FilePhysicalDeleteEvent extends ApplicationEvent {
//
//    /**
//     * 所有被物理删除的文件实体集合
//     */
//    private List<ECloudUserFile> allRecords;
//
//    public FilePhysicalDeleteEvent(Object source, List<ECloudUserFile> allRecords) {
//        super(source);
//        this.allRecords = allRecords;
//    }
//
//}
