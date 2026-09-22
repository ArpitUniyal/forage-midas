// Inside src/main/java/com/jpmc/midascore/entity/TransactionRecord.java

package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The sender and recipient links (Many-to-One hint from the task)
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserRecord recipient;

    private BigDecimal amount;
    private LocalDateTime timestamp;
    private BigDecimal incentiveAmount;


    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // You also need the getId() getter (or find another method to retrieve the ID)
    public Long getId() {
        return this.id;
    }

    // Add Getter and Setter for incentiveAmount
    public BigDecimal getIncentiveAmount() {
        return incentiveAmount;
    }

    public void setIncentiveAmount(BigDecimal incentiveAmount) {
        this.incentiveAmount = incentiveAmount;
    }
}