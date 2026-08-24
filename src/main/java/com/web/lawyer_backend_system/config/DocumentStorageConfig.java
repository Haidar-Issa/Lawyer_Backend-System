//package com.web.lawyer_backend_system.config;
//
//import com.web.lawyer_backend_system.exception.MissingDocumentStorageException;
//import jakarta.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.File;
//
//@Configuration
//public class DocumentStorageConfig {
//    @Value("${app.document.storage-path:}")
//    private String storagePath;
//
//    @PostConstruct
//    private void validateStorage(){
//        if(this.storagePath.isEmpty() || this.storagePath.isBlank()){
//            throw new MissingDocumentStorageException("NOT_CONFIGURED");
//        }
//
//        File file = new File(this.storagePath);
//
//        if(!file.exists() || !file.canWrite()){
//            throw new MissingDocumentStorageException(storagePath);
//        }
//
//    }
//}
