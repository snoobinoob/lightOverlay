package lightOverlay.patch;

import java.util.List;
import lightOverlay.LightOverlay;
import lightOverlay.OverlayDrawable;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.tickManager.TickManager;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.gameObject.GameObject;
import necesse.level.gameTile.GameTile;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;

public class GameObjectPatch {
    @ModMethodPatch(target = GameObject.class, name = "addDrawables",
            arguments = {List.class, OrderableDrawables.class, Level.class, int.class, int.class,
                    TickManager.class, GameCamera.class, PlayerMob.class})
    public static class AddLightDrawablesPatch {
        @Advice.OnMethodExit
        public static void onExit(@Advice.This GameObject obj,
                @Advice.Argument(0) List<LevelSortedDrawable> list, @Advice.Argument(2) Level level,
                @Advice.Argument(3) int tileX, @Advice.Argument(4) int tileY,
                @Advice.Argument(6) GameCamera camera, @Advice.Argument(7) PlayerMob player) {
            if (!LightOverlay.settings.drawOverlay)
                return;
            GameTile tile = level.getTile(tileX, tileY);
            if (tile.isLiquid || obj.isSolid)
                return; // mobs do not spawn in liquids or in solid objects
            if (!level.isCave && tile.isFloor && !level.isOutside(tileX, tileY))
                return; // Mobs on the surface don't spawn inside on floor
            if (level.lightManager.getStaticLight(tileX, tileY).getLevel() <= 50) {
                list.add(new OverlayDrawable(tileX, tileY, camera));
            }
        }
    }
}
