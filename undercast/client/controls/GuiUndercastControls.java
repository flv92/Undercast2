package undercast.client.controls;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.StringTranslate;

/**
 *
 * @author UndercastTeam <http://undercastteam.github.io>
 */
public class GuiUndercastControls extends GuiScreen {

    /**
     * A reference to the screen object that created this. Used for navigating
     * between screens.
     */
    private GuiScreen parentScreen;
    /**
     * The title string that is displayed in the top-center of the screen.
     */
    protected String screenTitle = "Controls";
    /**
     * Reference to the GameSettings object.
     */
    private GameSettings options;
    private GuiControlsScrollPanel scrollPane;

    public GuiUndercastControls(GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
        this.parentScreen = par1GuiScreen;
        this.options = par2GameSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() {
        this.scrollPane = new GuiControlsScrollPanel(this, this.options, this.mc);
        StringTranslate stringtranslate = StringTranslate.getInstance();
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 24, stringtranslate.translateKey("gui.done")));
        this.scrollPane.registerScrollButtons(this.buttonList, 7, 8);
        this.screenTitle = stringtranslate.translateKey("controls.title");
    }

    /**
     * Fired when a control is clicked. This is the equivalent of
     * ActionListener.actionPerformed(ActionEvent e).
     */
    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == 200) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Fired when a key is typed. This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    protected void keyTyped(char par1, int par2) {
        if (this.scrollPane.keyTyped(par1, par2)) {
            super.keyTyped(par1, par2);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.scrollPane.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 4, 0xffffff);
        super.drawScreen(par1, par2, par3);
    }
}
