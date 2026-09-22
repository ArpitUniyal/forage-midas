package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionProcessor {

    private static final Logger log = LoggerFactory.getLogger(TransactionProcessor.class);

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final IncentivesClient incentivesClient; // NEW: Client for API calls

    public TransactionProcessor(UserRepository userRepository,
                                TransactionRepository transactionRepository,
                                IncentivesClient incentivesClient) { // NEW: Constructor injection
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.incentivesClient = incentivesClient;
    }

    @Transactional
    public void processTransaction(Transaction rawTransaction) {

        // 1. FETCH USERS
        UserRecord sender = userRepository.findById(rawTransaction.getSenderId());
        UserRecord recipient = userRepository.findById(rawTransaction.getRecipientId());

        BigDecimal amount = rawTransaction.getAmount(); // Assumes getAmount() returns BigDecimal

        // 2. VALIDATION CHECK
        if (!isValidTransaction(sender, recipient, amount)) {
            log.warn("Transaction discarded: Validation failed for senderId {} and amount {}.",
                    rawTransaction.getSenderId(), amount);
            return;
        }

        // TASK 4 INTEGRATION: Get Incentive
        BigDecimal incentiveAmount = incentivesClient.postTransaction(rawTransaction);

        // 3. UPDATE BALANCES (Core Logic)

        // Sender: Deduct transaction amount (Incentive is NOT deducted)
        sender.setBalance(sender.getBalance().subtract(amount));
        userRepository.save(sender);

        // Recipient: Add transaction amount PLUS the incentive
        BigDecimal totalRecipientIncrease = amount.add(incentiveAmount);
        recipient.setBalance(recipient.getBalance().add(totalRecipientIncrease));
        userRepository.save(recipient);

        // 4. PERSIST TRANSACTION RECORD
        TransactionRecord record = createTransactionRecord(sender, recipient, amount, incentiveAmount);
        transactionRepository.save(record);

        log.info("Successfully recorded transaction. Incentive: {}", incentiveAmount);
    }

    private boolean isValidTransaction(UserRecord sender, UserRecord recipient, BigDecimal amount) {
        // Condition 1 & 2: The sender/recipient IDs are valid (exist)
        if (sender == null || recipient == null) {
            return false;
        }

        // Condition 3: The sender has a balance greater than or equal to the transaction amount
        if (sender.getBalance().compareTo(amount) < 0) {
            return false;
        }

        return true;
    }

    // UPDATED HELPER METHOD to include incentive
    private TransactionRecord createTransactionRecord(UserRecord sender, UserRecord recipient, BigDecimal amount, BigDecimal incentiveAmount) {
        TransactionRecord record = new TransactionRecord();
        record.setSender(sender);
        record.setRecipient(recipient);
        record.setAmount(amount);
        record.setIncentiveAmount(incentiveAmount); // NEW: Set incentive amount
        record.setTimestamp(LocalDateTime.now());

        return record;
    }
}