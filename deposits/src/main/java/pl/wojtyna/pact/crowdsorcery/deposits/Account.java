package pl.wojtyna.pact.crowdsorcery.deposits;

import java.math.BigDecimal;
import java.util.UUID;

public record Account(UUID id, BigDecimal balance, String currency) {

    public BigDecimal deposit(MakeADepositRequest makeADepositRequest) {
        return balance.add(BigDecimal.valueOf(makeADepositRequest.amount()));
    }
}
