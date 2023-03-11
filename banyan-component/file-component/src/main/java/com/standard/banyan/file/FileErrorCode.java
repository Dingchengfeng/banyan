package com.standard.banyan.file;

import com.standard.banyan.common.api.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileErrorCode implements IErrorCode {
    //
    FILE_UPLOAD_FAIL(5001, "file.upload.fail"),
    FILE_NOT_FOUND(5004, "file.notFound"),
    ;
    private final int code;
    private final String message;
}
