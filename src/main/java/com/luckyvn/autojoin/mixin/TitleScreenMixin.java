package com.luckyvn.autojoin.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {

    private static boolean firstLoad = true;

    @Inject(method = "init()V", at = @At("HEAD"))
    private void onInit(CallbackInfo ci) {
        if (!firstLoad) return;
        firstLoad = false;

        MinecraftClient client = MinecraftClient.getInstance();

        // Skip if already connected to any server (e.g. integrated server)
        if (client.world != null && client.getServer() != null) return;

        String host = "luckyvn.com";
        ServerAddress address = ServerAddress.parse(host);

        ServerInfo serverInfo = new ServerInfo("LuckyVN", host, ServerInfo.ServerType.OTHER);

        ConnectScreen.connect(((TitleScreen) (Object) this), client, address, serverInfo, false, null);
    }
}
