package pl.wojtyna.pact.crowdsorcery.fundraising;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class DepositContextClient {

    private final WebClient webClient;

    public DepositContextClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public DepositResult deposit(Money money) {
        return webClient.post()
                        .uri("/api/deposit")
                        .bodyValue(new DepositRequest(findAccountIdOfCurrentUser(),
                                                      money.amount().intValue(),
                                                      money.currency()))
                        .retrieve().toEntity(DepositResult.class).block().getBody();
    }

    private UUID findAccountIdOfCurrentUser() {
        return UUID.fromString("267f5380-aff4-4190-adc4-71e6a223b976");
    }
}
