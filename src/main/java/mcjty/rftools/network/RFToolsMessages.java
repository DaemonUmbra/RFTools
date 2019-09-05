package mcjty.rftools.network;

import mcjty.lib.network.PacketHandler;
import mcjty.lib.network.PacketSendClientCommand;
import mcjty.lib.network.PacketSendServerCommand;
import mcjty.lib.typed.TypedMap;
import mcjty.rftools.RFTools;
import mcjty.rftools.blocks.builder.PacketChamberInfoReady;
import mcjty.rftools.blocks.monitor.*;
import mcjty.rftools.blocks.screens.network.PacketGetScreenData;
import mcjty.rftools.blocks.screens.network.PacketModuleUpdate;
import mcjty.rftools.blocks.screens.network.PacketReturnScreenData;
import mcjty.rftools.blocks.security.PacketSecurityInfoReady;
import mcjty.rftools.blocks.security.SecurityConfiguration;
import mcjty.rftools.blocks.shaper.PacketProjectorClientNotification;
import mcjty.rftools.blocks.shield.PacketFiltersReady;
import mcjty.rftools.blocks.shield.PacketGetFilters;
import mcjty.rftools.blocks.teleporter.PacketGetReceivers;
import mcjty.rftools.blocks.teleporter.PacketGetTransmitters;
import mcjty.rftools.blocks.teleporter.PacketReceiversReady;
import mcjty.rftools.blocks.teleporter.PacketTransmittersReady;
import mcjty.rftools.items.builder.PacketUpdateNBTItemInventoryShape;
import mcjty.rftools.items.creativeonly.PacketDelightingInfoReady;
import mcjty.rftools.items.creativeonly.PacketGetDelightingInfo;
import mcjty.rftools.items.modifier.PacketUpdateModifier;
import mcjty.rftools.items.netmonitor.NetworkMonitorConfiguration;
import mcjty.rftools.items.netmonitor.PacketConnectedBlocksReady;
import mcjty.rftools.items.netmonitor.PacketGetConnectedBlocks;
import mcjty.rftools.items.teleportprobe.PacketAllReceiversReady;
import mcjty.rftools.items.teleportprobe.PacketGetAllReceivers;
import mcjty.rftools.items.teleportprobe.PacketTargetsReady;
import mcjty.rftools.playerprops.PacketSendBuffsToClient;
import mcjty.rftools.shapes.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nonnull;

public class RFToolsMessages {
    public static SimpleChannel INSTANCE;

    private static int id() {
        return PacketHandler.nextPacketID();
    }

    public static void registerMessages(String name) {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RFTools.MODID, name))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // Server side
        net.registerMessage(id(), PacketGetPlayers.class, PacketGetPlayers::toBytes, PacketGetPlayers::new, PacketGetPlayers::handle);
        net.registerMessage(id(), PacketGetReceivers.class, PacketGetReceivers::toBytes, PacketGetReceivers::new, PacketGetReceivers::handle);
        net.registerMessage(id(), PacketGetAllReceivers.class, PacketGetAllReceivers::toBytes, PacketGetAllReceivers::new, PacketGetAllReceivers::handle);
        net.registerMessage(id(), PacketGetTransmitters.class, PacketGetTransmitters::toBytes, PacketGetTransmitters::new, PacketGetTransmitters::handle);
        net.registerMessage(id(), PacketModuleUpdate.class, PacketModuleUpdate::toBytes, PacketModuleUpdate::new, PacketModuleUpdate::handle);
        net.registerMessage(id(), PacketGetScreenData.class, PacketGetScreenData::toBytes, PacketGetScreenData::new, PacketGetScreenData::handle);
        net.registerMessage(id(), PacketGetAdjacentBlocks.class, PacketGetAdjacentBlocks::toBytes, PacketGetAdjacentBlocks::new, PacketGetAdjacentBlocks::handle);
        net.registerMessage(id(), PacketGetAdjacentTankBlocks.class, PacketGetAdjacentTankBlocks::toBytes, PacketGetAdjacentTankBlocks::new, PacketGetAdjacentTankBlocks::handle);
        net.registerMessage(id(), PacketContentsMonitor.class, PacketContentsMonitor::toBytes, PacketContentsMonitor::new, PacketContentsMonitor::handle);
        net.registerMessage(id(), PacketGetFilters.class, PacketGetFilters::toBytes, PacketGetFilters::new, PacketGetFilters::handle);
        net.registerMessage(id(), PacketGetDelightingInfo.class, PacketGetDelightingInfo::toBytes, PacketGetDelightingInfo::new, PacketGetDelightingInfo::handle);
        if (NetworkMonitorConfiguration.enabled.get()) {
            net.registerMessage(id(), PacketGetConnectedBlocks.class, PacketGetConnectedBlocks::toBytes, PacketGetConnectedBlocks::new, PacketGetConnectedBlocks::handle);
        }
