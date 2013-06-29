package undercast.client.guiOptions;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import undercast.client.UndercastClient;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class UndercastGuiConfigButton extends GuiButton {

    public GuiScreen parentScreen;

    public UndercastGuiConfigButton(int i, int i0, int i1, int i2, int i3, String undercast_config, GuiScreen screen) {
        super(i, i0, i1, i2, i3, undercast_config);
        parentScreen = screen;
    }

    @Override
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
        if (this.enabled && this.drawButton && par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height) {
            try {
                UndercastClient.getInstance().config.loadConfig();
            } catch (Exception ex) {
                Logger.getLogger(UndercastGuiConfigButton.class.getName()).log(Level.SEVERE, null, ex);
            }
            Minecraft.getMinecraft().displayGuiScreen(new SettingsGui(parentScreen, UndercastClient.getInstance().config.entryes));
        }
        return super.mousePressed(par1Minecraft, par2, par3);
    }
}
