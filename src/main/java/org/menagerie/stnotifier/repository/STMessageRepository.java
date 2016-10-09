package org.menagerie.stnotifier.repository;

import org.bson.types.ObjectId;
import org.menagerie.stnotifier.model.STMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 9/30/16, 10:02 PM
 */
@Repository
public interface STMessageRepository extends MongoRepository<STMessage, ObjectId>
{
    STMessage findFirstByDisplayedOrderByReceivedDateDesc(Boolean isDisplayed);
}
