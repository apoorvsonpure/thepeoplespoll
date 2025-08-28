package com.app.Repositories;

import com.app.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String>
{

    User findByEmail(String username);
}
