package gg.umbra.wrapper.impl;

import gg.umbra.mapping.mappings.MPlayerInfo;
import gg.umbra.wrapper.Wrapper;

public class PlayerInfo
extends Wrapper {

    public ITextComponent R() {
        return new ITextComponent(MPlayerInfo.l(PlayerInfo.umbraInstance.getMappingsMapperCompat().CG, this.getObject()));
    }

    public ScorePlayerTeam X() {
        return new ScorePlayerTeam(MPlayerInfo.Z(PlayerInfo.umbraInstance.getMappingsMapperCompat().CG, this.getObject()));
    }

    public ResourceLocation i() {
        Object object = MPlayerInfo.J(PlayerInfo.umbraInstance.getMappingsMapperCompat().CG, this.I);
        if (object == null) {
            return null;
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            return new PlayerSkin(object).getTexture();
        }
        return new ResourceLocation(object);
    }

    public GameProfile v() {
        return new GameProfile(MPlayerInfo.W(PlayerInfo.umbraInstance.getMappingsMapperCompat().CG, this.I));
    }

    public PlayerInfo(Object object) {
        super(object);
    }

    public int z() {
        return MPlayerInfo.H(PlayerInfo.umbraInstance.getMappingsMapperCompat().CG, this.I);
    }
}

