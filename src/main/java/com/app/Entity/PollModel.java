package com.app.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

import java.util.List;

public class PollModel
{

    @NotBlank(message = "Poll Name should not be blank")
    private String name;

    @NotBlank(message = "Atleast provide 1 line of description about the poll.")
    private String description;

    @NotBlank( message = "Title should not be blank.")
    private String title;

    //@NotBlank(message =  "To create a Poll, please enter atleast 1 entries, new entries can be added later.")
    @Size(min = 0, max = 10)
    private List<EntryModel> entryModel;

    @NotBlank(message = "Please mention Poll's start date.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

   @NotBlank(message = "Please mention Poll's end date.")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private String attributes;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<EntryModel> getEntryModel()
    {
        return entryModel;
    }

    public void setEntryModel(List<EntryModel> entryModel)
    {
        this.entryModel = entryModel;
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

    public String getAttributes()
    {
        return attributes;
    }

    public void setAttributes(String attributes)
    {
        this.attributes = attributes;
    }
}