//        net.registerMessage(id(), PacketGridToServer.class, PacketGridToServer::toBytes, PacketGridToServer::new, PacketGridToServer::handle);
//        net.registerMessage(id(), PacketRequestItem.class, PacketRequestItem::toBytes, PacketRequestItem::new, PacketRequestItem::handle);
        net.registerMessage(id(), PacketGetHudLog.class, PacketGetHudLog::toBytes, PacketGetHudLog::new, PacketGetHudLog::handle);
        net.registerMessage(id(), PacketSendComposerData.class, PacketSendComposerData::toBytes, PacketSendComposerData::new, PacketSendComposerData::handle);
        net.registerMessage(id(), PacketUpdateModifier.class, PacketUpdateModifier::toBytes, PacketUpdateModifier::new, PacketUpdateModifier::handle);
        net.registerMessage(id(), PacketRequestShapeData.class, PacketRequestShapeData::toBytes, PacketRequestShapeData::new, PacketRequestShapeData::handle);
        net.registerMessage(id(), PacketUpdateNBTShapeCard.class, PacketUpdateNBTShapeCard::toBytes, PacketUpdateNBTShapeCard::new, PacketUpdateNBTShapeCard::handle);
        net.registerMessage(id(), PacketUpdateNBTItemInventoryShape.class, PacketUpdateNBTItemInventoryShape::toBytes, PacketUpdateNBTItemInventoryShape::new, PacketUpdateNBTItemInventoryShape::handle);
//        net.registerMessage(id(), PacketUpdateNBTItemFilter.class, PacketUpdateNBTItemFilter::toBytes, PacketUpdateNBTItemFilter::new, PacketUpdateNBTItemFilter::handle);
//        net.registerMessage(id(), PacketUpdateNBTItemStorage.class, PacketUpdateNBTItemStorage::toBytes, PacketUpdateNBTItemStorage::new, PacketUpdateNBTItemStorage::handle);
//        net.registerMessage(id(), PacketGetInventoryInfo.class, PacketGetInventoryInfo::toBytes, PacketGetInventoryInfo::new, PacketGetInventoryInfo::handle);

        // Client side
        net.registerMessage(id(), PacketPlayersReady.class, PacketPlayersReady::toBytes, PacketPlayersReady::new, PacketPlayersReady::handle);
        net.registerMessage(id(), PacketTransmittersReady.class, PacketTransmittersReady::toBytes, PacketTransmittersReady::new, PacketTransmittersReady::handle);
        net.registerMessage(id(), PacketReceiversReady.class, PacketReceiversReady::toBytes, PacketReceiversReady::new, PacketReceiversReady::handle);
        net.registerMessage(id(), PacketAllReceiversReady.class, PacketAllReceiversReady::toBytes, PacketAllReceiversReady::new, PacketAllReceiversReady::handle);
        net.registerMessage(id(), PacketTargetsReady.class, PacketTargetsReady::toBytes, PacketTargetsReady::new, PacketTargetsReady::handle);
        net.registerMessage(id(), PacketReturnScreenData.class, PacketReturnScreenData::toBytes, PacketReturnScreenData::new, PacketReturnScreenData::handle);
        net.registerMessage(id(), PacketChamberInfoReady.class, PacketChamberInfoReady::toBytes, PacketChamberInfoReady::new, PacketChamberInfoReady::handle);
        net.registerMessage(id(), PacketAdjacentBlocksReady.class, PacketAdjacentBlocksReady::toBytes, PacketAdjacentBlocksReady::new, PacketAdjacentBlocksReady::handle);
        net.registerMessage(id(), PacketAdjacentTankBlocksReady.class, PacketAdjacentTankBlocksReady::toBytes, PacketAdjacentTankBlocksReady::new, PacketAdjacentTankBlocksReady::handle);
        net.registerMessage(id(), PacketFiltersReady.class, PacketFiltersReady::toBytes, PacketFiltersReady::new, PacketFiltersReady::handle);
        net.registerMessage(id(), PacketSendBuffsToClient.class, PacketSendBuffsToClient::toBytes, PacketSendBuffsToClient::new, PacketSendBuffsToClient::handle);
        if (SecurityConfiguration.enabled.get()) {
            net.registerMessage(id(), PacketSecurityInfoReady.class, PacketSecurityInfoReady::toBytes, PacketSecurityInfoReady::new, PacketSecurityInfoReady::handle);
        }
        net.registerMessage(id(), PacketDelightingInfoReady.class, PacketDelightingInfoReady::toBytes, PacketDelightingInfoReady::new, PacketDelightingInfoReady::handle);
        if (NetworkMonitorConfiguration.enabled.get()) {
            net.registerMessage(id(), PacketConnectedBlocksReady.class, PacketConnectedBlocksReady::toBytes, PacketConnectedBlocksReady::new, PacketConnectedBlocksReady::handle);
        }
