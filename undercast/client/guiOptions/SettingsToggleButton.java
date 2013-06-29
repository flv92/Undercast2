package undercast.client.guiOptions;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import undercast.client.UndercastClient;
import undercast.client.config.ConfigKey;
import undercast.client.interfaces.IModule;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class SettingsToggleButton extends GuiButton {

    private String enabledString;
    private String disabledString;
    private ConfigKey setting;
    private IModule module;

    public SettingsToggleButton(int par1, int par2, int par3, int par4, int par5,
            ConfigKey config) {
        super(par1, par2, par3, par4, par5, "");
        this.setting = config;
        try {
            module = UndercastClient.getInstance().getModuleFromName(setting.category);
            this.enabledString = module.getConfigStringForKeyAndState(config, true);
            this.disabledString = module.getConfigStringForKeyAndState(config, false);
        } catch (NullPointerException e) {
        }
        this.updateString();
    }

    private void updateString() {
        if (UndercastClient.getInstance().config.getValue(module, setting.name, false)) {
            this.displayString = EnumChatFormatting.GREEN + enabledString;
        } else {
            this.displayString = EnumChatFormatting.RED + disabledString;
        }
    }

    public void buttonPressed() {
        boolean current = !UndercastClient.getInstance().config.getValue(module, setting.name, false);
        try {
            UndercastClient.getInstance().config.setValue(module, setting.name, current ? true : false);
            UndercastClient.getInstance().config.loadConfig();
        } catch (Exception ex) {
            Logger.getLogger(SettingsToggleButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.updateString();
    }
}
