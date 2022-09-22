package com.tosan.blog.moudules.user.service;


import com.tosan.blog.config.MyBeanCopy;
import com.tosan.blog.moudules.user.model.User;
import com.tosan.blog.moudules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserService implements UserDetailsService {

    private UserRepository usersRepository;
    @Autowired
    UserService(UserRepository usersRepository){
        this.usersRepository=usersRepository;
    }
    @Transactional
    public User userRegister(User user) throws IOException, InvocationTargetException, IllegalAccessException {
        if (!user.getFile().isEmpty()) {
            String path = ResourceUtils.getFile("classpath:static/img").getAbsolutePath();
            byte[] bytes = user.getFile().getBytes();
            String name = UUID.randomUUID() + "." + Objects.requireNonNull(user.getFile().getContentType()).split("/")[1];
            Files.write(Paths.get(path + File.separator + name), bytes);
            user.setCover(name);
        }

        if (!user.getPassword().isEmpty())
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        if(user.getId() != null) {
            User exist = usersRepository.getById(user.getId());
            MyBeanCopy myBeanCopy = new MyBeanCopy();
            myBeanCopy.copyProperties(exist,user);
            return usersRepository.save(exist);
        }



        return this.usersRepository.save(user);
    }

    public List<User> findAll(){
        return usersRepository.findAll();
    }

    public User findByEmail(String name) {
        return usersRepository.findByEmail(name);
    }

    public Object findById(Long id) {
        return usersRepository.findById(id);
    }
    @Transactional
    @PreAuthorize("#user.email != authentication.name")
    public void deleteById(User user ) {

        usersRepository.deleteById(user.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return usersRepository.findByEmail(s);
    }
}
