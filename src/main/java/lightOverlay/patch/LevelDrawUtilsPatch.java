package lightOverlay.patch;

import lightOverlay.LightOverlay;
import lightOverlay.OverlayDrawable;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.modifiers.Modifier;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.entity.mobs.MobSpawnLocation;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.SharedTextureDrawOptions;
import necesse.gfx.drawables.LevelDrawUtils;
import necesse.gfx.drawables.LevelTileTerrainDrawOptions;
import necesse.gfx.drawables.LevelTileLightDrawOptions;
import necesse.gfx.drawables.LevelTileLiquidDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;

import java.util.List;

public class LevelDrawUtilsPatch {
    @ModMethodPatch(target = LevelDrawUtils.class, name = "addTileBasedDrawProcesses", arguments = {TickManager.class, GameCamera.class, LevelDrawUtils.DrawArea.class, LevelDrawUtils.DrawArea.class, LevelTileTerrainDrawOptions.class, LevelTileLiquidDrawOptions.class, LevelTileTerrainDrawOptions.class, LevelTileLightDrawOptions.class, SharedTextureDrawOptions.class, SharedTextureDrawOptions.class, OrderableDrawables.class, List.class, boolean.class, PlayerMob.class})
    public static class AddTileBasedDrawProcessesPatch {
        @Advice.OnMethodExit
        static void onExit(@Advice.FieldValue("level") Level level, @Advice.Argument(1) GameCamera camera, @Advice.Argument(2) LevelDrawUtils.DrawArea tileArea, @Advice.Argument(10) OrderableDrawables list) {
            if (!LightOverlay.settings.drawOverlay || level.lightManager.ambientLightOverride != null)
                return;
            LightOverlay.fakeHostileMob.setLevel(level);
            Modifier<Integer> modifier = BuffModifiers.MOB_SPAWN_LIGHT_THRESHOLD;
            int threshold = LightOverlay.fakeHostileMob.spawnLightThreshold.limits.applyModifierLimits(
                    modifier,
                    modifier.finalLimit(modifier.appendManager(modifier.defaultBuffManagerValue, LightOverlay.fakeHostileMob.spawnLightThreshold.value))
            );
            for (int tileX = tileArea.startX; tileX <= tileArea.endX; tileX++) {
                for (int tileY = tileArea.startY; tileY <= tileArea.endY; tileY++) {
                    boolean canSpawn = new MobSpawnLocation(LightOverlay.fakeHostileMob, tileX * 32 + 16, tileY * 32 + 16)
                            .checkMaxStaticLightThreshold(threshold)
                            .checkMobSpawnLocation()
                            .validAndApply();
                    if (canSpawn) {
                        list.add(new OverlayDrawable(tileX, tileY, camera));
                    }
                }
            }
        }
    }
}
