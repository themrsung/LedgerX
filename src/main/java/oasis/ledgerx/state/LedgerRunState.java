package oasis.ledgerx.state;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.classes.Market;
import oasis.ledgerx.stack.contract.ContractStack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class of LedgerState
 */
public final class LedgerRunState implements LedgerState, Serializable {
    public LedgerRunState() {
        this.actors = new ArrayList<>();
        this.contracts = new ArrayList<>();
        this.markets = new ArrayList<>();
    }

    private final List<Actor> actors;
    private final List<ContractStack> contracts;
    private final List<Market> markets;

    @Override
    public List<Actor> getActors() {
        return new ArrayList<>(actors);
    }

    @Override
    public void addActor(Actor actor) {
        actors.add(actor);
    }

    @Override
    public void removeActor(Actor actor) {
        actors.remove(actor);
    }

    @Override
    public List<ContractStack> getContracts() {
        return new ArrayList<>(contracts);
    }

    @Override
    public void addContract(ContractStack contract) {
        for (ContractStack stack : getContracts()) {
            if (stack.getSymbol().equalsIgnoreCase(contract.getSymbol())) {
                stack.addQuantity(contract.getQuantity());
                return;
            }
        }

        contracts.add(contract);
    }

    @Override
    public void removeContract(ContractStack contract) {
        for (ContractStack stack : getContracts()) {
            if (stack.getSymbol().equalsIgnoreCase(contract.getSymbol())) {
                stack.removeQuantity(contract.getQuantity());
                cleanContracts();
                return;
            }
        }
    }

    private void cleanContracts() {
        contracts.removeIf(c -> c.getQuantity() == 0L);
    }

    @Override
    public List<Market> getMarkets() {
        return new ArrayList<>(markets);
    }

    @Override
    public void addMarket(Market market) {
        markets.add(market);
    }

    @Override
    public void removeMarket(Market market) {
        markets.remove(market);
    }
}
