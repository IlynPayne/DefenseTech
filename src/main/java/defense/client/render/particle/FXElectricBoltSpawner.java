package defense.client.render.particle;

import java.util.Random;

import mekanism.api.Pos3D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

/** A spawner used to spawn in multiple electrical bolts for a specific duration. */
public class FXElectricBoltSpawner extends EntityFX
{
    private Pos3D start;
    private Pos3D end;

    public FXElectricBoltSpawner(World world, Pos3D startVec, Pos3D targetVec, long seed, int duration)
    {
        super(world, startVec.xPos, startVec.yPos, startVec.zPos, 0.0D, 0.0D, 0.0D);

        if (seed == 0)
        {
            this.rand = new Random();
        }
        else
        {
            this.rand = new Random(seed);
        }

        this.start = startVec;
        this.end = targetVec;
        this.particleMaxAge = duration;
    }

    @Override
    public void onUpdate()
    {
        Minecraft.getMinecraft().effectRenderer.addEffect(new FXElectricBolt(this.worldObj, this.start, this.end, 0));
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }
    }

}
