package org.menagerie.stnotifier.repository;

import org.bson.types.ObjectId;
import org.menagerie.stnotifier.model.STMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 9/30/16, 10:02 PM
 */
@Repository
public interface STMessageRepository<T> extends MongoRepository<T, ObjectId>
{
    T findFirstByDisplayedAndBlockedOrderByReceivedDateDesc(Boolean isDisplayed, Boolean isBlocked);

    List<T> findByDisplayedOrderByStickyToTopDescReceivedDateDesc(Boolean isDisplayed, Pageable pageable);
}
