package undercast.client.guiOptions;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import undercast.client.config.ConfigKey;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class SettingsGui extends GuiScreen {

    public ArrayList<ConfigKey> settings;
    public GuiScreen parent;
    public int pageIndex = 0;
    public int buttonPerPage = 14;
    public ArrayList<String> modules = new ArrayList<String>();

    public SettingsGui(GuiScreen parentScreen, ArrayList<ConfigKey> ar) {
        settings = ar;
        parent = parentScreen;
        for (ConfigKey c : settings) {
            if (!modules.contains(c.category)) {
                modules.add(c.category);
            }
        }
    }

    @Override
    public void initGui() {
        // Add buttons
        int x1 = width / 2 - 150;
        int x2 = width / 2 + 10;
        int y = height / 2 - 80;
        for (int i = 0; i < 7; i++) {
            int j = pageIndex * buttonPerPage + i;
            if (j < modules.size()) {
                this.buttonList.add(new GuiButton(i, x1, y + (i * 25), 150, 20, modules.get(j)));
            }
        }
        y = height / 2 - 80;
        for (int i = 7; i < 14; i++) {
            int j = pageIndex * buttonPerPage + i;
            if (j < modules.size()) {
                this.buttonList.add(new GuiButton(i, x2, y + ((i - this.buttonPerPage / 2) * 25), 150, 20, modules.get(j)));
            }
        }
        int x = width / 2 - 75;
        y = y + this.buttonPerPage / 2 * 25;
        this.buttonList.add(new GuiButton(1, x, y, 150, 20, "Back"));
        this.buttonList.add(new GuiButton(15, this.width - 40, y, 20, 20, ">"));
        this.buttonList.add(new GuiButton(16, 20, y, 20, 20, "<"));
        if (this.pageIndex == 0) {
            ((GuiButton) this.buttonList.get(this.buttonList.size() - 1)).enabled = false;
        }
        if (this.modules.size() < (pageIndex + 1) * buttonPerPage) {
            ((GuiButton) this.buttonList.get(this.buttonList.size() - 2)).enabled = false;
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        drawDefaultBackground();
        // Draw label at top of screen
        drawCenteredString(fontRenderer, "Undercast settings", width / 2, height / 2 - 80 - 20, 0x4444bb);
        // Draw buttons
        super.drawScreen(par1, par2, par3);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        if (guibutton.id == 15) {
            this.pageIndex++;
            this.buttonList.clear();
            this.initGui();
        } else if (guibutton.id == 16) {
            this.pageIndex--;
            this.buttonList.clear();
            this.initGui();
        } else if(guibutton.id == 1){
            Minecraft.getMinecraft().displayGuiScreen(parent);
        } else{
            String category = guibutton.displayString;
            Minecraft.getMinecraft().displayGuiScreen(new ModuleSettingsGui(this, category, settings));
        }
    }
}
