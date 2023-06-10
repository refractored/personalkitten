package net.refractored.personalkitten;

import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.refractored.personalkitten.runnables.RegisterListeners;
import net.refractored.personalkitten.runnables.ThreadManager;
import net.refractored.personalkitten.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class PersonalKitten {
    private static final Logger logger = LogManager.getLogger(PersonalKitten.class);
    private static ShardManager jda;
    private static ThreadManager threadManager;

    public static void start() throws SQLException {
        logger.info("Starting kitten meow...");
        threadManager = new ThreadManager();
        threadManager.start();
        jda = DefaultShardManagerBuilder.createDefault(Config.getConfig().getToken()).build();
        threadManager.add(new RegisterListeners());
    }

    public static ShardManager getJda() {
        return jda;
    }

    public static ThreadManager getThreadManager() {
        return threadManager;
    }
}
