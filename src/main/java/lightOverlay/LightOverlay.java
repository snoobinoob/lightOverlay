package lightOverlay;

import lightOverlay.command.ModifyOverlayAlphaCommand;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.input.InputEvent;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import org.lwjgl.glfw.GLFW;
import necesse.engine.commands.CommandsManager;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.clientCommands.BoolClientCommand;
import necesse.engine.input.Control;
import necesse.engine.localization.Localization;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.GlobalData;
import necesse.engine.state.MainGame;
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
                Control.addModControl(new Control(GLFW.GLFW_KEY_F8, "toggleoverlay") {
                    @Override
                    public void activate(InputEvent event) {
                        super.activate(event);
                        if (isPressed()) {
                            settings.drawOverlay = !settings.drawOverlay;

                            MainGame mainGame = (GlobalData.getCurrentState() instanceof MainGame) ? (MainGame)GlobalData.getCurrentState() : null;
                            if (mainGame != null) {
                              //mainGame.getClient().chat.addMessage("LightOverlay is " + (settings.drawOverlay ? "on.":"off."));
                              mainGame.getClient().chat.addMessage(Localization.translate("controls", "togglemessage") + " " + Localization.translate("controls", (settings.drawOverlay ? "on":"off")));
                            }
                        }
                    }
                });
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
                    "Toggle light overlay", PermissionLevel.USER,
                    Settings.class.getField("drawOverlay"), settings));
        } catch (NoSuchFieldException | SecurityException e) {
            System.out.println("Could not register lightoverlay command: " + e.getMessage());
            e.printStackTrace();
        }
        CommandsManager.registerClientCommand(new ModifyOverlayAlphaCommand());
    }
}
