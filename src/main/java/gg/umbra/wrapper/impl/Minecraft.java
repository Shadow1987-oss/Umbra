package gg.umbra.wrapper.impl;

import gg.umbra.Umbra;
import gg.umbra.mapping.mappings.MMinecraft;
import gg.umbra.wrapper.Wrapper;

import java.util.Map;

public class Minecraft
extends Wrapper {
    private static Object L;
    private static boolean s;

    public static void J(boolean bl) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().D(bl);
            return;
        }
        MMinecraft.t(Minecraft.umbraInstance.getMappings().U, Minecraft.i(), bl);
    }

    public static NetworkManager x$src$Lgg_umbra_wrapper_impl_NetworkManager_$1sglv7v() {
        return new NetworkManager(MMinecraft.w(Umbra.INSTANCE.getMappings().U, Minecraft.i()));
    }

    public static boolean V() {
        return MMinecraft.I(Minecraft.umbraInstance.getMappings().U, Minecraft.i());
    }

    public static int w() {
        return MMinecraft.y(Minecraft.umbraInstance.getMappings().U, Minecraft.i());
    }

    public static void b() {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().Q();
            return;
        }
        MMinecraft.R(Minecraft.umbraInstance.getMappings().U, Minecraft.i());
    }

    static {
        if (!Minecraft.g()) {
            Minecraft.e(true);
        }
    }

    public static void r(int n) {
        MMinecraft.x(Minecraft.umbraInstance.getMappings().U, Minecraft.i(), n);
    }

    public static Map P() {
        return Minecraft.umbraInstance.getMappings().U.h$src$Ljava_util_Map_$8i5rvc(Minecraft.i());
    }

    public static RenderManager D() {
        return ForgeVersion.MC_1_7_10.L() ? RenderManager.getInstance() : new RenderManager(Minecraft.umbraInstance.getMappings().U.w(Minecraft.i()));
    }

    public static GuiSpriteManager T() {
        return new GuiSpriteManager(MMinecraft.p(Minecraft.umbraInstance.getMappings().U, Minecraft.i()));
    }

    public static boolean l$src$Z$b9uwii() {
        boolean bl = Minecraft.g();
        return false;
    }

    public static int h() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return Minecraft.p().R();
        }
        return Minecraft.umbraInstance.getMappings().U.R(Minecraft.i());
    }

    public static void O(boolean bl) {
        MMinecraft.u(Minecraft.umbraInstance.getMappings().U, Minecraft.i(), bl);
    }

    public static TextureManagerHandle getMainRenderTarget() {
        if (!ForgeVersion.MC_1_21_10.d()) {
            return null;
        }
        return new TextureManagerHandle(Minecraft.umbraInstance.getMappings().U.V(Minecraft.i()));
    }

    public static boolean a() {
        return Minecraft.umbraInstance.getMappings().U.f(Minecraft.i());
    }

    public static EffectRenderer z() {
        return new EffectRenderer(MMinecraft.v(Minecraft.umbraInstance.getMappings().U, Minecraft.i()));
    }

    public static EntityLivingBase F() {
        return new EntityLivingBase(Minecraft.umbraInstance.getMappings().U.h(Minecraft.i()));
    }

    public static ModelManager x() {
        return new ModelManager(MMinecraft.n(Minecraft.umbraInstance.getMappings().U, Minecraft.i()));
    }

    public static boolean F$src$Z$aoypys() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return Minecraft.p().x();
        }
        return MMinecraft.f(Minecraft.umbraInstance.getMappings().U, Minecraft.i());
    }

    public static VoxelShape H$src$Lgg_umbra_wrapper_impl_VoxelShape_$1dlcquv() {
        return new VoxelShape(MMinecraft.x(Minecraft.umbraInstance.getMappings().U, Minecraft.i()));
    }

    public static FontRenderer getFontRenderer() {
        return new FontRenderer(Minecraft.umbraInstance.getMappings().U.J(Minecraft.i()));
    }

    public static ScaledResolution G() {
        return new ScaledResolution();
    }

    public static PlayerControllerMP playerController() {
        return new PlayerControllerMP(Minecraft.umbraInstance.getMappings().U.r(Minecraft.i()));
    }

    public static int Q() {
        return MMinecraft.S(Minecraft.umbraInstance.getMappings().U, Minecraft.i());
    }

    public static Framebuffer getFrameBuffer() {
        return new Framebuffer(Minecraft.umbraInstance.getMappings().U.u(Minecraft.i()));
    }

    public static TextureManagerBridge M() {
        return new TextureManagerBridge(Minecraft.umbraInstance.getMappings().U.P(Minecraft.i()));
    }

    public static Entity Y() {
        return new Entity(Minecraft.umbraInstance.getMappings().U.F(Minecraft.i()));
    }

    public static void e(boolean bl) {
        s = bl;
    }

    public static FontManager q() {
        if (!ForgeVersion.MC_1_21_10.d()) {
            return null;
        }
        Object object = Minecraft.umbraInstance.getMappings().U.j(Minecraft.i());
        return object != null ? new FontManager(object) : null;
    }

    public static int J() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return Minecraft.p().I();
        }
        return Minecraft.umbraInstance.getMappings().U.Y(Minecraft.i());
    }

    public static boolean m$src$Z$baep3v() {
        return Minecraft.umbraInstance.getMappings().U.X$src$Z$1ecebix(Minecraft.i());
    }

    public static Object u() {
        return Minecraft.umbraInstance.getMappings().U.R$src$Ljava_lang_Object_$11ec019(Minecraft.i());
    }

    public static RenderItem v() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return RenderItem.d();
        }
        return new RenderItem(MMinecraft.k(Minecraft.umbraInstance.getMappings().U, Minecraft.i()));
    }

    public static ResourceManager getResourcePackRepository() {
        return new ResourceManager(Minecraft.umbraInstance.getMappings().U.X(Minecraft.i()));
    }

    public static Timer getTimer() {
        return new Timer(MMinecraft.W(Minecraft.umbraInstance.getMappings().U, Minecraft.i()));
    }

    public static KeyboardHandler r() {
        return new KeyboardHandler(MMinecraft.o(Minecraft.umbraInstance.getMappings().U, Minecraft.i()));
    }

    public static GameSettings gameSettings() {
        return new GameSettings(Minecraft.umbraInstance.getMappings().U.b(Minecraft.i()));
    }

    public static MouseHandler s() {
        return new MouseHandler(MMinecraft.X(Minecraft.umbraInstance.getMappings().U, Minecraft.i()));
    }

    public static NetHandlerPlayClientImpl N() {
        return new NetHandlerPlayClientImpl(Minecraft.umbraInstance.getMappings().U.a(Minecraft.i()));
    }

    public static EntityPlayerSP thePlayer() {
        return new EntityPlayerSP(Umbra.INSTANCE.getMappings().U.T(Minecraft.i()));
    }

    public static int l() {
        return Minecraft.umbraInstance.getMappings().U.L();
    }

    public static boolean g() {
        return s;
    }

    public static void B(GuiScreen guiScreen) {
        Minecraft.umbraInstance.getMappings().U.r(Minecraft.i(), guiScreen.getObject());
    }

    public static void E(int n) {
        MMinecraft.v(Minecraft.umbraInstance.getMappings().U, Minecraft.i(), n);
    }

    public static void W(Entity entity) {
        Minecraft.umbraInstance.getMappings().U.a(Minecraft.i(), entity.getObject());
    }

    public static TitledScreen k() {
        return new TitledScreen(Minecraft.umbraInstance.getMappings().U.A(Minecraft.i()));
    }

    public static RenderTypeBuffer getFramerateLimitTracker() {
        return new RenderTypeBuffer(Minecraft.umbraInstance.getMappings().U.S(Minecraft.i()));
    }

    public Minecraft() {
        super(Umbra.INSTANCE.getMappings().U.J());
    }

    public static String getSessionUsername() {
        try {
            Object session = MMinecraft.G(Minecraft.umbraInstance.getMappings().U, Minecraft.i());
            if (session == null) {
                return "Player";
            }
            return (String) Minecraft.umbraInstance.getMappings().hw.s(session);
        }
        catch (Throwable ignored) {
            return "Player";
        }
    }

    public static Object i() {
        if (L == null) {
            L = Minecraft.umbraInstance.getMappings().U.J();
        }
        return L;
    }

    public static void S() {
        MMinecraft.Y(Minecraft.umbraInstance.getMappings().U, Minecraft.i());
    }

    public static TextureManager getTextureManager() {
        return new TextureManager(Minecraft.umbraInstance.getMappings().U.c(Minecraft.i()));
    }

    public static void R() {
        MMinecraft.b(Minecraft.umbraInstance.getMappings().U, Minecraft.i());
    }

    public static void r(EntityLivingBase entityLivingBase) {
        Minecraft.umbraInstance.getMappings().U.G(Minecraft.i(), entityLivingBase == null ? null : entityLivingBase.getObject());
    }


    public static void currentScreen(Object object) {
        Minecraft.umbraInstance.getMappings().U.Y(Minecraft.i(), object);
    }

    public static RayTraceResult p$src$Lgg_umbra_wrapper_impl_RayTraceResult_$5rw6n0() {
        return new RayTraceResult(Minecraft.umbraInstance.getMappings().U.q(Minecraft.i()));
    }

    public static MouseHelper p() {
        return new MouseHelper(MMinecraft.y$src$Ljava_lang_Object_$1igycos(Minecraft.umbraInstance.getMappings().U, Minecraft.i()));
    }

    public static EntityRenderer m$src$Lgg_umbra_wrapper_impl_EntityRenderer_$13begmf() {
        return new EntityRenderer(Minecraft.umbraInstance.getMappings().U.m(Minecraft.i()));
    }

    public static void v(Runnable runnable) {
        MMinecraft.m(Minecraft.umbraInstance.getMappings().U).invokeVoid(Minecraft.i(), runnable);
    }

    public static ServerData H() {
        return new ServerData(Minecraft.umbraInstance.getMappings().U.l(Minecraft.i()));
    }

    public static WorldClient theWorld() {
        return new WorldClient(Minecraft.umbraInstance.getMappings().U.E(Minecraft.i()));
    }

    public static RenderGlobal O() {
        return new RenderGlobal(MMinecraft.N(Minecraft.umbraInstance.getMappings().U, Minecraft.i()));
    }

    public static void F$src$V$aoypvc() {
        Minecraft.umbraInstance.getMappings().U.V$src$V$9672l7(Minecraft.i());
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.s().u();
        }
    }

    public static void X(int n) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().a(n);
            return;
        }
        Minecraft.umbraInstance.getMappings().U.B(Minecraft.i(), n);
    }

    public static void O(RayTraceResult rayTraceResult) {
        Minecraft.umbraInstance.getMappings().U.P(Minecraft.i(), rayTraceResult.getObject());
    }

    public static void U(int n) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().H(n);
            return;
        }
        Minecraft.umbraInstance.getMappings().U.R(Minecraft.i(), n);
    }

    public static GuiScreen currentScreen() {
        return new GuiScreen(Minecraft.umbraInstance.getMappings().U.U(Minecraft.i()));
    }

    public static Timer a_jo_2_T() {
        return Minecraft.getTimer();
    }

    public static EntityPlayerSP a_xH_J() {
        return Minecraft.thePlayer();
    }

    public static GuiScreen a_pt_1_w() {
        return Minecraft.currentScreen();
    }

    public static GameSettings a_w3_0_S() {
        return Minecraft.gameSettings();
    }
}

