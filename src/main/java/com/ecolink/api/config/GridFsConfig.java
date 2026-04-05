package com.ecolink.api.config;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
@RequiredArgsConstructor
public class GridFsConfig {

    private final MongoDatabaseFactory mongoDatabaseFactory;

    @Bean
    public GridFsTemplate gridFsTemplate(MongoDatabaseFactory dbFactory,
                                         MappingMongoConverter converter) {
        return new GridFsTemplate(dbFactory, converter, "images");
    }
}
