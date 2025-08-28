package com.app.Repositories;

import com.app.Entity.VoteDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class CustomVoteBatchRepositoryImpl implements CustomVoteBatchRepository
{

    private final ReactiveRedisTemplate reactiveRedisTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    public CustomVoteBatchRepositoryImpl(
            @Qualifier("reactiveRedisTemplate") ReactiveRedisTemplate reactiveRedisTemplate)
    {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    @Override
    @Transactional
    public Map<String, Integer> saveVotesInBatch(List<VoteDTO> votes)
    {
        Map<String, Integer> entryScoreMap = new HashMap<>();
        if(votes == null || votes.isEmpty())
            return entryScoreMap;

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO thepeoplespoll.vote (id,user_id, poll_id, entry_id, score,vote_date) VALUES ");

        for(int i = 0; i < votes.size(); i++)
        {
            VoteDTO vote = votes.get(i);
            UUID uuid = UUID.nameUUIDFromBytes((vote.getUserId().toString()
                    +vote.getEntryId().toString()).getBytes());

            queryBuilder.append("('");
            queryBuilder.append(uuid);
            queryBuilder.append("','");
            queryBuilder.append(vote.getUserId());
            queryBuilder.append("','");
            queryBuilder.append(vote.getPollId());
            queryBuilder.append("','");
            queryBuilder.append(vote.getEntryId());
            queryBuilder.append("',");
            queryBuilder.append(vote.getScore());
            queryBuilder.append(",'");
            queryBuilder.append(Timestamp.from(Instant.now()));
            queryBuilder.append("')");

            if (i != votes.size() - 1) {
                queryBuilder.append(",");
            }

            entryScoreMap.put(vote.getEntryId().toString(), entryScoreMap.getOrDefault(vote.getEntryId().toString(),0) + vote.getScore());
        }
        queryBuilder.append(" ON CONFLICT (user_id, poll_id, entry_id) DO NOTHING;");
        entityManager.createNativeQuery(queryBuilder.toString()).executeUpdate();

        return entryScoreMap;
    }
}
