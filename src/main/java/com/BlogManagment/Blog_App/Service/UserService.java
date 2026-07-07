package com.BlogManagment.Blog_App.Service;

import com.BlogManagment.Blog_App.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    public User createUser(User user);
    public User updateUser(User user);
    public User findUserById(Long id);
    public  User findUserByEmail(String email);
    public void deleteUser(Long id);
    public Page<User> findAllUsers(int pageNo , Pageable pageable);
}
