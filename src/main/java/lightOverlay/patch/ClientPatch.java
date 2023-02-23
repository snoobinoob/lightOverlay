package lightOverlay.patch;

import lightOverlay.LightOverlay;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.state.MainGame;
import necesse.engine.tickManager.TickManager;
import net.bytebuddy.asm.Advice;

public class ClientPatch {
    @ModMethodPatch(target = MainGame.class, name = "frameTick", arguments = {TickManager.class})
    public static class FrameTickPatch {
        @Advice.OnMethodExit
        public static void update(@Advice.This MainGame mainGame) {
            if (LightOverlay.toggleOverlayControl.isPressed()) {
                LightOverlay.settings.drawOverlay = !LightOverlay.settings.drawOverlay;
            }
        }
    }
}
