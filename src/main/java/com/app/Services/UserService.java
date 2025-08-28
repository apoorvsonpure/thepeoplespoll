package com.app.Services;

import com.app.Entity.User;
import com.app.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{

    @Autowired
    private IUserRepository userRepository;

    public User getUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }
}
