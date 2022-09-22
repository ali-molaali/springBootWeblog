package com.tosan.blog.moudules.post.service;

import com.tosan.blog.config.MyBeanCopy;
import com.tosan.blog.moudules.authority.model.Authority;
import com.tosan.blog.moudules.post.model.Post;
import com.tosan.blog.moudules.post.repository.PostRepository;
import com.tosan.blog.moudules.role.model.Role;
import com.tosan.blog.moudules.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;


import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostService {

    private PostRepository postRepository;
    @Autowired
    PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }
    @Transactional
    public Post postRegister(Post posts) throws IOException, InvocationTargetException, IllegalAccessException {
        if (!posts.getFile().isEmpty()) {
            String path = ResourceUtils.getFile("classpath:static/img").getAbsolutePath();
            byte[] bytes = posts.getFile().getBytes();
            String name = UUID.randomUUID() + "." + Objects.requireNonNull(posts.getFile().getContentType()).split("/")[1];
            Files.write(Paths.get(path + File.separator + name), bytes);
            posts.setCover(name);
        }
        if (posts.getId() != null) {
            Post exist = postRepository.getById(posts.getId());
            MyBeanCopy myBeanCopy = new MyBeanCopy();
            myBeanCopy.copyProperties(exist,posts);
            return postRepository.save(exist);
        }
        return this.postRepository.save(posts);
    }

    public List<Post> findAll(){

        return postRepository.findAll();
    }
    public List<Post> findAllByUser(User user){
        Boolean check=false;
        System.out.println("0");
        for (Role role:user.getRoles()) {
            System.out.println("1");
            for (Authority authority:role.getAuthorities()) {
                System.out.println("2");
                System.out.println(authority.getName());
                if(authority.getName().equals("OP_ADMIN_ACCESS") || authority.getName().equals("OP_ADMIN_DELETE")||authority.getName().equals("OP_ADMIN_ADD")||authority.getName().equals("OP_ADMIN_EDIT"))
                {
                    check=true;
                    System.out.println("check = "+check);
                    System.out.println("3");
                }
                System.out.println("4");
            }
            System.out.println("5");
        }
        System.out.println("6");
        if (check) {
            System.out.println("7");
            return postRepository.findAll();
           }else
        { System.out.println("8");
            return postRepository.findAllByUser(user);}

    }
    public Object findById(Long id) {
        return postRepository.findById(id);
    }
    @Transactional
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
