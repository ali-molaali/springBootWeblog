package com.tosan.blog.moudules.user.repository;

import com.tosan.blog.moudules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository <User,Long> {
    User findByEmail(String email);

}
