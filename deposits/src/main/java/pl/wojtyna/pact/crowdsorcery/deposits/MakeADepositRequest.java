package pl.wojtyna.pact.crowdsorcery.deposits;

public record MakeADepositRequest(String id, int amount, String currency) {
}
