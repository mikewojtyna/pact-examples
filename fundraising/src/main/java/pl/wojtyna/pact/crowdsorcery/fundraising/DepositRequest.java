package pl.wojtyna.pact.crowdsorcery.fundraising;

import java.util.UUID;

public record DepositRequest(UUID id, int amount, String currency) {
}
