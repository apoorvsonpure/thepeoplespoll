package com.app.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.joda.time.LocalDateTime;

@Entity
@Table(
        name = "vote",
        schema = "thepeoplespoll",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "poll_id", "entry_id"})
)
public class Vote extends BaseEntity
{
    @JsonIgnore
    @ManyToOne
    @JoinColumn( name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "poll_id", referencedColumnName = "id")
    private Poll poll;

    @JsonIgnore
    @ManyToOne
    @JoinColumn( name = "entry_id", referencedColumnName = "id")
    private Entry entry;

    private Integer score;

    private LocalDateTime voteDate;

    @Transient
    private String userId;

    @Transient
    private String pollId;

    @Transient
    private String entryId;

    public Vote()
    { super(); }

    public Vote(User user, Poll poll, Entry entry, Integer score,
            LocalDateTime voteDate, String userId, String pollId,
            String entryId)
    {
        this.user = user;
        this.poll = poll;
        this.entry = entry;
        this.score = score;
        this.voteDate = voteDate;
        this.userId = userId;
        this.pollId = pollId;
        this.entryId = entryId;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Poll getPoll()
    {
        return poll;
    }

    public void setPoll(Poll poll)
    {
        this.poll = poll;
    }

    public Entry getEntry()
    {
        return entry;
    }

    public void setEntry(Entry entry)
    {
        this.entry = entry;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    public LocalDateTime getVoteDate()
    {
        return voteDate;
    }

    public void setVoteDate(LocalDateTime voteDate)
    {
        this.voteDate = voteDate;
    }

    public String getUserId()
    {
        if(this.user != null && this.user.getId() != null)
            return this.user.getId();
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getPollId()
    {
        if(this.poll != null && this.poll.getId() != null)
            return this.poll.getId();
        return pollId;
    }

    public void setPollId(String pollId)
    {
        this.pollId = pollId;
    }

    public String getEntryId()
    {
        if(this.entry != null && this.entry.getId() != null)
            return this.entry.getId();
        return entryId;
    }

    public void setEntryId(String entryId)
    {
        this.entryId = entryId;
    }
}
