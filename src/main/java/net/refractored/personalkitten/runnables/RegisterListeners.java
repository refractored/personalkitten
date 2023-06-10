package net.refractored.personalkitten.runnables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.refractored.personalkitten.PersonalKitten;
import net.refractored.personalkitten.listeners.CommandListener;
import net.refractored.personalkitten.listeners.ReadyListener;

public class RegisterListeners extends EndableRunnable {
    private final static Logger logger = LogManager.getLogger(RegisterListeners.class);
    @Override
    public void run() {
        if (isRunning()) {
            logger.info("Registering listeners...");
            PersonalKitten.getJda().addEventListener(new ReadyListener());
            PersonalKitten.getJda().addEventListener(new CommandListener());
            logger.info("Registered listeners.");
        }
        stopped = true;
    }

    @Override
    public String getName() {
        return "RegisterListeners";
    }
}
