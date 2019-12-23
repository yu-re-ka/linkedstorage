package net.kyrptonaught.linkedstorage;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.kyrptonaught.linkedstorage.block.StorageBlock;
import net.kyrptonaught.linkedstorage.block.StorageBlockRenderer;
import net.kyrptonaught.linkedstorage.client.StorageContainerScreen;
import net.kyrptonaught.linkedstorage.inventory.LinkedInventoryHelper;
import net.kyrptonaught.linkedstorage.register.ModBlocks;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class LinkedStorageModClient implements ClientModInitializer {
    public static final ModelIdentifier LINKED_CHEST_LID = new ModelIdentifier(new Identifier(LinkedStorageMod.MOD_ID, "storageblock_lid"), "");

    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerAppender(((manager, out) -> out.accept(LINKED_CHEST_LID)));
        BlockEntityRendererRegistry.INSTANCE.register(StorageBlock.blockEntity, StorageBlockRenderer::new);
        ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier(LinkedStorageMod.MOD_ID, "linkedstorage"), (syncId, identifier, player, buf) ->
        {
            int[] channel = buf.readIntArray();
            return new StorageContainerScreen(LinkedStorageMod.getContainer(syncId, player, channel), player.inventory, LinkedInventoryHelper.getChannelName(channel));
        });
    }
}
