package com.app.Services;

import com.app.Repositories.IVoteRepository;
import org.apache.kafka.common.requests.VoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class CacheService
{
    private final IVoteRepository voteRepository;
    private final RedisTemplate redisTemplate;

    @Autowired
    public CacheService (IVoteRepository voteRepository, RedisTemplate<String, String> redisTemplate)
    {
        super();
        this.voteRepository = voteRepository;
        this.redisTemplate = redisTemplate;
    }

    private final String REDIS_HASH = "entry_scores";

    @Cacheable(value = "entry-scores", key = "#entryId")
    public Integer getScore(String entryId)
    {
        // Fallback: fetch from DB (if not in cache)
        System.out.println("Fetching from DB for entry: " + entryId);
        return voteRepository.sumScoreByEntryId(entryId);
       // return 0;
    }

    @CachePut(value = "entry-scores", key = "#entryId")
    public Integer updateScore(String entryId, Integer newScore) {
        return newScore;
    }

    @CacheEvict(value = "entry-scores", key = "#entryId")
    public void evictScore(UUID entryId) {
        System.out.println("Evicted entry from cache: " + entryId);
    }

    private void incrementEntryScore(String entryId, int score) {
        redisTemplate.opsForHash().increment(REDIS_HASH, entryId, score);
    }

    private Integer getEntryScore(String entryId) {
        Object score = redisTemplate.opsForHash().get(REDIS_HASH, entryId.toString());
        return score != null ? Integer.parseInt(score.toString()) : 0;
    }

    public void updateEntryCache(Map<String,Integer> map)
    {
        for(Map.Entry<String,Integer> entry : map.entrySet())
        {
            final Integer cachedScore = getEntryScore(entry.getKey());
            incrementEntryScore(entry.getKey(), cachedScore + entry.getValue());
        }


    }

}
