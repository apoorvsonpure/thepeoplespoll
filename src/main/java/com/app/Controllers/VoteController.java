package com.app.Controllers;

import com.app.Entity.Vote;
import com.app.Entity.VoteModel;
import com.app.Services.VoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class VoteController
{
    private final VoteService voteService;

    @Autowired
    public VoteController (VoteService voteService)
    {   super();
        this.voteService = voteService;
    }

    @PostMapping(value = "/api/vote/cast_vote")
    public ResponseEntity<String> castVote(@RequestBody @Valid List<VoteModel>  voteModelList)
    {
        final String dbVotes = voteService.castVote(voteModelList);
        if(!dbVotes.isEmpty())
            return new ResponseEntity<>(dbVotes, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
