package defense.common.explosion;

import mekanism.common.recipe.ShapedMekanismRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import defense.common.explosive.Explosive;
import defense.common.explosive.blast.BlastRepulsive;

public class ExRepulsive extends Explosion
{
    public ExRepulsive(String name, int tier)
    {
        super(name, tier);
        this.setFuseTime(120);
    }
    
    @Override
    public void init()
    {
        if (this.getID() == Explosive.attractive.getID())
        {
            GameRegistry.addRecipe(new ShapedMekanismRecipe(this.getItemStack(), new Object[] { "YY", 'Y', Explosive.condensed.getItemStack() }));
        }
        else
        {
            GameRegistry.addRecipe(new ShapedMekanismRecipe(this.getItemStack(), new Object[] { "Y", "Y", 'Y', Explosive.condensed.getItemStack() }));
        }
    }

    @Override
    public void doCreateExplosion(World world, double x, double y, double z, Entity entity)
    {
        if (this.getID() == Explosive.attractive.getID())
        {
            new BlastRepulsive(world, entity, x, y, z, 2f).setDestroyItems().setPushType(1).explode();
        }
        else
        {
            new BlastRepulsive(world, entity, x, y, z, 2f).setDestroyItems().setPushType(2).explode();
        }
    }
}
