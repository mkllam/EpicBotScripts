import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.entity.SceneObject;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.time.Time;

import java.awt.*;


@ScriptManifest(name = "Canifis Rooftop Course", gameType = GameType.OS)
public class CanifisRoofTop extends LoopScript {
    private int stage = 0, token = 0, startEXP, startLv;
    private SceneObject start;
    private long startTime;

    @Override
    protected void onPaint(Graphics2D g, APIContext ctx){
        g.setColor(new Color(60, 10, 250, 250 ));
        g.fillRoundRect(0, 200, 100, 70, 5, 5);
        g.setColor(new Color(140, 130, 0, 255 ));
        g.drawString("Runtime: "+ Time.getFormattedRuntime(startTime), 5, 215);
        g.drawString("Tokens: "+ token, 5, 230);
        g.drawString("Lv: " + getAPIContext().skills().agility().getCurrentLevel() + " (+"
                         + (getAPIContext().skills().agility().getCurrentLevel()-startLv) + ")", 5, 245);
        g.drawString("EXP: "+ (getAPIContext().skills().agility().getExperience()-startEXP), 5, 260);
    }

    @Override
    protected int loop() {
        if(getAPIContext().groundItems().query().id(11849).reachable().results().toList().size() > 0){
            getAPIContext().groundItems().query().id(11849).results().first().interact("Take");
            Time.sleep(1000, () -> getAPIContext().localPlayer().isMoving());
            Time.sleep(3000, () -> (!getAPIContext().localPlayer().isMoving()));
            Time.sleep(500);
            token++;
            System.out.println("Tokens Acquired: "+token);
            return 0;
        }

        if(stage == 0) {
            getAPIContext().objects().query().id(14843).results().first().interact("Climb");
            Time.sleep(1000);
            waitUntilIdle();
            stage=1;
            System.out.println("STAGE: "+stage);
            return 0;
        }

        if(stage == 1) {
            getAPIContext().objects().query().id(14844).results().first().interact("Jump");
            waitUntilIdle();
            if(getAPIContext().objects().query().id(14845).reachable().results().toList().size() > 0) {
                stage=2;
            }
            System.out.println("STAGE: "+stage);
            return 0;
        }
        if(stage == 2) {
            getAPIContext().objects().query().id(14845).results().first().interact("Jump");
            waitUntilIdle();
            if(getAPIContext().objects().query().id(14848).reachable().results().toList().size() > 0) {
                stage=3;
            }
            System.out.println("STAGE: "+stage);
            Time.sleep(1000);
            return 0;
        }
        if(stage == 3) {
            if(getAPIContext().objects().query().id(14848).reachable().results().toList().size() == 0) {
                getAPIContext().webWalking().walkTo(start);
                Time.sleep(4000, () -> (getAPIContext().localPlayer().isMoving()));
                Time.sleep(8000, () -> (!getAPIContext().localPlayer().isMoving()));
                stage = 0;
                return 0;
            }
            getAPIContext().objects().query().id(14848).results().first().interact("Jump");
            waitUntilIdle();
            if(getAPIContext().objects().query().id(14846).reachable().results().toList().size() > 0) {
                stage=4;
            }
            System.out.println("STAGE: "+stage);
            return 0;
        }
        if(stage == 4) {
            getAPIContext().objects().query().id(14846).results().first().click();//.interact("Jump");
            waitUntilIdle();
            if(getAPIContext().objects().query().id(14894).reachable().results().toList().size() > 0) {
                stage=5;
            }
            System.out.println("STAGE: "+stage);
            return 0;
        }
        if(stage == 5) {
            if(getAPIContext().objects().query().id(14847).results().toList().size() > 0) {
                stage=6;
                return 0;
            }
            getAPIContext().objects().query().id(14894).results().first().click();//.interact("Vault");
            waitUntilIdle();
            if(getAPIContext().objects().query().id(14894).reachable().results().toList().size() == 0) {
                stage=6;
            }
            System.out.println("STAGE: "+stage);
            return 0;
        }
        if(stage == 6) {
            if(getAPIContext().objects().query().id(14843).reachable().results().toList().size() > 0) {
                stage=0;
                return 0;
            }
            getAPIContext().objects().query().id(14847).results().first().interact("Jump");
            Time.sleep(1000, () -> getAPIContext().localPlayer().isMoving());
            Time.sleep(6000, () -> (!getAPIContext().localPlayer().isMoving()));
            Time.sleep(1000, () -> getAPIContext().localPlayer().isAnimating());
            Time.sleep(4000, () -> (!getAPIContext().localPlayer().isAnimating()));
            Time.sleep(500);
            if(getAPIContext().objects().query().id(14897).reachable().results().toList().size() > 0) {
                stage=7;
            }
            System.out.println("STAGE: "+stage);
            return 0;
        }
        if(stage == 7) {
            getAPIContext().objects().query().id(14897).results().first().interact("Jump");
            waitUntilIdle();
            if(getAPIContext().objects().query().id(14843).reachable().results().toList().size() > 0) {
                stage=0;
            }
            System.out.println("STAGE: "+stage);
            return 0;
        }
        return 0;
    }

    @Override
    public boolean onStart(String... strings) {
        System.out.println("Canifis Rooftop Course Start!");
        startTime = System.currentTimeMillis();
        startEXP = getAPIContext().skills().agility().getExperience();
        startLv = getAPIContext().skills().agility().getCurrentLevel();
        start = getAPIContext().objects().query().id(14843).results().first();
        return true;
    }

    protected void waitUntilIdle(){
        Time.sleep(1000, () -> getAPIContext().localPlayer().isMoving());
        Time.sleep(4000, () -> (!getAPIContext().localPlayer().isMoving()));
        Time.sleep(1000, () -> getAPIContext().localPlayer().isAnimating());
        Time.sleep(4000, () -> (!getAPIContext().localPlayer().isAnimating()));
        Time.sleep(500);
    }
}
