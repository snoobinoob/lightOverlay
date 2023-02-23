package lightOverlay;

import org.lwjgl.glfw.GLFW;
import necesse.engine.commands.CommandsManager;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.clientCommands.BoolClientCommand;
import necesse.engine.control.Control;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.hostile.ZombieMob;
import necesse.gfx.gameTexture.GameTexture;

@ModEntry
public class LightOverlay {
    public static GameTexture lowLightTexture;
    public static Mob fakeHostileMob;

    public static Control toggleOverlayControl;

    public static Settings settings;

    public void init() {
        fakeHostileMob = new ZombieMob();

        toggleOverlayControl =
                Control.addModControl(new Control(GLFW.GLFW_KEY_F8, "toggleoverlay"));
    }

    public void initResources() {
        lowLightTexture = GameTexture.fromFile("texture/lowlight");
    }

    public Settings initSettings() {
        settings = new Settings();
        return settings;
    }

    public void postInit() {
        try {
            CommandsManager.registerClientCommand(new BoolClientCommand("lightoverlay",
                    "Toggle Light Overlay", PermissionLevel.USER,
                    Settings.class.getField("drawOverlay"), settings));
        } catch (NoSuchFieldException | SecurityException e) {
            System.out.println("Could not register lightoverlay command: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
