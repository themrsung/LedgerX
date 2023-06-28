package oasis.ledgerx.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.classes.Market;
import oasis.ledgerx.stack.contract.ContractStack;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.util.FileUtil;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class of LedgerState
 */
public final class LedgerXState implements LedgerState {
    public LedgerXState() {
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

    // IO
    private static final String SAVE_PATH = "plugins/LedgerX";

    private LedgerXState(List<Actor> actors, List<ContractStack> contracts, List<Market> markets) {
        this.actors = actors;
        this.contracts = contracts;
        this.markets = markets;
    }

    /**
     * Returns loaded state from saved data
     * Will return an empty state if error occurs
     */
    @Nonnull
    public static LedgerXState load() {
        File path = new File(SAVE_PATH);
        if (!path.exists()) return new LedgerXState();

        List<Actor> actors = new ArrayList<>();
        List<ContractStack> contracts = new ArrayList<>();
        List<Market> markets = new ArrayList<>();

        File actorsFolder = new File(SAVE_PATH + "/actors");
        File contractsFolder = new File(SAVE_PATH + "/contracts");
        File marketsFolder = new File(SAVE_PATH + "/markets");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        if (actorsFolder.exists()) {
            File[] files = actorsFolder.listFiles();
            if (files != null) {
                for (File f : files) {
                    try {
                        actors.add(mapper.readValue(f, Actor.class));
                    } catch (IOException e) {
                        Bukkit.getLogger().info("[LedgerX] Error while loading data: " + e.getMessage());
                    }
                }
            }
        }

        if (contractsFolder.exists()) {
            File[] files = contractsFolder.listFiles();
            if (files != null) {
                for (File f : files) {
                    try {
                        contracts.add(mapper.readValue(f, ContractStack.class));
                    } catch (IOException e) {
                        Bukkit.getLogger().info("[LedgerX] Error while loading data: " + e.getMessage());
                    }
                }
            }
        }

        if (marketsFolder.exists()) {
            File[] files = marketsFolder.listFiles();
            if (files != null) {
                for (File f : files) {
                    try {
                        markets.add(mapper.readValue(f, Market.class));
                    } catch (IOException e) {
                        Bukkit.getLogger().info("[LedgerX] Error while loading data: " + e.getMessage());
                    }
                }
            }
        }

        Bukkit.getLogger().info("[LedgerX] Data loaded.");
        return new LedgerXState(actors, contracts, markets);
    }

    /**
     * Saves the state
     */
    @Override
    public void save() {
        File path = new File(SAVE_PATH);
        if (!path.exists() && !path.mkdirs()) {
            Bukkit.getLogger().info("[LedgerX] Failed to locate plugin directory.");
            return;
        }

        File actorsFolder = new File(SAVE_PATH + "/actors");
        File contractsFolder = new File(SAVE_PATH + "/contracts");
        File marketsFolder = new File(SAVE_PATH + "/markets");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        if (!actorsFolder.mkdirs() && !actorsFolder.exists()) {
            Bukkit.getLogger().info("[LedgerX] Error creating actors directory.");
        }

        try {
            FileUtils.cleanDirectory(actorsFolder);

            for (Actor actor : getActors()) {
                File f = new File(SAVE_PATH + "/actors/" + actor.getUniqueId() + ".yml");
                try {
                    mapper.writeValue(f, actor);
                } catch (IOException e) {
                    Bukkit.getLogger().info("[LedgerX] Error saving file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            Bukkit.getLogger().info("[LedgerX] Error cleaning actors directory.");
        }

        if (!contractsFolder.mkdirs() && !contractsFolder.exists()) {
            Bukkit.getLogger().info("[LedgerX] Error creating contracts directory.");
        }

        try {
            FileUtils.cleanDirectory(contractsFolder);

            for (ContractStack contract : getContracts()) {
                File f = new File(SAVE_PATH + "/contracts/" + UUID.randomUUID() + ".yml");
                try {
                    mapper.writeValue(f, contract);
                } catch (IOException e) {
                    Bukkit.getLogger().info("[LedgerX] Error saving file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            Bukkit.getLogger().info("[LedgerX] Error cleaning contracts directory.");
        }

        if (!marketsFolder.mkdirs() && !marketsFolder.exists()) {
            Bukkit.getLogger().info("[LedgerX] Error creating markets directory.");
        }

        try {
            FileUtils.cleanDirectory(marketsFolder);

            for (Market market : getMarkets()) {
                File f = new File(SAVE_PATH + "/markets/" + UUID.randomUUID() + ".yml");
                try {
                    mapper.writeValue(f, market);
                } catch (IOException e) {
                    Bukkit.getLogger().info("[LedgerX] Error saving file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            Bukkit.getLogger().info("[LedgerX] Error cleaning markets directory.");
            }

        Bukkit.getLogger().info("[LedgerX] Data saved.");
    }
}
