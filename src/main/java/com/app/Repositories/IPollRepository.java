package com.app.Repositories;

import com.app.Entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPollRepository extends JpaRepository<Poll, String>
{

}
