package com.app.Services;

import com.app.Entity.VoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduledKafkaConsumerService {

    private final KafkaConsumer<String, String> kafkaConsumer;
    private final VoteProcessorService voteProcessor;
    private final ObjectMapper objectMapper;

    @Autowired
    public ScheduledKafkaConsumerService(KafkaConsumer<String, String> kafkaConsumer,
            VoteProcessorService voteProcessor, ObjectMapper objectMapper) {
        this.kafkaConsumer = kafkaConsumer;
        this.voteProcessor = voteProcessor;
        this.objectMapper = objectMapper;
    }

    @Scheduled(cron = "0 */3 * * * ?") // every 10 minutes
    public void consumeVotesInBatch() {
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(5));
        List<VoteDTO> allVotes = new ArrayList<>();
        System.out.println(" Scheduled job started........" );
        for (ConsumerRecord<String, String> record : records) {
//            messages.add(record.value());
//            System.out.println("recoed ==> " +record);
//            System.out.println("record.value() ==>"+ record.value());
            List<VoteDTO> votes = null;
            try
            {
                votes = objectMapper.readValue(
                        record.value(),
                        new TypeReference<List<VoteDTO>>() {}
                );
            }
            catch (JsonProcessingException e)
            {
                throw new RuntimeException(e);
            }
            allVotes.addAll(votes);
        }

        if (!allVotes.isEmpty()) {
            System.out.println("all vote list ==> " + allVotes);
            try
            {
                voteProcessor.processVotes(allVotes);
            }
            catch (JsonProcessingException e)
            {
                throw new RuntimeException(e);
            }
            kafkaConsumer.commitSync();
            System.out.println(" Scheduled job completed........" );
        }
    }
}
