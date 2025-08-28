package com.app.Entity;

import jakarta.validation.constraints.NotBlank;

public class EntryModel
{
    @NotBlank(message = "Entry name should not be Blank.")
    private String name;

    @NotBlank(message = "Atleast provide 1 line of description for entry.")
    private String description;

    private String attributes;

//    @NotBlank(message = "poll_id is mandatory to provide an entry.")
//    private String poll_id;

    public @NotBlank(message = "Entry name should not be Blank.") String getName()
    {
        return name;
    }

    public void setName(
            @NotBlank(message = "Entry name should not be Blank.") String name)
    {
        this.name = name;
    }

    public @NotBlank(message = "Atleast provide 1 line of description for entry.") String getDescription()
    {
        return description;
    }

    public void setDescription(
            @NotBlank(message = "Atleast provide 1 line of description for entry.") String description)
    {
        this.description = description;
    }

    public String getAttributes()
    {
        return attributes;
    }

    public void setAttributes(String attributes)
    {
        this.attributes = attributes;
    }

//    public @NotBlank(message = "poll_id is mandatory to provide an entry.") String getPoll_id()
//    {
//        return poll_id;
//    }
//
//    public void setPoll_id(
//            @NotBlank(message = "poll_id is mandatory to provide an entry.") String poll_id)
//    {
//        this.poll_id = poll_id;
//    }
}
