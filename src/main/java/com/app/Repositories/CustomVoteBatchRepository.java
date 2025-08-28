package com.app.Repositories;

import com.app.Entity.VoteDTO;

import java.util.List;
import java.util.Map;

public interface CustomVoteBatchRepository
{
    Map<String, Integer> saveVotesInBatch(List<VoteDTO> votes);
}
