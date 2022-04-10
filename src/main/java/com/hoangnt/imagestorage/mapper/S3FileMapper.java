package com.hoangnt.imagestorage.mapper;

import com.hoangnt.imagestorage.api.model.ListFileResponse;
import com.hoangnt.imagestorage.api.model.UploadFileResponse;
import com.hoangnt.imagestorage.entity.S3File;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class S3FileMapper {
    public S3File toEntity(UploadFileResponse response){
        return S3File.builder()
                .url(response.getFileUrl())
                .id(response.getId())
                .tags(response.getTags())
                .build();
    }

    public UploadFileResponse toModel(S3File file){
        UploadFileResponse response = new UploadFileResponse();
        response.setFileUrl(file.getUrl());
        response.setId(file.getId());
        response.setTags(file.getTags());
        response.setCharacter(file.getCharacter());
        return response;
    }

    public ListFileResponse toListResponse(List<S3File> files){
        ListFileResponse response = new ListFileResponse();
        for (S3File file : files){
            response.addContentItem(toModel(file));
        }
        return response;
    }
}
