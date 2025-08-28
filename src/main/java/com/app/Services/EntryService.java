package com.app.Services;

import com.app.Entity.Entry;
import com.app.Entity.EntryModel;
import com.app.Entity.Poll;
import com.app.Repositories.IEntryRepository;
import com.app.Repositories.IPollRepository;
import com.app.Utils.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EntryService
{

    private final IEntryRepository entryRepository;
    private final IPollRepository pollRepository;

    @Autowired
    public EntryService (IEntryRepository entryRepository, IPollRepository pollRepository)
    {   super();
        this.entryRepository = entryRepository;
        this.pollRepository = pollRepository;
    }

    public Poll addPollEntry(String pollId, List<EntryModel> entryModelList)
    {
        Poll dbPoll = pollRepository.findById(pollId).orElse(null);
        if(dbPoll != null)
        {
            final String entryModelJson = CommonUtilities.convertObjectToJson(entryModelList);
            final List<Entry> entryList = CommonUtilities.convertJsonToEntryList(entryModelJson);
            entryList.forEach(entry -> entry.setPoll(dbPoll));
            dbPoll.getEntries().addAll(entryList);
            return pollRepository.save(dbPoll);

        }
        return dbPoll;
    }

    public List<Entry> getAllPollEntry(String pollId)
    {
        Poll dbPoll = pollRepository.findById(pollId).orElse(null);
        if(dbPoll != null)
        {
            return dbPoll.getEntries();
        }
        return null;
    }

    public Entry updatePollEntry(String pollId, String entryId, EntryModel entryModel)
    {
        Poll dbPoll = pollRepository.findById(pollId).orElse(null);
        if(dbPoll != null)
        {
            Entry dbEntry = entryRepository.findById(entryId).orElse(null);
            if(dbEntry != null)
            {
                dbEntry.setName(entryModel.getName());
                dbEntry.setDescription(entryModel.getDescription());
                dbEntry.setAttributes(entryModel.getAttributes());
                return entryRepository.save(dbEntry);
            }
        }
        return null;
    }

    public String deletePollEntry(String pollId, String entryId)
    {
        Poll dbPoll = pollRepository.findById(pollId).orElse(null);
        if(dbPoll != null)
        {
             entryRepository.deleteById(entryId);
             return "successfull";
        }
        return "Failed to delete entry. Unable to find the mentioned Poll.";
    }
}
