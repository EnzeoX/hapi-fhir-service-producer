package com.job.testsender.repository;

import com.job.testsender.entity.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@ConditionalOnExpression("${security.jwt.enabled} and not ${security.simple-token.enabled}")
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
}
