package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.common.block.BlockSpectralContainer;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectObsidian extends RitualEffect {

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        String owner = ritualStone.getOwner();
        World worldSave = MinecraftServer.getServer().worldServers[0];
        LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);
        int currentEssence = data.currentEssence;
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();

        if (data == null) {
            data = new LifeEssenceNetwork(owner);
            worldSave.setItemData(owner, data);
        }

        Block block = world.getBlock(x, y + 1, z);

        if (world.isAirBlock(x, y + 1, z) && !(block instanceof BlockSpectralContainer)) {
            if (currentEssence < this.getCostPerRefresh()) {
                EntityPlayer entityOwner = SpellHelper.getPlayerForUsername(owner);

                if (entityOwner == null) {
                    return;
                }

                entityOwner.addPotionEffect(new PotionEffect(Potion.confusion.id, 80));
            } else {
                for (int i = 0; i < 10; i++) {
                    SpellHelper.sendIndexedParticleToAllAround(world, x, y, z, 20, world.provider.dimensionId, 3, x, y, z);
                }
                if (world.isAirBlock(x + 1, y + 1, z)) {
                    world.setBlock(x + 1, y + 1, z, Blocks.obsidian, 0, 3);
                    data.currentEssence = currentEssence - this.getCostPerRefresh();
                }
                if (world.isAirBlock(x - 1, y + 1, z)) {
                    world.setBlock(x - 1, y + 1, z, Blocks.obsidian, 0, 3);
                    data.currentEssence = currentEssence - this.getCostPerRefresh();
                }
                if (world.isAirBlock(x, y + 1, z + 1)) {
                    world.setBlock(x, y + 1, z + 1, Blocks.obsidian, 0, 3);
                    data.currentEssence = currentEssence - this.getCostPerRefresh();
                }
                if (world.isAirBlock(x, y + 1, z - 1)) {
                    world.setBlock(x, y + 1, z - 1, Blocks.obsidian, 0, 3);
                    data.currentEssence = currentEssence - this.getCostPerRefresh();
                }
                data.markDirty();
            }
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 250;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> obsidianRitual = new ArrayList();
        obsidianRitual.add(new RitualComponent(1, 0, 0, RitualComponent.FIRE));
        obsidianRitual.add(new RitualComponent(-1, 0, 0, RitualComponent.FIRE));
        obsidianRitual.add(new RitualComponent(0, 0, 1, RitualComponent.FIRE));
        obsidianRitual.add(new RitualComponent(0, 0, -1, RitualComponent.FIRE));
        obsidianRitual.add(new RitualComponent(1, 1, 1, RitualComponent.WATER));
        obsidianRitual.add(new RitualComponent(-1, 1, 1, RitualComponent.WATER));
        obsidianRitual.add(new RitualComponent(-1, 1, -1, RitualComponent.WATER));
        obsidianRitual.add(new RitualComponent(1, 1, -1, RitualComponent.WATER));
        return obsidianRitual;
    }
}
