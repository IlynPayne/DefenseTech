package icbm.core.blocks;

import icbm.Reference;
import icbm.core.ICBMCore;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/** World generated block
 * 
 * @author Calcalvia */
public class BlockSulfurOre extends Block
{
    public BlockSulfurOre()
    {
        super(Material.rock);
        this.setBlockName(Reference.PREFIX + "oreSulfur");
        this.setBlockTextureName(Reference.PREFIX + "oreSulfur");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(3.0f);
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune)
    {
        return ICBMCore.itemSulfurDust;
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 3 + rand.nextInt(3);
    }

    @Override
    public int quantityDroppedWithBonus(int drop, Random rand)
    {
        return this.quantityDropped(rand) + rand.nextInt(drop + 1);
    }
}
