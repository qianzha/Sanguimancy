package tombenpotter.sanguimancy.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import tombenpotter.sanguimancy.client.render.RenderAltarDiviner;
import tombenpotter.sanguimancy.client.render.RenderItemAltarDiviner;
import tombenpotter.sanguimancy.registry.BlocksRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(tombenpotter.sanguimancy.tile.TileAltarDiviner.class, new RenderAltarDiviner());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlocksRegistry.altarDiviner), new RenderItemAltarDiviner());
    }
}
