package com.app.Services;

import com.app.Entity.*;
import com.app.Repositories.IEntryRepository;
import com.app.Repositories.IPollRepository;
import com.app.Repositories.IUserRepository;
import com.app.Repositories.IVoteRepository;
import com.app.Utils.BatchUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VoteProcessorService
{

    private final IVoteRepository voteRepository;
    private final CacheService cacheService;
//
//    private final IPollRepository pollRepository;
//
//    private final IEntryRepository entryRepository;
//
//    private final IUserRepository userRepository;

    @Autowired
    public VoteProcessorService(IVoteRepository voteRepository, IPollRepository pollRepository, IEntryRepository entryRepository,
            IUserRepository userRepository, CacheService cacheService)
    {
        super();
        this.voteRepository = voteRepository;
        this.cacheService = cacheService;
//        this.pollRepository = pollRepository;
//        this.entryRepository = entryRepository;
//        this.userRepository = userRepository;
    }

    //@Retryable(retryFor = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    //public void processVotes(List<String> messages)
    public void processVotes(List<VoteDTO> allVotes)
            throws JsonProcessingException
    {

        List<List<VoteDTO>> voteChunks = BatchUtils.splitList(allVotes, 500);

        for (List<VoteDTO> chunk : voteChunks)
        {
           // voteRepository.saveVotesInBatch(chunk);
            final Map<String, Integer> entryScoreMap = saveChunkWithRetry(chunk);
            cacheService.updateEntryCache(entryScoreMap);
        }

    }

    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000) // 2 seconds delay between retries
    )
    private Map<String, Integer> saveChunkWithRetry(List<VoteDTO> voteChunk) {
          return voteRepository.saveVotesInBatch(voteChunk);

    }

    @Recover
    public void recover(Exception e, List<Vote> failedChunk) {
        // log and move forward
        System.err.println("Failed to save chunk after retries. Chunk size: " + failedChunk.size());
        e.printStackTrace();
        // optionally save failedChunk to a DLQ table
    }
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = mapper.readTree(messages.toString());
//        List<Vote> votes = new ArrayList<>();
//        if (root.isArray())
//        {
//            for (JsonNode innerArray : root)
//            {
//                // The inner array
//                if (innerArray.isArray())
//                {
//                    for (JsonNode item : innerArray)
//                    {
//                        String userId = item.get("userId").asText();
//                        String pollId = item.get("pollId").asText();
//                        String entryId = item.get("entryId").asText();
//                        int score = item.get("score").asInt();
//
//                        final Poll poll = pollRepository.findById(pollId).get();
//                        final Entry entry = entryRepository.findById(entryId).get();
//                        final User user = userRepository.findById(userId).get();
//
//                        Vote vote = new Vote();
//                        vote.setUser(user);
//                        vote.setPoll(poll);
//                        vote.setEntry(entry);
//                        vote.setScore(score);
//                        vote.setVoteDate(
//                                LocalDateTime.fromDateFields(new Date()));
//                        vote.setEntryId(entryId);
//                        vote.setPollId(pollId);
//                        vote.setUserId(userId);
//                        System.out.println("vote data-----" + vote.toString());
//                        votes.add(vote);
                        //                        System.out.println("UserId: " + userId);
                        //                        System.out.println("PollId: " + pollId);
                        //                        System.out.println("EntryId: " + entryId);
                        //                        System.out.println("Score: " + score);
                        //                        System.out.println("-------------------------");

//                    }
//                }
//            }
//        }
        //        for(String message : messages)
        //        {
        //            System.out.println("for db message ==> " +message);
        //        }
//        System.out.println("== going for batch insert in db....");
//        voteRepository.saveAll(votes);
//        System.out.println("=== db insert completed...");

//    }

//    @Recover
//    public void recover(){
//
//    }
}
