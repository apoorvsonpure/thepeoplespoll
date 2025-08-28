package com.app.Services;

import com.app.Entity.*;
import com.app.Repositories.IPollRepository;
import com.app.Utils.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PollService
{

    @Autowired
    private IPollRepository pollRepo;

    public Poll createPoll(Poll poll, List<Entry> entryList)
    {
        poll.setPollType(PollType.NOMINATION);
        poll.setStatus(Status.UPCOMING);
        entryList.forEach(entry -> entry.setPoll(poll));
        poll.setEntries(entryList);
//        poll.setStartTime();
//        poll.setEndTime();
        return pollRepo.save(poll);
    }

    public List<Poll> getAllPolls()
    {
       return pollRepo.findAll();

    }

    public Poll getPoll(String pollId)
    {
          return pollRepo.findById(pollId).orElse(null);
    }

    public String deletePoll(String pollId)
    {
        pollRepo.deleteById(pollId);
        return "Poll deleted successfully";
    }

    public Poll updatePoll(PollModel pollModel, String pollId)
    {
        Poll poll = pollRepo.findById(pollId).orElse(null);
        if( poll != null)
        {
            poll.setName(pollModel.getName());
            poll.setDescription(pollModel.getDescription());
            poll.setTitle(pollModel.getTitle());
            poll.setAttributes(pollModel.getAttributes());
            poll.setStartTime(pollModel.getStartTime());
            poll.setEndTime(pollModel.getEndTime());
            return pollRepo.save(poll);
        }
        return null;
    }

    public String updatePollStatus(String pollId, Status status)
    {
        Poll dbPoll = pollRepo.findById(pollId).orElse(null);
        if(dbPoll != null)
        {
                dbPoll.setStatus(status);
                pollRepo.save(dbPoll);
                return "Status updated successfully";
        }
        return "Poll not found, status not updated";
    }


  //  @Scheduled(cron = "0 */1 * * * *")
    void changePollType(){
        final List<Poll> allPolls = pollRepo.findAll();
        allPolls.forEach(poll -> poll.setStatus(Status.UPCOMING));
        pollRepo.saveAll(allPolls);
    }
}
