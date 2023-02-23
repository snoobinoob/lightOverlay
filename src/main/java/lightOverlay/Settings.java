package lightOverlay;

import necesse.engine.modLoader.ModSettings;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

public class Settings extends ModSettings {
    public boolean drawOverlay;

    @Override
    public void addSaveData(SaveData save) {
        save.addBoolean("drawoverlay", drawOverlay);
    }

    @Override
    public void applyLoadData(LoadData save) {
        if (save == null)
            return;
        drawOverlay = save.getBoolean("drawoverlay", false);
    }

}
