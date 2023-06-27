package oasis.ledgerx.classes;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.inventory.asset.AssetStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EconomicActor implements Actor {
    public EconomicActor() {
        this.uniqueId = UUID.randomUUID();
        this.assets = new ArrayList<>();
    }

    private final UUID uniqueId;
    private final List<AssetStack> assets;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public List<AssetStack> getAssets() {
        return new ArrayList<>(assets);
    }

    @Override
    public void addAsset(AssetStack asset) {
        for (AssetStack stack : getAssets()) {
            if (stack.getSymbol().equalsIgnoreCase(asset.getSymbol())) {
                stack.addQuantity(asset.getQuantity());
                return;
            }
        }

        assets.add(asset);
    }

    @Override
    public void removeAsset(AssetStack asset) {
        for (AssetStack stack : getAssets()) {
            if (stack.getSymbol().equalsIgnoreCase(asset.getSymbol())) {
                stack.removeQuantity(asset.getQuantity());
                return;
            }
        }
    }
}
