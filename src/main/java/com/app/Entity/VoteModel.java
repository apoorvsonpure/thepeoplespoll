package com.app.Entity;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class VoteModel
{
    @NotBlank(message = "user_id cannot be blank to cast a vote for an entry.")
    private String userId;

    @NotBlank(message = "poll_id cannot be blank to cast a vote for an entry.")
    private String pollId;

    @NotBlank(message = "entry_id cannot be blank to cast a vote for an entry.")
    private String entryId;

    @Range(min = 0, max = 10, message = "Please select a rating between 0 to 10.")
    private int score;

    public @NotBlank(message = "user_id cannot be blank to cast a vote for an entry.") String getUserId()
    {
        return userId;
    }

    public void setUserId(
            @NotBlank(message = "user_id cannot be blank to cast a vote for an entry.") String userId)
    {
        this.userId = userId;
    }

    public @NotBlank(message = "poll_id cannot be blank to cast a vote for an entry.") String getPollId()
    {
        return pollId;
    }

    public void setPollId(
            @NotBlank(message = "poll_id cannot be blank to cast a vote for an entry.") String pollId)
    {
        this.pollId = pollId;
    }

    public @NotBlank(message = "entry_id cannot be blank to cast a vote for an entry.") String getEntryId()
    {
        return entryId;
    }

    public void setEntryId(
            @NotBlank(message = "entry_id cannot be blank to cast a vote for an entry.") String entryId)
    {
        this.entryId = entryId;
    }

    @Range(min = 0, max = 10, message = "Please select a rating between 0 to 10.")
    public int getScore()
    {
        return score;
    }

    public void setScore(
            @Range(min = 0, max = 10, message = "Please select a rating between 0 to 10.") int score)
    {
        this.score = score;
    }
}
