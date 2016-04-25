package defense.client.gui;

import java.util.ArrayList;

import mekanism.api.Coord4D;
import mekanism.api.Pos3D;
import mekanism.common.Mekanism;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import defense.common.Reference;
import defense.common.tile.TileLauncherScreen;

public class GuiLauncherScreen extends GuiBase
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.DOMAIN, Reference.GUI_PATH + "gui_empty.png");

    private TileLauncherScreen tileEntity;
    private GuiTextField target_xCoord_field;
    private GuiTextField target_yCoord_field;
    private GuiTextField target_zCoord_field;
    private GuiTextField target_freq_field;
    private GuiTextField target_height_field;

    private int containerWidth;
    private int containerHeight;

    public GuiLauncherScreen(TileLauncherScreen tileEntity)
    {
        super(tileEntity);
        this.tileEntity = tileEntity;
        ySize = 166;
    }

    /** Adds the buttons (and other controls) to the screen in question. */
    @Override
    public void initGui()
    {
        super.initGui();
        this.target_xCoord_field = new GuiTextField(fontRendererObj, 110, 37, 45, 12);
        this.target_zCoord_field = new GuiTextField(fontRendererObj, 110, 52, 45, 12);
        this.target_yCoord_field = new GuiTextField(fontRendererObj, 110, 67, 45, 12);
        this.target_height_field = new GuiTextField(fontRendererObj, 110, 82, 45, 12);
        this.target_freq_field = new GuiTextField(fontRendererObj, 110, 97, 45, 12);

        this.target_freq_field.setMaxStringLength(4);
        this.target_xCoord_field.setMaxStringLength(6);
        this.target_zCoord_field.setMaxStringLength(6);
        this.target_yCoord_field.setMaxStringLength(3);
        this.target_height_field.setMaxStringLength(3);

        this.target_freq_field.setText(this.tileEntity.getFrequency() + "");
        this.target_height_field.setText(this.tileEntity.gaoDu + "");

        if (this.tileEntity.getTarget() == null)
        {
            this.target_xCoord_field.setText(Math.round(this.tileEntity.xCoord) + "");
            this.target_zCoord_field.setText(Math.round(this.tileEntity.zCoord) + "");
            this.target_yCoord_field.setText("0");
        }
        else
        {
            this.target_xCoord_field.setText(Math.round(this.tileEntity.getTarget().xPos) + "");
            this.target_zCoord_field.setText(Math.round(this.tileEntity.getTarget().zPos) + "");
            this.target_yCoord_field.setText(Math.round(this.tileEntity.getTarget().yPos) + "");
        }
    }

    /** Call this method from you GuiScreen to process the keys into textbox. */
    @Override
    public void keyTyped(char par1, int par2)
    {
        super.keyTyped(par1, par2);
        this.target_xCoord_field.textboxKeyTyped(par1, par2);
        this.target_zCoord_field.textboxKeyTyped(par1, par2);

        if (tileEntity.getTier() >= 1)
        {
            this.target_yCoord_field.textboxKeyTyped(par1, par2);
            this.target_height_field.textboxKeyTyped(par1, par2);

            if (tileEntity.getTier() > 1)
            {
                this.target_freq_field.textboxKeyTyped(par1, par2);
            }
        }

        try
        {
            Pos3D newTarget = new Pos3D(Integer.parseInt(this.target_xCoord_field.getText()), Math.max(Integer.parseInt(this.target_yCoord_field.getText()), 0), Integer.parseInt(this.target_zCoord_field.getText()));

            this.tileEntity.setTarget(newTarget);
            
            ArrayList data = new ArrayList();
            
            data.add(2);
            data.add((int)tileEntity.getTarget().xPos);
            data.add((int)tileEntity.getTarget().yPos);
            data.add((int)tileEntity.getTarget().zPos);
            
            Mekanism.packetHandler.sendToServer(new TileEntityMessage(Coord4D.get(tileEntity), data));
        }
        catch (NumberFormatException e)
        {

        }

        try
        {
            short newFrequency = (short) Math.max(Short.parseShort(this.target_freq_field.getText()), 0);

            this.tileEntity.setFrequency(newFrequency);
            
            ArrayList data = new ArrayList();
            
            data.add(1);
            data.add(tileEntity.getFrequency());
            
            Mekanism.packetHandler.sendToServer(new TileEntityMessage(Coord4D.get(tileEntity), data));
        }
        catch (NumberFormatException e)
        {

        }

        try
        {
            short newGaoDu = (short) Math.max(Math.min(Short.parseShort(this.target_height_field.getText()), Short.MAX_VALUE), 3);

            this.tileEntity.gaoDu = newGaoDu;
            
            ArrayList data = new ArrayList();
            
            data.add(3);
            data.add(tileEntity.gaoDu);
            
            Mekanism.packetHandler.sendToServer(new TileEntityMessage(Coord4D.get(tileEntity), data));
        }
        catch (NumberFormatException e)
        {

        }
    }

    /** Args: x, y, buttonClicked */
    @Override
    public void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.target_xCoord_field.mouseClicked(par1 - containerWidth, par2 - containerHeight, par3);
        this.target_zCoord_field.mouseClicked(par1 - containerWidth, par2 - containerHeight, par3);

        if (tileEntity.getTier() >= 1)
        {
            this.target_yCoord_field.mouseClicked(par1 - containerWidth, par2 - containerHeight, par3);
            this.target_height_field.mouseClicked(par1 - containerWidth, par2 - containerHeight, par3);

            if (tileEntity.getTier() > 1)
            {
                this.target_freq_field.mouseClicked(par1 - containerWidth, par2 - containerHeight, par3);
            }
        }

    }

    /** Draw the foreground layer for the GuiContainer (everything in front of the items) */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.target_xCoord_field.drawTextBox();
        this.target_zCoord_field.drawTextBox();

        // Draw the air detonation GUI
        if (tileEntity.getTier() >= 1)
        {
            this.target_yCoord_field.drawTextBox();
            this.fontRendererObj.drawString(LangUtils.localize("gui.launcherScreen.detHeight"), 12, 68, 4210752);

            this.target_height_field.drawTextBox();
            this.fontRendererObj.drawString(LangUtils.localize("gui.launcherScreen.lockHeight"), 12, 83, 4210752);

            if (tileEntity.getTier() > 1)
            {
                this.target_freq_field.drawTextBox();
                this.fontRendererObj.drawString(LangUtils.localize("gui.misc.freq"), 12, 98, 4210752);
            }
        }

        this.fontRendererObj.drawString("", 45, 6, 4210752);
        this.fontRendererObj.drawString("\u00a77" + LangUtils.localize("gui.launcherScreen.name"), 30, 6, 4210752);

        this.fontRendererObj.drawString(LangUtils.localize("gui.launcherScreen.target"), 12, 25, 4210752);
        this.fontRendererObj.drawString(LangUtils.localize("gui.misc.XCoord"), 25, 40, 4210752);
        this.fontRendererObj.drawString(LangUtils.localize("gui.misc.ZCoord"), 25, 55, 4210752);

        int inaccuracy = 30;

        if (this.tileEntity.laucherBase != null)
        {
            if (this.tileEntity.laucherBase.supportFrame != null)
            {
                inaccuracy = this.tileEntity.laucherBase.supportFrame.getInaccuracy();
            }
        }

        this.fontRendererObj.drawString(LangUtils.localize("gui.launcherScreen.inaccuracy").replaceAll("%p", "" + inaccuracy), 12, 113, 4210752);

        // Shows the status of the missile launcher
        this.fontRendererObj.drawString(LangUtils.localize("gui.misc.status") + " " + this.tileEntity.getStatus(), 12, 125, 4210752);
        //voltage
        this.fontRendererObj.drawString(MekanismUtils.getEnergyDisplay(this.tileEntity.getEnergy()) + "/" + MekanismUtils.getEnergyDisplay(this.tileEntity.getMaxEnergy()), 12, 150, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
    {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        containerWidth = (this.width - this.xSize) / 2;
        containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        if (!this.target_xCoord_field.isFocused())
            this.target_xCoord_field.setText(Math.round(this.tileEntity.getTarget().xPos) + "");
        if (!this.target_zCoord_field.isFocused())
            this.target_zCoord_field.setText(Math.round(this.tileEntity.getTarget().zPos) + "");
        if (!this.target_yCoord_field.isFocused())
            this.target_yCoord_field.setText(Math.round(this.tileEntity.getTarget().yPos) + "");

        if (!this.target_height_field.isFocused())
            this.target_height_field.setText(this.tileEntity.gaoDu + "");

        if (!this.target_freq_field.isFocused())
            this.target_freq_field.setText(this.tileEntity.getFrequency() + "");
    }
}
