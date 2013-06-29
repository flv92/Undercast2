package undercast.client.modules.fpsviewer;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import undercast.client.DisplayElement;
import undercast.client.config.ConfigKey;
import undercast.client.config.UndercastConfig;
import undercast.client.interfaces.IDisplay;
import undercast.client.interfaces.IModule;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class FPSViewer implements IModule, IDisplay {

    public boolean state = true;
    public boolean showFPS = true;

    @Override
    public void load(UndercastConfig config) {
        showFPS = config.getValue(this, "showFPS", true);
    }

    @Override
    public void setState(boolean newState) {
        state = newState;
    }

    @Override
    public String getConfigStringForKeyAndState(ConfigKey config, boolean enabled) {
        if (enabled && config.name.equals("showFPS")) {
            return "FPS shown";
        }
        if (!enabled && config.name.equals("showFPS")) {
            return "FPS hidden";
        }
        return null;
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public String getName() {
        return "FPS Viewer";
    }

    @Override
    public ArrayList<DisplayElement> getDisplayStrings() {
        ArrayList<DisplayElement> array = new ArrayList<DisplayElement>();
        array.add(new DisplayElement(Minecraft.getMinecraft().debug.split(",")[0], 0xffff));
        return array;
    }
}
