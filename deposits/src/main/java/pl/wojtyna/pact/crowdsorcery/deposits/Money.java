package pl.wojtyna.pact.crowdsorcery.deposits;

import java.math.BigDecimal;

public record Money(BigDecimal amount, String currency) {
}
