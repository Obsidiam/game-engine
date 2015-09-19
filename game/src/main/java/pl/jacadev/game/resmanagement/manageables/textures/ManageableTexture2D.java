package pl.jacadev.game.resmanagement.manageables.textures;

import org.lwjgl.opengl.GL11;
import pl.jacadev.engine.graphics.contexts.Bindings;
import pl.jacadev.game.resmanagement.loading.DataContainer;
import pl.jacadev.game.resmanagement.manageables.containers.TextureDataContainer;
import pl.jacadev.game.resmanagement.manageables.loaders.texture.TextureDesc;
import pl.jacadev.game.resmanagement.Manageable;
import pl.jacadev.engine.graphics.textures.Texture2D;
import pl.jacadev.engine.graphics.textures.Texturing;

/**
* @author Jaca777
*         Created 26.01.15 at 18:41
*/
public class ManageableTexture2D extends Texture2D implements Manageable{
    private boolean loaded = false;
    private boolean blocked = false;
    private TextureDesc desc;


    public ManageableTexture2D(TextureDesc desc) {
        super(0,0,0,0,0,false);
        this.desc = desc;
    }

    @Override
    public void bind(Bindings bindings) {
        if (!loaded) GL11.glBindTexture(type, 0);
        else super.bind(bindings);
    }

    @Override
    public void unload() {
        if(loaded) {
            super.unload();
            this.loaded = false;
        } else blocked = true;
    }

    @Override
    public void load(DataContainer data) {
        if(!blocked) {
            TextureDataContainer textureContainer = (TextureDataContainer) data;
            this.format = textureContainer.getFormat();
            this.internalformat = textureContainer.getInternalformat();
            this.width = textureContainer.getW();
            this.height = textureContainer.getH();
            this.texture = Texturing.genTexture2DArray(this.internalformat, this.format, this.width, this.height, 1, textureContainer.getData());
            this.loaded = true;
        }else blocked = false;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public TextureDesc getDesc() {
        return desc;
    }
}
