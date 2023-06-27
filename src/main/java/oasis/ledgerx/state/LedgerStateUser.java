package oasis.ledgerx.state;

import oasis.ledgerx.LedgerX;
import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.classes.Market;
import oasis.ledgerx.stack.contract.ContractStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Interface for using LedgerState
 * Inlines frequently used getter calls
 */
public interface LedgerStateUser {
    @Nonnull
    LedgerX getLx();
    @Nonnull
    default LedgerState getState() {
        return getLx().getState();
    }

    default List<Actor> getActors() {
        return getState().getActors();
    }

    default void addActor(Actor actor) {
        getState().addActor(actor);
    }

    default void removeActor(Actor actor) {
        getState().removeActor(actor);
    }

    default List<ContractStack> getContracts() {
        return getState().getContracts();
    }

    default void addContract(ContractStack contract) {
        getState().addContract(contract);
    }

    default void removeContract(ContractStack contract) {
        getState().removeContract(contract);
    }

    default List<Market> getMarkets() {
        return getState().getMarkets();
    }

    default void addMarket(Market market) {
        getState().addMarket(market);
    }

    default void removeMarket(Market market) {
        getState().removeMarket(market);
    }
}
