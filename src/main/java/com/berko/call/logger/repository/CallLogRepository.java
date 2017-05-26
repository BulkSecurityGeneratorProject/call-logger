package com.berko.call.logger.repository;

import com.berko.call.logger.domain.CallLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the CallLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CallLogRepository extends MongoRepository<CallLog,String> {

}
