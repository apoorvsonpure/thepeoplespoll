package com.app.Services;



import com.app.Entity.*;
import com.app.Repositories.IEntryRepository;
import com.app.Repositories.IPollRepository;

import com.app.Repositories.IVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class VoteService
{

    private final IPollRepository pollRepository;
    private final IEntryRepository entryRepository;
    private KafkaTemplate<String, List<VoteModel>> kafkaTemplate;

    @Autowired
    public VoteService(IVoteRepository voteRepository, IPollRepository pollRepository, IEntryRepository entryRepository,KafkaTemplate kafkaTemplate )
    {
        super();
        this.pollRepository = pollRepository;
        this.entryRepository = entryRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public String castVote(List<VoteModel> voteModelList)
    {
        if(voteModelList != null &&  !voteModelList.isEmpty())
        {
           User  loggedInUser  = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final String pollId = voteModelList.getFirst().getPollId();

            final Poll poll = pollRepository.findById(pollId).orElse(null);
            if(poll == null)
                return null;

            final List<String> entryIds = voteModelList.stream()
                    .map(VoteModel::getEntryId).toList();
            final List<Entry> entries = entryRepository.findByIdIn(entryIds);

            if(entryIds.size() != entries.size())
            {
                final List<String> dbEntryIds = entries.stream()
                        .map(entry -> entry.getId()).toList();
                entryIds.removeAll(dbEntryIds);
                if(entryIds.size() != 0)
                    return null;
            }

            kafkaTemplate.send("test123",voteModelList);
            return "Vote sent to kafka topic";


//
//            final Map<String, Entry> entryObjsMap = entries.stream()
//                    .collect(Collectors.toMap(Entry::getId, entry -> entry));
//
////            final String jsonVoteList = CommonUtilities.convertObjectToJson(voteModelList);
////            final List<Vote> votes = CommonUtilities.convertJsonToVoteList(jsonVoteList);
//
////            votes.forEach(vote ->  { vote.setUser(loggedInUser);
////                vote.setPoll(poll);});
//
//            for(VoteModel voteModel : voteModelList)
//            {
//                Vote vote = new Vote();
//                vote.setUser(loggedInUser);
//                vote.setPoll(poll);
//                vote.setEntry(entryObjsMap.get(voteModel.getEntryId()));
//                vote.setScore(voteModel.getScore());
//                voteRepository.save(vote);
//            }
//
//            return voteRepository.findByUser(loggedInUser);
        }
        return null;
    }
}
