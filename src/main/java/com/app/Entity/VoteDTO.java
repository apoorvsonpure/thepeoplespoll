package com.app.Entity;

import org.joda.time.LocalDateTime;

import java.util.UUID;

public class VoteDTO
{

    private UUID userId;
    private UUID pollId;
    private UUID entryId;
    private Integer score;
    private LocalDateTime voteDate;

    // Constructors
    public VoteDTO() {}

    public VoteDTO(UUID userId, UUID pollId, UUID entryId, Integer score, LocalDateTime voteDate) {
        this.userId = userId;
        this.pollId = pollId;
        this.entryId = entryId;
        this.score = score;
        this.voteDate = voteDate;
    }

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getPollId() {
        return pollId;
    }

    public void setPollId(UUID pollId) {
        this.pollId = pollId;
    }

    public UUID getEntryId() {
        return entryId;
    }

    public void setEntryId(UUID entryId) {
        this.entryId = entryId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getVoteDate()
    {
        return voteDate;
    }

    public void SetVoteDate(LocalDateTime voteDate)
    {
        this.voteDate = voteDate;
    }


    @Override
    public String toString() {
        return "VoteDTO{" +
                "userId=" + userId +
                ", pollId=" + pollId +
                ", entryId=" + entryId +
                ", score=" + score +
                ", voteDate=" + voteDate +
                '}';
    }
}
