package com.tosan.blog.moudules.post.repository;

import com.tosan.blog.moudules.post.model.Post;
import com.tosan.blog.moudules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository <Post,Long> {
  List<Post> findAllByUser(User user);
}
