package gg.umbra.wrapper.impl;

public class RenderPlayer
extends RenderLivingBase<EntityPlayer> {
    public RenderPlayer(Object renderPlayerHandle) {
        super(renderPlayerHandle);
    }

    public ModelBiped getModelBipedMain() {
        return new ModelBiped(RenderPlayer.umbraInstance.getMappingsMapperCompat().renderPlayer.getMainModel(this.I));
    }

    @Override
    public PlayerModel getMainModel() {
        return new PlayerModel(RenderPlayer.umbraInstance.getMappingsMapperCompat().renderPlayer.getMainModel(this.I));
    }
}
