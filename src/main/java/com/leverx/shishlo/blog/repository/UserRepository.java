package com.leverx.shishlo.blog.repository;

import com.leverx.shishlo.blog.entity.User;
import com.leverx.shishlo.blog.entity.UserStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("update User u set u.status = :status where u.id = :id")
    void updateStatus(@Param("id") Long id,
                      @Param("status") UserStatus status);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    void updatePassword(@Param("id") Long id,
                        @Param("password") String password);
}
