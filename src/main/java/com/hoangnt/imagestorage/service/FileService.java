package com.hoangnt.imagestorage.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.amplify.model.BadRequestException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hoangnt.imagestorage.entity.FileStatus;
import com.hoangnt.imagestorage.entity.S3File;
import com.hoangnt.imagestorage.repository.S3FileRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Value("${s3.accessKey}")
    private String accessKey;

    @Value("${s3.secretKey}")
    private String secretKey;

    @Value("${s3.bucketName}")
    private String bucketName;

    private final S3FileRepository s3FileRepository;

    @Autowired
    private Validator validator;

    public FileService(S3FileRepository s3FileRepository) {
        this.s3FileRepository = s3FileRepository;
    }

    public S3File uploadFile(Resource file) {
        File targetFile = null;
        try {
            String fileName = UUID.randomUUID().toString().replace("-", "");
            targetFile = File.createTempFile(fileName, ".jpg");
            InputStream storeImage = file.getInputStream();
            FileUtils.copyInputStreamToFile(storeImage, targetFile);
            AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.AP_SOUTHEAST_2)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .build();
            s3Client.putObject(new PutObjectRequest(bucketName, fileName,
                    targetFile).withCannedAcl(
                    CannedAccessControlList.PublicRead));
            URL url = s3Client.getUrl(bucketName, fileName);
            S3File s3File = S3File.builder().url(url.toString()).status(FileStatus.ACTIVE).build();
            return s3FileRepository.save(s3File);
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        } finally {
            if (targetFile != null) {
                targetFile.delete();
            }
        }
    }

    public List<S3File> getAll() {
        return s3FileRepository.findAll();
    }

    public void delete(long id){
        s3FileRepository.deleteById(id);
    }

    public S3File putTags(String tags, long id){
        S3File file = s3FileRepository.getById(id);
        file.setTags(tags);
        return s3FileRepository.save(file);
    }
}
