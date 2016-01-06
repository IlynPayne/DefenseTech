package defense.explosion.explosive;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import defense.api.IExplosive;
import defense.api.IExplosiveContainer;
import defense.core.CoreModule;
import defense.explosion.items.ItemRemoteDetonator;
import mekanism.api.Coord4D;
import mekanism.common.Mekanism;
import mekanism.common.base.ITileNetwork;
import mekanism.common.network.PacketDataRequest.DataRequestMessage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileExplosive extends TileEntity implements IExplosiveContainer, ITileNetwork
{
    public boolean exploding = false;
    public int haoMa = 0;
    public NBTTagCompound nbtData = new NBTTagCompound();

    @Override
    public boolean canUpdate()
    {
        return false;
    }

    /** Reads a tile entity from NBT. */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.haoMa = par1NBTTagCompound.getInteger("explosiveID");
        this.nbtData = par1NBTTagCompound.getCompoundTag("data");
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("explosiveID", this.haoMa);
        par1NBTTagCompound.setTag("data", this.nbtData);
    }

    @Override
    public void handlePacketData(ByteBuf dataStream)
    {
        haoMa = dataStream.readInt();
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
    
    @Override
    public ArrayList getNetworkedData(ArrayList data)
    {
    	data.add(haoMa);
    	
    	return data;
    }
    
    @Override
	public void validate()
	{
		super.validate();

		if(worldObj.isRemote)
		{
			Mekanism.packetHandler.sendToServer(new DataRequestMessage(Coord4D.get(this)));
		}
	}

    public ForgeDirection getDirection()
    {
        return ForgeDirection.getOrientation(this.getBlockMetadata());
    }

    public void setDirection(ForgeDirection facingDirection)
    {
        this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, facingDirection.ordinal(), 2);
    }

    @Override
    public IExplosive getExplosiveType()
    {
        return ExplosiveRegistry.get(this.haoMa);
    }

    @Override
    public NBTTagCompound getTagCompound()
    {
        return this.nbtData;
    }
}
