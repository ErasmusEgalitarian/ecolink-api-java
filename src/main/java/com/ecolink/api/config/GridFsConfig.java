package com.ecolink.api.config;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

@Configuration
@RequiredArgsConstructor
public class GridFsConfig {

    private final MongoDatabaseFactory mongoDatabaseFactory;

    @Bean
    public GridFSBucket imagesGridFsBucket() {
        return GridFSBuckets.create(
                mongoDatabaseFactory.getMongoDatabase(),
                "images"
        );
    }
}
