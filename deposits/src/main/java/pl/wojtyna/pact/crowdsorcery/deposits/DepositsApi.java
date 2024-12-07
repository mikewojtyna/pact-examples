package pl.wojtyna.pact.crowdsorcery.deposits;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/deposit")
public class DepositsApi {

    private AccountRepo accountRepo;

    public DepositsApi(AccountRepo accountRepo) {this.accountRepo = accountRepo;}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepositResult deposit(@RequestBody MakeADepositRequest makeADepositRequest) {
        // deposit logic
        var balance = accountRepo.load(UUID.fromString(makeADepositRequest.id())).deposit(makeADepositRequest);
        return new DepositResult(new Money(BigDecimal.valueOf(makeADepositRequest.amount()),
                                           makeADepositRequest.currency()),
                                 new Money(balance, makeADepositRequest.currency()),
                                 Status.SUCCESS);
    }
}
