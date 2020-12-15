// Thieving training script for lvs 5-25
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.entity.SceneObject;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.time.Time;

import java.util.List;

@ScriptManifest(name = "Baker Stall", gameType = GameType.OS)
public class BakerStall extends LoopScript {
    private List<SceneObject> stalls;
    @Override
    protected int loop() {
        if(getAPIContext().inventory().isFull()) {
            getAPIContext().inventory().dropAll();
            Time.sleep(10000, () -> getAPIContext().inventory().isEmpty());
        } else {
            stalls = getAPIContext().objects().query().named("Baker's stall").visible().results().toList();
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
