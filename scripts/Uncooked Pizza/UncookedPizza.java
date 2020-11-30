//Script to produce uncooked pizzas at any bank.  Created by Ziertus, 30/11/2020
//Special Thanks to dankmemer#5590 and Suko#1406 for their support in the creation of this script
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.time.Time;
import com.epicbot.api.shared.webwalking.model.RSBank;


@ScriptManifest(name = "Uncooked Pizzeria", gameType = GameType.OS)
public class UncookedPizza extends LoopScript {
    private RSBank bankLocation;

    @Override
    protected int loop() {
//        if (bankLocation.getTile().distanceTo(getAPIContext(), getAPIContext().localPlayer().getLocation()) > 7) {
//            getAPIContext().webWalking().walkTo(bankLocation.getTile());
//        }
        if (!getAPIContext().bank().isOpen()) {
            if (getAPIContext().bank().open()) {
                Time.sleep(5000, () -> getAPIContext().bank().isOpen());
            }
            if (getAPIContext().bank().depositInventory()) {
                Time.sleep(5000, () -> getAPIContext().inventory().isEmpty());
            }
            if (!getAPIContext().bank().containsAll(2283, 1982, 1985)){
                getAPIContext().script().stop("No more materials.");
                return 404;
            }
            if (getAPIContext().bank().withdraw(9, 2283)) {
                Time.sleep(5000, () -> getAPIContext().inventory().contains(2283));
            }
            if (getAPIContext().bank().withdraw(9, 1982)) {
                Time.sleep(5000, () -> getAPIContext().inventory().contains(1982));
            }
            if (getAPIContext().bank().withdraw(9, 1985)) {
                Time.sleep(5000, () -> getAPIContext().inventory().contains(1985));
            }
            while(getAPIContext().bank().isOpen()) {
                if(getAPIContext().bank().close()) {
                    Time.sleep(1000);
                }
            }
            if(getAPIContext().inventory().selectItem(2283)) {
                Time.sleep(5000, () -> getAPIContext().inventory().isItemSelected());
            }
            if(getAPIContext().inventory().selectItem(1982)) {
                Time.sleep(5000, () -> (getAPIContext().widgets().get(270, 14).isVisible()));
            }
            if(getAPIContext().mouse().click(getAPIContext().widgets().get(270,14))) {
                Time.sleep(20000, () -> (!getAPIContext().inventory().contains(2283)));
            }
            if(getAPIContext().inventory().selectItem(2285)) {
                Time.sleep(5000, () -> getAPIContext().inventory().isItemSelected());
            }
            if(getAPIContext().inventory().selectItem(1985)) {
                Time.sleep(5000, () -> (getAPIContext().widgets().get(270, 14).isVisible()));
            }
            if(getAPIContext().mouse().click(getAPIContext().widgets().get(270,14))) {
                Time.sleep(20000, () -> (!getAPIContext().inventory().contains(2285)));
            }
        }
        return 200;
    }

    @Override
    public boolean onStart(String... strings) {
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
