package org.menagerie.stnotifier.repository;

import org.bson.types.ObjectId;
import org.menagerie.stnotifier.model.STConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 11/13/16, 3:27 PM
 */
@Repository
public interface STConfigRepository extends MongoRepository<STConfig, ObjectId>
{
    STConfig findFirstByName(String name);
}
