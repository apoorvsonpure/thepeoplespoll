package com.app.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Poll extends BaseEntity
{

    private String title;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Entry> entries;

    @Enumerated(EnumType.STRING)
    private PollType pollType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String attributes;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<Entry> getEntries()
    {
        return entries;
    }

    public void setEntries(List<Entry> entries)
    {
        this.entries = entries;
    }

    public PollType getPollType()
    {
        return pollType;
    }

    public void setPollType(PollType pollType)
    {
        this.pollType = pollType;
    }

    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime)
    {
        this.endTime = endTime;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public String getAttributes()
    {
        return attributes;
    }

    public void setAttributes(String attributes)
    {
        this.attributes = attributes;
    }
}
