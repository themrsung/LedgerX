package oasis.ledgerx.state;


import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.inventory.contract.ContractStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public interface LedgerState {
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
}
