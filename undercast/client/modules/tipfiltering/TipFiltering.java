package undercast.client.modules.tipfiltering;

import net.minecraft.client.settings.KeyBinding;
import undercast.client.interfaces.IChatModifier;
import undercast.client.interfaces.IModule;
import undercast.client.annotations.UndercastModule;
import undercast.client.config.ConfigKey;
import undercast.client.config.UndercastConfig;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
@UndercastModule(name = "TipFiltering", version = "1.0")
public class TipFiltering implements IModule, IChatModifier {

    private boolean state = true;
    private boolean hideTips = true;

    @Override
    public void load(UndercastConfig config) {
        hideTips = config.getValue(this, "hideTips", true);
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean newState) {
        this.state = newState;
    }

    @Override
    public String messageReceived(String message, String strippedMessage) {
        if (hideTips) {
            if (message.contains("§7§o[§b§l§oTip§r§7§o]§7")) {
                message = null;
            }
        }
        return message;
    }

    @Override
    public String getName() {
        return "TipFiltering";
    }

    @Override
    public String getConfigStringForKeyAndState(ConfigKey config, boolean enabled) {
        if (enabled) {
            String name = config.name;
            if (name.equals("hideTips")) {
                return "Tips hidden";
            }
        } else {
            String name = config.name;
            if (name.equals("hideTips")) {
                return "Tips shown";
            }
        }
        return null;
    }
}
