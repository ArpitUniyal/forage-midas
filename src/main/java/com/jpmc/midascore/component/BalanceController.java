package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Balance; // The provided class to return
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

@RestController
public class BalanceController {

    private final UserRepository userRepository;

    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Exposes GET /balance endpoint, accepting userId, and returns a Balance object.
     */
    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {
        // Find the user's balance
        BigDecimal finalBalance = userRepository.findById(userId)
                .map(user -> user.getBalance())
                .orElse(BigDecimal.ZERO);

        // **TASK 5 REQUIREMENT:** Return a new instance of the Balance class
        return new Balance(finalBalance);
    }
}