package com.app.Repositories;

import com.app.Entity.User;
import com.app.Entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IVoteRepository extends JpaRepository<Vote, String> , CustomVoteBatchRepository
{

    List<Vote> findByUser(User user);

    @Query(value = "select sum(score) from thepeoplespoll.vote  where entry_id = :entryId", nativeQuery = true)
    Integer sumScoreByEntryId(String entryId);
}
