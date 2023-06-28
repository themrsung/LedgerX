package oasis.ledgerx.classes;

import oasis.ledgerx.actor.Actor;
import oasis.ledgerx.actor.ActorType;
import oasis.ledgerx.stack.asset.AssetStack;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class of Actor
 */
public final class EconomicActor implements Actor, Serializable {
    public EconomicActor() {
        this.uniqueId = UUID.randomUUID();
        this.assets = new ArrayList<>();
    }

    public EconomicActor(UUID uniqueId, List<AssetStack> assets) {
        this.uniqueId = uniqueId;
        this.assets = assets;
    }

    public EconomicActor(EconomicActor other) {
        this.uniqueId = other.uniqueId;
        this.assets = other.assets;
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
                cleanAssets();
                return;
            }
        }
    }

    private void cleanAssets() {
        assets.removeIf(a -> a.getQuantity() == 0);
    }

    @Nonnull
    @Override
    public ActorType getType() {
        return ActorType.ACTOR;
    }
}
