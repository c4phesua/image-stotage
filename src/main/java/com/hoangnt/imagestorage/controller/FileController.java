package com.hoangnt.imagestorage.controller;

import com.hoangnt.imagestorage.api.FilesUploadApi;
import com.hoangnt.imagestorage.api.model.DeleteFileRequest;
import com.hoangnt.imagestorage.api.model.ListFileResponse;
import com.hoangnt.imagestorage.api.model.UploadFileResponse;
import com.hoangnt.imagestorage.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class FileController implements FilesUploadApi {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity<Void> deleteFile(DeleteFileRequest deleteFileRequest) {
        fileService.deleteByUrl(deleteFileRequest.getUrl());
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<ListFileResponse> getAllFiles() {
        return ResponseEntity.ok(fileService.getAll());
    }

    @Override
    public ResponseEntity<UploadFileResponse> uploadFiles(Resource body) {
        return ResponseEntity.ok(fileService.uploadFile(body));
    }
}
