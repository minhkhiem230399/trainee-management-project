package com.edu.hutech.models;

import lombok.Data;

@Data
public class ResponseMessage {

    private String message;

    private String fileDownloadUri;

    public ResponseMessage(String message, String fileDownloadUri) {
        this.message = message;
        this.fileDownloadUri = fileDownloadUri;
    }
}
