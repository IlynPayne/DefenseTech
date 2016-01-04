package icbm.explosion.render.entity;

import icbm.ModelICBM;
import icbm.api.IExplosive;
import icbm.explosion.entities.EntityMissile;
import icbm.explosion.entities.EntityMissile.MissileType;
import icbm.explosion.ex.Explosion;

import java.util.HashMap;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
/** @author Calclavia */
public class RenderMissile extends Render
{
    public static final HashMap<Explosion, ModelICBM> cache = new HashMap<Explosion, ModelICBM>();

    public RenderMissile(float f)
    {
        this.shadowSize = f;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float f1)
    {
        EntityMissile entityMissile = (EntityMissile) entity;
        IExplosive e = entityMissile.getExplosiveType();
        
        if (e instanceof Explosion)
        {
            Explosion missile = (Explosion) e;

            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            GL11.glRotatef(entityMissile.prevRotationYaw + (entityMissile.rotationYaw - entityMissile.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(entityMissile.prevRotationPitch + (entityMissile.rotationPitch - entityMissile.prevRotationPitch) * f1 + 90.0F, 0.0F, 0.0F, 1.0F);

            if (entityMissile.missileType == MissileType.CruiseMissile)
            {
                GL11.glScalef(0.5f, 0.5f, 0.5f);
            }

            FMLClientHandler.instance().getClient().renderEngine.bindTexture(missile.getMissileResource());
            synchronized (cache)
            {
                if (!RenderMissile.cache.containsKey(missile))
                {

                    RenderMissile.cache.put(missile, missile.getMissileModel());

                }
                RenderMissile.cache.get(missile).render(0.0625F);
            }

            GL11.glPopMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }
}