import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.time.Time;
import com.epicbot.api.shared.webwalking.model.RSBank;

import java.awt.*;

@ScriptManifest(name = "Pineapple Pizza", gameType = GameType.OS)
public class PineapplePizza extends LoopScript {
    private RSBank bankLocation;
    private long startTime;

    @Override
    protected void onPaint(Graphics2D g, APIContext ctx){
        g.setColor(new Color(60, 10, 250, 250 ));
        g.fillRoundRect(0, 200, 100, 30, 3, 3);
        g.setColor(new Color(140, 130, 0, 255 ));
        g.drawString("Runtime: "+ Time.getFormattedRuntime(startTime), 0, 215);
    }

    @Override
    protected int loop() {
        while (bankLocation.getTile().distanceTo(getAPIContext(), getAPIContext().localPlayer().getLocation()) > 7) {
            getAPIContext().webWalking().walkTo(bankLocation.getTile());
            Time.sleep(5000);
        }
        if (!getAPIContext().bank().isOpen()) {
            if (getAPIContext().bank().open()) {
                Time.sleep(10000, () -> getAPIContext().bank().isOpen());
                if (getAPIContext().bank().depositInventory()) {
                    Time.sleep(5000, () -> getAPIContext().inventory().isEmpty());
                }
                if (!getAPIContext().bank().containsAll(2289, 2118)){
                    System.out.println("Total Runtime: "+ Time.getFormattedRuntime(startTime));
                    getAPIContext().script().stop("No more materials.");
                    return 404;
                }
                if (getAPIContext().bank().withdraw(14, 2289)) {
                    Time.sleep(5000, () -> getAPIContext().inventory().contains(2289));
                }
                if (getAPIContext().bank().withdraw(14, 2118)) {
                    Time.sleep(5000, () -> getAPIContext().inventory().contains(2118));
                }
                while(getAPIContext().bank().isOpen()) {
                    if(getAPIContext().bank().close()) {
                        Time.sleep(1000);
                    }
                }
            }
        }
        if(getAPIContext().inventory().selectItem(2289)) {
            Time.sleep(5000, () -> getAPIContext().inventory().isItemSelected());
        }
        if(getAPIContext().inventory().selectItem(2118)) {
            Time.sleep(5000, () -> (getAPIContext().widgets().get(270, 14).isVisible()));
        }
        if(getAPIContext().mouse().click(getAPIContext().widgets().get(270,14))) {
            getAPIContext().mouse().moveOffScreen();
            Time.sleep(20000, () -> (!getAPIContext().inventory().containsAll(2289, 2118)));
        }
        return 0;
    }

    @Override
    public boolean onStart(String... strings) {
        startTime = System.currentTimeMillis();
        bankLocation = RSBank.GRAND_EXCHANGE;
        for(int i = 0; i < RSBank.values().length; i++) {
            if(bankLocation == null) {
                bankLocation = RSBank.values()[i];
            } else {
                if(getAPIContext().localPlayer().getLocation().distanceTo(getAPIContext(), RSBank.values()[i].getTile()) < getAPIContext().localPlayer().getLocation().distanceTo(getAPIContext(), bankLocation.getTile())) {
                    bankLocation = RSBank.values()[i];
                }
            }
        }
        return true;
    }
}
