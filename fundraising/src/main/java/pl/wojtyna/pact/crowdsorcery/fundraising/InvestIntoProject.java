package pl.wojtyna.pact.crowdsorcery.fundraising;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvestIntoProject {

    private DepositContextClient depositClient;

    public InvestIntoProject(DepositContextClient depositClient) {this.depositClient = depositClient;}

    public Money invest(UUID projectId, Money money) {
        doSomeInvestmentLogic();
        return depositClient.deposit(money).balance();
    }

    private void doSomeInvestmentLogic() {

    }
}
