package com.app.Repositories;

import com.app.Entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEntryRepository extends JpaRepository<Entry, String>
{

    List<Entry> findByIdIn(List<String> entryIds);
}
