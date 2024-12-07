package pl.wojtyna.pact.crowdsorcery.deposits;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.UUID;

@Provider("DepositsContext")
@PactBroker(host = "localhost", port = "9292")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepositsProviderTest {

    @LocalServerPort
    private int port;
    @MockitoBean
    private AccountRepo accountRepo;

    @BeforeEach
    void setUp(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("balance of account with id 267f5380-aff4-4190-adc4-71e6a223b976 is $500")
    void depositBalanceIs500() {
        // it's generally a better idea to use mocks here, as it might be hard to ensure tests isolation when there are more tests being executed
        Mockito.when(accountRepo.load(UUID.fromString("267f5380-aff4-4190-adc4-71e6a223b976"))).thenReturn(new Account(
            UUID.fromString("267f5380-aff4-4190-adc4-71e6a223b976"),
            BigDecimal.valueOf(500),
            "USD"));
    }
}
