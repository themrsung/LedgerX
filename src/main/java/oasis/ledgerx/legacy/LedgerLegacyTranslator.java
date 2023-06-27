package oasis.ledgerx.legacy;

import oasis.ledgerx.LedgerX;
import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.classes.Market;
import oasis.ledgerx.stack.contract.ContractStack;
import oasis.ledgerx.state.LedgerState;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * An interface for translating LedgerX data into Ledger data and vice versa
 * Can be ignored if you are not migrating from jbs.ledger.Ledger
 * All methods are prefixed with LX or suffixed with X
 */
public interface LedgerLegacyTranslator {
    /**
     * Only method that requires implementation
     * Other methods are derivative of this
     */
    @Nonnull
    LedgerX getLedgerX();

    default LedgerState getLXState() {
        return getLedgerX().getState();
    }


    default List<Actor> getLXActors() {
        return getLXState().getActors();
    }

    default void addLXActor(Actor actor) {
        getLXState().addActor(actor);
    }

    default void removeLXActor(Actor actor) {
        getLXState().removeActor(actor);
    }


    default List<ContractStack> getLXContracts() {
        return getLXState().getContracts();
    }

    default void addLXContract(ContractStack contract) {
        getLXState().addContract(contract);
    }

    default void removeLXContract(ContractStack contract) {
        getLXState().removeContract(contract);
    }


    default List<Market> getLXMarkets() {
        return getLXState().getMarkets();
    }

    default void addLXMarket(Market market) {
        getLXState().addMarket(market);
    }

    default void removeLXMarket(Market market) {
        getLXState().removeMarket(market);
    }

}
