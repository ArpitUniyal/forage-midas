package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.Incentive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class IncentivesClient {

    private final RestTemplate restTemplate;
    // URL specified in the task brief
    private static final String INCENTIVES_API_URL = "http://localhost:8080/incentive";

    // Inject RestTemplateBuilder via constructor for safety (assuming a RestTemplate bean is available)
    public IncentivesClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Posts the transaction to the external API and returns the incentive amount.
     */
    public BigDecimal postTransaction(Transaction transaction) {
        try {
            // Post the Transaction object and receive the Incentive object
            Incentive response = restTemplate.postForObject(
                    INCENTIVES_API_URL,
                    transaction,
                    Incentive.class
            );

            // Return the amount, or 0 if the response is null/missing amount.
            return response != null && response.getAmount() != null
                    ? response.getAmount()
                    : BigDecimal.ZERO;
        } catch (Exception e) {
            // Log error but return 0 to prevent transaction failure (Black Box principle)
            return BigDecimal.ZERO;
        }
    }
}
