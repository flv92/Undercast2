package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.settings.KeyBinding;
import undercast.client.UndercastClient;
import undercast.client.controls.GuiUndercastControls;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class mod_UndercastMLBase extends BaseMod {

    public boolean isUsingForge = true;
    private UndercastClient client;

    public mod_UndercastMLBase() {
        client = new UndercastClient(this);
        try {
            Class modClass = Class.forName("cpw.mods.fml.common.Mod");
        } catch (ClassNotFoundException exception) {
            isUsingForge = false;
        }
    }

    @Override
    public void load() {
        if (!isUsingForge) {
            ModLoader.setInGUIHook(this, true, false);
            ModLoader.setInGameHook(this, true, false);
            UndercastClient.getInstance().registerMLKeyBindings();
        }
    }

    @Override
    public String getVersion() {
        return "2.0";
    }

    @Override
    public void clientChat(String var1) {
        if (!isUsingForge) {
            this.client.chatMessageReceived(var1);
        }
    }

    @Override
    public void clientConnect(NetClientHandler handler) {
        if (!isUsingForge) {
            this.client.clientConnect(handler.getNetManager().getSocketAddress().toString());
        }
    }

    @Override
    public void clientDisconnect(NetClientHandler handler) {
        if (!isUsingForge) {
            this.client.clientDisconnect(handler.getNetManager().getSocketAddress().toString());
        }
    }

    @Override
    public boolean onTickInGame(float tick, Minecraft mc) {
        if (!isUsingForge) {
            return this.client.onTickInGame(tick, mc);
        }
        return true;
    }

    @Override
    public boolean onTickInGUI(float tick, Minecraft mc, GuiScreen screen) {
        mc.fontRenderer.drawStringWithShadow(mc.currentScreen.getClass().getCanonicalName(), 2, 2, 16777216);
        if (!isUsingForge) {
            if (mc.currentScreen.getClass().equals(GuiControls.class)) {
                mc.displayGuiScreen(new GuiUndercastControls(new GuiOptions((GuiScreen) null, mc.gameSettings), mc.gameSettings));
            }
            return this.client.onTickInGUI(tick, mc, screen);
        }
        return true;
    }

    @Override
    public void keyboardEvent(KeyBinding key) {
        if (!isUsingForge) {
            this.client.keyboardEvent(key);
        }
    }
}
