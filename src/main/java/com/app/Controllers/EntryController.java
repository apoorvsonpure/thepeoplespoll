package com.app.Controllers;

import com.app.Entity.Entry;
import com.app.Entity.EntryModel;
import com.app.Entity.Poll;
import com.app.Services.CacheService;
import com.app.Services.EntryService;
import com.app.Services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class EntryController
{

    private final EntryService entryService;

    private final CacheService cacheService;

    @Autowired
    public EntryController(EntryService entryService, CacheService cacheService)
    {   super();
        this.entryService = entryService;
        this.cacheService = cacheService;
    }

    @PostMapping(value = "/api/entry/create/{poll_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Poll> createEntry(@RequestBody List<EntryModel> entryModelList,
            @PathVariable String poll_id)
    {
        final Poll poll = entryService.addPollEntry(poll_id, entryModelList);
        if(poll != null)
            return new ResponseEntity<>(poll, HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/api/entry/{poll_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Entry>> getAllPollEntry(@PathVariable String poll_id)
    {
        final List<Entry> allPollEntry = entryService.getAllPollEntry(poll_id);
        if(allPollEntry != null)
            return new ResponseEntity<>(allPollEntry, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping(value = "/api/entry/update_entry/{poll_id}")
    public ResponseEntity<Entry> updatePollEntry(@PathVariable String poll_id, @RequestParam String entryId,
            @RequestBody EntryModel entryModel)
    {
        final Entry updatedPollEntry = entryService.updatePollEntry(poll_id, entryId,
                entryModel);
        if(updatedPollEntry != null)
            return new ResponseEntity<>(updatedPollEntry,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/api/entry/{poll_id}")
    public ResponseEntity<String> deletePollEntry(@PathVariable String poll_id, @RequestParam String entryId)
    {
        final String msg = entryService.deletePollEntry(poll_id, entryId);
        if(msg.contains("success"))
            return new ResponseEntity<>(msg, HttpStatus.OK);
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }


    @GetMapping(value = "/api/entry/get_score/{entryId}")
    public ResponseEntity<String> getEntryScore(@PathVariable String entryId)
    {
       // final Integer score = cacheService.getScore(UUID.fromString(entryId));
        final Integer score = cacheService.getScore(entryId);
        return new ResponseEntity<>(String.valueOf(score), HttpStatus.OK);
    }
}
