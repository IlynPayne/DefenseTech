package defense.core.prefab.item;

import defense.Reference;
import defense.CreativeTabHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

/** Prefab that sets the creative tab, texture name, and translation name
 * 
 * @author DarkGuardsman */
public class ItemBase extends Item
{
    public ItemBase(String name)
    {
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabHandler.INSTANCE);
        this.setTextureName(Reference.PREFIX + name);
    }
}
