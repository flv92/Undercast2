package undercast.client.forge;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import java.util.Map;
import net.minecraft.client.settings.KeyBinding;
import undercast.client.UndercastClient;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UndercastKeyBinding extends KeyBindingRegistry.KeyHandler {

    public UndercastKeyBinding(KeyBinding[] keyBindings, boolean[] isRepeat) {
        super(keyBindings, isRepeat);
    }

    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
        if(!tickEnd){
            UndercastClient.getInstance().keyboardEvent(kb);
        }
    }

    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    @Override
    public String getLabel() {
        return null;
    }
}
