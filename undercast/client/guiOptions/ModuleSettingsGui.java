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
public class ModuleSettingsGui extends GuiScreen {

    public GuiScreen parentGui;
    public String category;
    public ArrayList<ConfigKey> values = new ArrayList();
    public int pageIndex = 0;
    public int buttonPerPage = 14;

    public ModuleSettingsGui(SettingsGui parent, String c, ArrayList<ConfigKey> allSettings) {
        parentGui = parent;
        category = c;
        for (ConfigKey config : allSettings) {
            if (config.category.equals(category)) {
                values.add(config);
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
            if (j < values.size()) {
                this.buttonList.add(new SettingsToggleButton(i, x1, y + (i * 25), 150, 20, values.get(j)));
            }
        }
        y = height / 2 - 80;
        for (int i = 7; i < 14; i++) {
            int j = pageIndex * buttonPerPage + i;
            if (j < values.size()) {
                this.buttonList.add(new SettingsToggleButton(i, x2, y + ((i - this.buttonPerPage / 2) * 25), 150, 20, values.get(j)));
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
        if (this.values.size() < (pageIndex + 1) * buttonPerPage) {
            ((GuiButton) this.buttonList.get(this.buttonList.size() - 2)).enabled = false;
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        drawDefaultBackground();
        // Draw label at top of screen
        drawCenteredString(fontRenderer, category + " settings", width / 2, height / 2 - 80 - 20, 0x4444bb);
        // Draw buttons
        super.drawScreen(par1, par2, par3);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        if (guibutton instanceof SettingsToggleButton) {
            ((SettingsToggleButton) guibutton).buttonPressed();
        } else if (guibutton.id == 15) {
            this.pageIndex++;
            this.buttonList.clear();
            this.initGui();
        } else if (guibutton.id == 16) {
            this.pageIndex--;
            this.buttonList.clear();
            this.initGui();
        } else if (guibutton.id == 1) {
            Minecraft.getMinecraft().displayGuiScreen(parentGui);
        }
    }
}
