package defense.client.render.tile;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defense.client.model.tile.ModelSMine;
import defense.client.render.RenderUtils;
import defense.common.DefenseTech;
import defense.common.Reference;
import defense.common.explosive.Explosive;
import defense.common.tile.TileExplosive;

@SideOnly(Side.CLIENT)
public class RenderBombBlock extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
    public static final ResourceLocation TEXTURE_FILE = new ResourceLocation(Reference.DOMAIN, Reference.MODEL_TEXTURE_PATH + "s-mine.png");
    public static final int ID = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if(modelID == ID)
        {
            if(metadata == Explosive.sMine.getID())
            {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0F, 1.5F, 0.0F);
                GL11.glRotatef(180f, 0f, 0f, 1f);
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_FILE);
                ModelSMine.INSTANCE.render(0.0625F);
                GL11.glPopMatrix();
            }
            else {
                try {
                    RenderUtils.renderNormalBlockAsItem(block, metadata, renderer);
                } catch(Exception e) {
                    DefenseTech.LOGGER.severe("Explosive Rendering Crash with: " + block + " and metadata: " + metadata);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
    {
        if(modelID == ID)
        {
            TileEntity tileEntity = iBlockAccess.getTileEntity(x, y, z);

            if(tileEntity instanceof TileExplosive)
            {
                renderer.renderStandardBlock(block, x, y, z);
                return true;
            }
        }

        return false;
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {}

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return ID;
    }
}
