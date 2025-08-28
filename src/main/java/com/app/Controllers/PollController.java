package com.app.Controllers;

import com.app.Entity.*;
import com.app.Services.PollService;
import com.app.Utils.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PollController
{

    private final PollService pollService;

    @Autowired
    public PollController(PollService pollService)
    {   super();
        this.pollService = pollService;
    }

    @PostMapping(value = "/api/poll/create", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Poll> createPoll(@RequestBody PollModel pollModel)
    {
        final List<EntryModel> entryModelList = pollModel.getEntryModel();
        final String entryModelJson = CommonUtilities.convertObjectToJson(entryModelList);
        final List<Entry> entryList = CommonUtilities.convertJsonToEntryList(entryModelJson);

        final String pollModelJson = CommonUtilities.convertObjectToJson(pollModel);
        final Poll poll = CommonUtilities.convertJsonToObject(pollModelJson, Poll.class);

        final Poll savedPoll = pollService.createPoll(poll, entryList);
        return new ResponseEntity<Poll>(savedPoll, HttpStatus.CREATED);
    }

    @GetMapping(value = "/api/poll/get_polls", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Poll>> getPolls()
    {
        final List<Poll> allPolls = pollService.getAllPolls();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @GetMapping(value = "/api/poll/get_poll/{poll_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Poll> getPoll(@PathVariable String poll_id)
    {
        final Poll poll = pollService.getPoll(poll_id);
        return new ResponseEntity<>(poll, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/poll/{poll_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePoll(@PathVariable String poll_id)
    {
        final String msg = pollService.deletePoll(poll_id);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }


    @PutMapping(value = "/api/poll/update_poll/{poll_id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Poll> updatePoll(@RequestBody PollModel pollModel, @PathVariable String poll_id)
    {

        final Poll updatedPoll = pollService.updatePoll(pollModel, poll_id);
        return new ResponseEntity<>(updatedPoll, HttpStatus.OK);
    }

    @PatchMapping(value = "/api/poll/update_status/{poll_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePollStatus(@PathVariable String poll_id,
            @RequestParam(name = "status") Status status)
    {

        final String result = pollService.updatePollStatus(poll_id, status);
        if(result.contains("success"))
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
