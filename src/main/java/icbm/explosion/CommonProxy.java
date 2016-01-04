package icbm.explosion;

import icbm.explosion.container.ContainerCruiseLauncher;
import icbm.explosion.container.ContainerMissileCoordinator;
import icbm.explosion.entities.EntityMissile;
import icbm.explosion.explosive.TileExplosive;
import icbm.explosion.machines.TileCruiseLauncher;
import icbm.explosion.machines.TileEMPTower;
import icbm.explosion.machines.TileMissileCoordinator;
import icbm.explosion.machines.TileRadarStation;
import icbm.explosion.machines.launcher.TileLauncherBase;
import icbm.explosion.machines.launcher.TileLauncherFrame;
import icbm.explosion.machines.launcher.TileLauncherScreen;

import java.util.List;

import mekanism.api.Pos3D;
import mekanism.common.inventory.container.ContainerNull;
import mekanism.common.tile.TileEntityContainerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

/** ICBM Explosion Module Common Proxy
 * 
 * @author Calclavia */
public class CommonProxy implements IGuiHandler
{
    public void preInit()
    {
    }

    public void init()
    {
        GameRegistry.registerTileEntity(TileCruiseLauncher.class, "ICBMXiaoFaSheQi");
        GameRegistry.registerTileEntity(TileLauncherBase.class, "ICBMFaSheDi");
        GameRegistry.registerTileEntity(TileLauncherScreen.class, "ICBMFaSheShiMuo");
        GameRegistry.registerTileEntity(TileLauncherFrame.class, "ICBMFaSheJia");
        GameRegistry.registerTileEntity(TileRadarStation.class, "ICBMLeiDaTai");
        GameRegistry.registerTileEntity(TileEMPTower.class, "ICBMDianCiQi");
        GameRegistry.registerTileEntity(TileMissileCoordinator.class, "ICBMYinDaoQi");
        GameRegistry.registerTileEntity(TileExplosive.class, "ICBMZhaDan");
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TileCruiseLauncher)
        {
            return new ContainerCruiseLauncher(player.inventory, (TileCruiseLauncher) tileEntity);
        }
        else if (tileEntity instanceof TileMissileCoordinator)
        {
            return new ContainerMissileCoordinator(player.inventory, (TileMissileCoordinator) tileEntity);
        }
        else if (tileEntity instanceof TileLauncherScreen || tileEntity instanceof TileRadarStation || tileEntity instanceof TileEMPTower || tileEntity instanceof TileLauncherBase || tileEntity instanceof TileMissileCoordinator)
        {
            return new ContainerNull(player, (TileEntityContainerBlock)tileEntity);
        }

        return null;
    }

    public boolean isGaoQing()
    {
        return false;
    }

    public void spawnParticle(String name, World world, Pos3D position, float scale, double distance)
    {
        this.spawnParticle(name, world, position, 0, 0, 0, scale, distance);
    }

    public void spawnParticle(String name, World world, Pos3D position, double motionX, double motionY, double motionZ, float scale, double distance)
    {
        this.spawnParticle(name, world, position, motionX, motionY, motionZ, 1, 1, 1, scale, distance);
    }

    public void spawnParticle(String name, World world, Pos3D position, double motionX, double motionY, double motionZ, float red, float green, float blue, float scale, double distance)
    {

    }
    
    public void playSound(EntityMissile missile) {}

    public int getParticleSetting()
    {
        return -1;
    }

    public List<Entity> getEntityFXs()
    {
        return null;
    }

    public void spawnShock(World world, Pos3D position, Pos3D target)
    {

    }

    public void spawnShock(World world, Pos3D startVec, Pos3D targetVec, int duration)
    {
        // TODO Auto-generated method stub

    }

}