//        net.registerMessage(id(), PacketSyncSlotsToClient.class, PacketSyncSlotsToClient::toBytes, PacketSyncSlotsToClient::new, PacketSyncSlotsToClient::handle);
//        net.registerMessage(id(), PacketGridToClient.class, PacketGridToClient::toBytes, PacketGridToClient::new, PacketGridToClient::handle);
//        net.registerMessage(id(), PacketCraftTestResultToClient.class, PacketCraftTestResultToClient::toBytes, PacketCraftTestResultToClient::new, PacketCraftTestResultToClient::handle);
        net.registerMessage(id(), PacketHudLogReady.class, PacketHudLogReady::toBytes, PacketHudLogReady::new, PacketHudLogReady::handle);
        net.registerMessage(id(), PacketReturnRfInRange.class, PacketReturnRfInRange::toBytes, PacketReturnRfInRange::new, PacketReturnRfInRange::handle);
        net.registerMessage(id(), PacketReturnShapeData.class, PacketReturnShapeData::toBytes, PacketReturnShapeData::new, PacketReturnShapeData::handle);
        net.registerMessage(id(), PacketProjectorClientNotification.class, PacketProjectorClientNotification::toBytes, PacketProjectorClientNotification::new, PacketProjectorClientNotification::handle);
        net.registerMessage(id(), PacketReturnExtraData.class, PacketReturnExtraData::toBytes, PacketReturnExtraData::new, PacketReturnExtraData::handle);
//        net.registerMessage(id(), PacketReturnInventoryInfo.class, PacketReturnInventoryInfo::toBytes, PacketReturnInventoryInfo::new, PacketReturnInventoryInfo::handle);
    }

    public static void sendToServer(String command, @Nonnull TypedMap.Builder argumentBuilder) {
        INSTANCE.sendToServer(new PacketSendServerCommand(RFTools.MODID, command, argumentBuilder.build()));
    }

    public static void sendToServer(String command) {
        INSTANCE.sendToServer(new PacketSendServerCommand(RFTools.MODID, command, TypedMap.EMPTY));
    }

    public static void sendToClient(PlayerEntity player, String command, @Nonnull TypedMap.Builder argumentBuilder) {
        INSTANCE.sendTo(new PacketSendClientCommand(RFTools.MODID, command, argumentBuilder.build()), ((ServerPlayerEntity) player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToClient(PlayerEntity player, String command) {
        INSTANCE.sendTo(new PacketSendClientCommand(RFTools.MODID, command, TypedMap.EMPTY), ((ServerPlayerEntity) player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }
}
