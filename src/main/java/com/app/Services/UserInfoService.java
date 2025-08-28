package com.app.Services;

import com.app.Entity.User;
import com.app.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService
{

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
                final User dbUser = userRepository.findByEmail(username);
                if(dbUser != null)
                {
//                    return org.springframework.security.core.userdetails.User.builder()
//                            .username(dbUser.getEmail())
//                            .password(dbUser.getPassword())
//                            .build();
                    return dbUser;
                }
                throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
