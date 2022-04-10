package com.hoangnt.imagestorage.controller;

import com.hoangnt.imagestorage.api.FilesUploadApi;
import com.hoangnt.imagestorage.api.model.ListFileResponse;
import com.hoangnt.imagestorage.api.model.PutTagsRequest;
import com.hoangnt.imagestorage.api.model.UploadFileResponse;
import com.hoangnt.imagestorage.mapper.S3FileMapper;
import com.hoangnt.imagestorage.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class FileController implements FilesUploadApi {
    private final FileService fileService;

    private final S3FileMapper mapper;

    @Override
    public ResponseEntity<Void> deleteFile(Long id) {
        fileService.delete(id);
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<ListFileResponse> getAllFiles() {
        return ResponseEntity.ok(mapper.toListResponse(fileService.getAll()));
    }

    @Override
    public ResponseEntity<UploadFileResponse> putTags(Long id, PutTagsRequest putTagsRequest) {
        return ResponseEntity.ok(mapper.toModel(fileService.putTags(putTagsRequest.getTags(), id)));
    }

    @Override
    public ResponseEntity<UploadFileResponse> uploadFiles(Resource body) {
        return ResponseEntity.ok(mapper.toModel(fileService.uploadFile(body)));
    }
}
