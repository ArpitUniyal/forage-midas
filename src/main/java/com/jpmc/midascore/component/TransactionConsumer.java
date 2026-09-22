package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// NOTE: We don't need Logger imports if we remove the logging code
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

@Component
public class TransactionConsumer {

    // Inject the TransactionProcessor, which holds the business logic (validation/saving)
    private final TransactionProcessor processor;

    // Use constructor injection to get the processor instance
    public TransactionConsumer(TransactionProcessor processor) {
        this.processor = processor;
    }

    /**
     * Listens to the Kafka topic and immediately passes the message to the
     * processor for validation, balance adjustment, and persistence (Task 3).
     */
    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
    public void handleTransaction(Transaction transaction) {

        // TASK 3 IMPLEMENTATION: Delegate the transaction to the service layer
        processor.processTransaction(transaction);

        // Old logging code is removed or commented out.
    }
}