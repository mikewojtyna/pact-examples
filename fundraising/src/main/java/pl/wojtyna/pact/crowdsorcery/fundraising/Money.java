package pl.wojtyna.pact.crowdsorcery.fundraising;

import java.math.BigDecimal;

public record Money(BigDecimal amount, String currency) {
}
