package com.app.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import java.util.List;

@Entity
public class Entry extends BaseEntity
{

   @ManyToOne
   @JoinColumn( name = "poll_id", referencedColumnName = "id")
   @JsonIgnore
   private Poll poll;

   @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL)
   private List<Vote> votes;

   private LocalDateTime createdAt;

   private String attributes;

   public Poll getPoll()
   {
      return poll;
   }

   public void setPoll(Poll poll)
   {
      this.poll = poll;
   }

   public List<Vote> getVotes()
   {
      return votes;
   }

   public void setVotes(List<Vote> votes)
   {
      this.votes = votes;
   }

   public LocalDateTime getCreatedAt()
   {
      return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt)
   {
      this.createdAt = createdAt;
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
