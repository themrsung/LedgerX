package oasis.ledgerx.state;


import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.classes.Market;
import oasis.ledgerx.inventory.contract.ContractStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public interface LedgerState {
    // ACTORS

    /**
     * Gets every actor in existence
     */
    List<Actor> getActors();

    /**
     * Gets a specific actor
     */
    @Nullable Actor getActor(UUID uniqueId);

    /**
     * Adds an actor to the state
     */
    void addActor(Actor actor);

    /**
     * Removes an actor from the state
     */
    void removeActor(Actor actor);

    // CONTRACTS

    /**
     * Gets all contracts in existence
     */
    List<ContractStack> getContracts();

    /**
     * Registers a contract
     */
    void addContract(ContractStack contract);

    /**
     * Removes a contract
     */
    void removeContract(ContractStack contract);

    // MARKETS

    /**
     * Gets all markets in existence
     */
    List<Market> getMarkets();

    /**
     * Adds a market
     */
    void addMarket(Market market);

    /**
     * Removes a market
     */
    void removeMarket(Market market);
}
