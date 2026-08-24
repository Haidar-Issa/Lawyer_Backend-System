package com.web.lawyer_backend_system.analyzer;

import com.web.lawyer_backend_system.exception.MissingDocumentStorageException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class DocumentStorageFailureAnalyzer extends AbstractFailureAnalyzer<MissingDocumentStorageException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, MissingDocumentStorageException cause) {

        String description = String.format(
                "فشل إقلاع نظام المحاماة! مسار حفظ ملفات وعقود الموكلين غير صالح أو لا يملك صلاحية كتابة: [%s]",
                cause.getStoragePath()
        );

        String action = """
                يرجى اتخاذ الخطوات التالية:\s
                1. تأكد من تحديد المسار الصحيح داخل application.properties عبر المتغير:
                   app.documents.storage-path=C:/lawyer_system/documents
                2. تأكد من إنشاء المجلد على القرص ومنح السيرفر صلاحيات القراءة والكتابة (Read/Write Permissions).""";

        return new FailureAnalysis(description, action, cause);
    }
}