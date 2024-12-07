package pl.wojtyna.pact.crowdsorcery.deposits;

import java.util.UUID;

public interface AccountRepo {
    Account load(UUID accountId);
    void save(Account account);
}
