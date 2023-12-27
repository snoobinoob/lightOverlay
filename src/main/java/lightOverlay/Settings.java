package lightOverlay;

import necesse.engine.modLoader.ModSettings;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

public class Settings extends ModSettings {
    public boolean drawOverlay;
    public float alpha;

    @Override
    public void addSaveData(SaveData save) {
        save.addBoolean("drawoverlay", drawOverlay);
        save.addFloat("alpha", alpha, "Float in the range [0, 1]. 0 is clear, 1 fully opaque");
    }

    @Override
    public void applyLoadData(LoadData save) {
        if (save == null)
            return;
        drawOverlay = save.getBoolean("drawoverlay", false);
        alpha = save.getFloat("alpha", 1, 0, 1);
    }

}
