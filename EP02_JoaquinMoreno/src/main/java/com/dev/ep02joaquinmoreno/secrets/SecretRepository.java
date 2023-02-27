package com.dev.ep02joaquinmoreno.secrets;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecretRepository extends MongoRepository<Secret, ObjectId> {

    Optional<Secret> findByKey(String key);
}
