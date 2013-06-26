package undercast.client;

import undercast.client.modules.tipfiltering.TipFiltering;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_UndercastMLBase;
import net.minecraft.util.StringUtils;

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

    public UndercastClient(BaseMod mod) {
        instance = this;
        modLoaderBaseMod = mod;
        modules.add(new TipFiltering());
        this.registerKeyBindings();
        this.loadModules();
    }

    private void loadModules() {
        for (IModule i : modules) {
            i.load();
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
        return true;
    }

    public void keyboardEvent(KeyBinding key) {
    }
}
