package com.hoangnt.imagestorage.repository;

import com.hoangnt.imagestorage.entity.S3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3FileRepository extends JpaRepository<S3File, Long> {
    S3File deleteByUrl(String url);
}
