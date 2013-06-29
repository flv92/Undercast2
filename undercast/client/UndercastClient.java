package undercast.client;

import undercast.client.guiOptions.UndercastGuiConfigButton;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_UndercastMLBase;
import net.minecraft.util.StringUtils;
import undercast.client.config.UndercastConfig;
import undercast.client.interfaces.IChatModifier;
import undercast.client.interfaces.IDisplay;
import undercast.client.interfaces.IKeyRegistry;
import undercast.client.interfaces.IModule;
import undercast.client.modules.fpsviewer.FPSViewer;
import undercast.client.modules.tipfiltering.TipFiltering;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UndercastClient {

    private static UndercastClient instance;
    private static BaseMod modLoaderBaseMod;
    private ArrayList<IModule> modules = new ArrayList();
    public HashMap<KeyBinding, Boolean> keyBindings = new HashMap<KeyBinding, Boolean>();
    public String forgeNewMessage = null;
    public boolean shouldUseForgeNewMessage = false;
    public UndercastConfig config;
    private Integer buttonListSizeOfGuiOptions = null;

    public UndercastClient(BaseMod mod) {
        instance = this;
        modLoaderBaseMod = mod;
        modules.add(new TipFiltering());
        modules.add(new FPSViewer());
        try {
            config = new UndercastConfig(new File(Minecraft.getMinecraftDir().getCanonicalPath() + File.separatorChar + "config" + File.separatorChar + "UndercastClient.cfg"));
            config.loadConfig();
        } catch (Exception ex) {
        }
        this.loadModules();
        this.registerKeyBindings();
    }

    private void loadModules() {
        for (IModule i : modules) {
            i.load(config);
        }
        try {
            config.loadConfig();
        } catch (Exception ex) {
            Logger.getLogger(UndercastClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void registerKeyBindings() {
        for (IModule i : modules) {
            if (i instanceof IKeyRegistry) {
                keyBindings.putAll(((IKeyRegistry) i).registerKeyBindings());
            }
        }
    }

    public static UndercastClient getInstance() {
        return instance;
    }

    /*
     * ML only
     */
    public void registerMLKeyBindings() {
        for (Map.Entry<KeyBinding, Boolean> i : keyBindings.entrySet()) {
            ModLoader.registerKey(modLoaderBaseMod, i.getKey(), i.getValue());
        }
    }

    /**
     * "chatMessageReceived" is called from Forge using an IChatListener while
     * ModLoader calls it from the clientChat(String) method.
     *
     * @param message The content of the message
     */
    public void chatMessageReceived(String message) {
        String newMessage = null;
        for (IModule i : modules) {
            if (i instanceof IChatModifier) {
                newMessage = ((IChatModifier) i).messageReceived(message, StringUtils.stripControlCodes(message));
            }
        }
        if (newMessage == null) {
            if (((mod_UndercastMLBase) modLoaderBaseMod).isUsingForge) {
                this.shouldUseForgeNewMessage = true;
                this.forgeNewMessage = null;
            } else {
                Minecraft.getMinecraft().ingameGUI.getChatGUI().deleteChatLine(0);
            }
        } else if (!message.equals(newMessage)) {
            if (((mod_UndercastMLBase) modLoaderBaseMod).isUsingForge) {
                this.shouldUseForgeNewMessage = true;
                this.forgeNewMessage = newMessage;
            } else {
                Minecraft.getMinecraft().ingameGUI.getChatGUI().deleteChatLine(0);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(newMessage);
            }
        }
    }

    /**
     * "clientConnect" is called from Forge using an IConnectionHandler while
     * ModLoader calls it from the clientConnect(NetClientHandler) method.
     *
     * @param server The server ip
     */
    public void clientConnect(String server) {
    }

    /**
     * "clientDisconnect" is called from Forge using an IConnectionHandler while
     * ModLoader calls it from the clientDisconnect(NetClientHandler) method.
     *
     * @param server The server ip
     */
    public void clientDisconnect(String server) {
    }

    /**
     * "onTickInGame" is called from Forge using an ITickHandler and simulating
     * ML way of calling it
     *
     * @param tick No idea, possibly an unique id
     * @param mc A Minecraft instance
     * @return
     */
    public boolean onTickInGame(float tick, Minecraft mc) {
        int x = 2;
        int y = 2;
        for (IModule i : modules) {
            if (i instanceof IDisplay) {
                for (DisplayElement s : ((IDisplay) i).getDisplayStrings()) {
                    mc.fontRenderer.drawStringWithShadow(s.message, x, y, s.color);
                }
            }
        }
        return true;
    }

    /**
     * "onTickInGui" is called from Forge using an ITickHandler and simulating
     * ML way of calling it
     *
     * @param tick No idea, possibly an unique id
     * @param mc A Minecraft instance
     * @param screen The current displayed screen
     * @return
     */
    public boolean onTickInGUI(float tick, Minecraft mc, GuiScreen screen) {
        if (screen instanceof GuiOptions) {
            List customButtonList;
            try {
                customButtonList = ObfuscationReflectionHelper.getPrivateValue(GuiScreen.class, (GuiOptions) screen, 4);
                if (this.buttonListSizeOfGuiOptions == null) {
                    this.buttonListSizeOfGuiOptions = customButtonList.size();
                }
                if (customButtonList.size() == this.buttonListSizeOfGuiOptions) {
                    customButtonList.add(new UndercastGuiConfigButton(301, screen.width / 2 + 5, screen.height / 6 + 60, 150, 20, "Undercast config", screen));
                }
                ObfuscationReflectionHelper.setPrivateValue(GuiScreen.class, (GuiOptions) screen, customButtonList, 4);
            } catch (ReflectionHelper.UnableToAccessFieldException e) {
            }
        }
        return true;
    }

    public void keyboardEvent(KeyBinding key) {
        for (IModule i : modules) {
            if (i instanceof IKeyRegistry) {
                ((IKeyRegistry) i).keyDown(key);
            }
        }
    }

    public IModule getModuleFromName(String name) {
        for (IModule i : this.modules) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }
}
