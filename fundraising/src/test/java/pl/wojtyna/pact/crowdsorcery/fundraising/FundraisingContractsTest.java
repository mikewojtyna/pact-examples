package pl.wojtyna.pact.crowdsorcery.fundraising;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@PactConsumerTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FundraisingContractsTest {

    @Autowired
    private InvestIntoProject investIntoProject;

    @Pact(provider = "DepositsContext", consumer = "FundraisingContext")
    RequestResponsePact shouldReturnDepositResult(PactDslWithProvider builder) {
        return builder
            .given("balance of account with id 267f5380-aff4-4190-adc4-71e6a223b976 is $500")
            .uponReceiving("deposit $100")
            .path("/api/deposit")
            .method("POST")
            .body(
                """
                {
                    "id": "267f5380-aff4-4190-adc4-71e6a223b976",
                    "amount": 100,
                    "currency": "USD"
                }
                """
            )
            .willRespondWith()
            .status(201)
            .body(
                """
                {
                    "amount": {
                        "amount": 100,
                        "currency": "USD"
                    },
                    "balance": {
                        "amount": 600,
                        "currency": "USD"
                    },
                    "status": "SUCCESS"
                }
                """,
                "application/json"
            )
            .toPact();
    }

    // @formatter:off
    @DisplayName(
        """
         given deposit balance of account with id 267f5380-aff4-4190-adc4-71e6a223b976 is $500
         when deposit $100
         then deposit balance is $600
        """
    )
    // @formatter:on
    @Test
    @PactTestFor(
        pactMethod = "shouldReturnDepositResult",
        pactVersion = PactSpecVersion.V3,
        port = "9999"
    )
    void test() {
        // when
        var balance = investIntoProject.invest(UUID.randomUUID(), new Money(BigDecimal.valueOf(100), "USD"));

        // then
        assertThat(balance).isEqualTo(new Money(BigDecimal.valueOf(600), "USD"));
    }
}
