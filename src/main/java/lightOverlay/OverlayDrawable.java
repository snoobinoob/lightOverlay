package lightOverlay;

import necesse.engine.tickManager.TickManager;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;

public class OverlayDrawable extends LevelSortedDrawable {
    private final TextureDrawOptions options;

    public OverlayDrawable(int tileX, int tileY, GameCamera camera) {
        super(null, tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);

        options = LightOverlay.lowLightTexture.initDraw().alpha(LightOverlay.settings.alpha).pos(drawX, drawY);
    }

    @Override
    public int getSortY() {
        return 0;
    }

    @Override
    public void draw(TickManager tickManager) {
        options.draw();
    }
}
