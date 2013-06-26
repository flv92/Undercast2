package undercast.client.forge;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.Map;
import net.minecraft.client.settings.KeyBinding;
import undercast.client.UndercastClient;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
@Mod(modid = "UndercastMod", name = "UndercastMod", version = "2.0")
public class UndercastForgeModClass {

    @Init
    public void init(FMLInitializationEvent event) {
        //Register a ChatListener to handle chat in game.
        NetworkRegistry.instance().registerChatListener(new UndercastChatListener());
        //Register a connection handler to know handle server connections
        NetworkRegistry.instance().registerConnectionHandler(new UndercastConnectionHandler());
        //Register a tick handler
        TickRegistry.registerTickHandler(new UndercastTickHandler(), Side.CLIENT);
        
        //Register KeyBindings
        KeyBinding[] allKeyBindings = new KeyBinding[UndercastClient.getInstance().keyBindings.size()];
        boolean[] isRepeat = new boolean[UndercastClient.getInstance().keyBindings.size()];
        int index = 0;
        for (Map.Entry<KeyBinding, Boolean> mapEntry : UndercastClient.getInstance().keyBindings.entrySet()) {
            allKeyBindings[index] = mapEntry.getKey();
            isRepeat[index] = mapEntry.getValue();
            index++;
        }
        KeyBindingRegistry.registerKeyBinding(new UndercastKeyBinding(allKeyBindings, isRepeat));
    }
}
