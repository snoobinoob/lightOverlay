package lightOverlay.command;

import lightOverlay.LightOverlay;
import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.FloatParameterHandler;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameMath;

public class ModifyOverlayAlphaCommand extends ModularChatCommand {
    public ModifyOverlayAlphaCommand() {
        super("lightoverlayalpha", "Edit overlay alpha", PermissionLevel.USER, false, new CmdParameter("value", new FloatParameterHandler(1f)));
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog commandLog) {
        float newValue = GameMath.limit((float)args[0], 0, 1);

        LightOverlay.settings.alpha = newValue;
        commandLog.add("Set light overlay alpha to: " + newValue);
    }
}
