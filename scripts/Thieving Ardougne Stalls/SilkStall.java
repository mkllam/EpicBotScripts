// Thieving training script for lv25-45
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.entity.SceneObject;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.time.Time;

import java.util.List;

@ScriptManifest(name = "Silk Stall", gameType = GameType.OS)
public class SilkStall extends LoopScript {
    private List<SceneObject> stalls;
    @Override
    protected int loop() {
        if(getAPIContext().inventory().isFull()) {
            getAPIContext().inventory().dropAll();
            Time.sleep(10000, () -> getAPIContext().inventory().isEmpty());
        } else {
            stalls = getAPIContext().objects().query().named("Silk stall").visible().results().toList();
            if(stalls.size() > 0) {
                stalls.get(0).click();
                Time.sleep(4000); // Delay for animation
            }
        }
        return 0;
    }

    @Override
    public boolean onStart(String... strings) {
        return true;
    }
}
