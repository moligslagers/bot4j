package ai.nitro.bot4j.middle.repo.impl;

import ai.nitro.bot4j.bot.Bot;
import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.inject.Injector;
import com.restfb.FacebookClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Markus Oligslagers on 26.04.2017.
 * Implentation of Bot Repository for the Bot4J Framework. Extends the Framework with a stateful bot repository that
 * manages several bot implementations at run time
 */
@Singleton
public class StafefulBotProviderServiceImpl implements StatefulBotProviderService {

    private final static Logger LOG = LogManager.getLogger(StatefulBotProviderService.class);


    @Inject
    private Injector injector;

    private Map<Long, Bot> bots = new HashMap<>();

    private Map<String, Class<? extends BotImpl>> botTypes = new HashMap<>();

    private Map<Long, FacebookClient> facebookClients = new HashMap<>();

    @Override
    public String deleteBot(Long botId) {
        LOG.info(String.format("Deleting Bot %s", botId), StatefulBotProviderService.class);
        LOG.info(String.format("Number of Bots: %s", bots.size()), StatefulBotProviderService.class);

        bots.remove(botId);
        facebookClients.remove(botId);

        LOG.info(String.format("Deleted Bot %s", botId), StatefulBotProviderService.class);
        LOG.info(String.format("Number of Bots: %s", bots.size()), StatefulBotProviderService.class);
        return String.format("Successfully deleted bot %s", botId);
    }

    @Override
    public Bot getBot(Long botId) {
        LOG.info(String.format("Getting Bot %s", botId), StatefulBotProviderService.class);
        return bots.get(botId);
    }

    @Override
    public Map<Long, Bot> getBots() {
        LOG.info(String.format("Getting List of %s Bots", bots.size()), StatefulBotProviderService.class);
        return bots;
    }

    @Override
    public Set<String> getBotTypes() {
        LOG.info(String.format("Getting List of %s BotTypes", botTypes.size()), StatefulBotProviderService.class);
        return this.botTypes.keySet();
    }

    @Override
    public FacebookClient getFacebookClient(Long botId) {
        LOG.info(String.format("Getting FacebookClient for Bot: %s", botId), StatefulBotProviderService.class);
        return facebookClients.get(botId);
    }

    @Override
    public Map<Long, FacebookClient> getFacebookClients() {
        LOG.info(String.format("Getting List of %s FacebookClients", facebookClients.size()), StatefulBotProviderService.class);
        return this.facebookClients;
    }

    /**
     * Add a new bot to the bot repository
     *
     * @param botId   the id to retrieve the bot back from
     * @param botType name of the bot class implementation the bot should be instantiated from
     * @return String
     */
    @Override
    public String putBot(Long botId, String botType) {
        if (bots.containsKey(botId)) {
            return String.format("A Bot with id %s already exists", botId);
        }
        LOG.info(String.format("Number of Bots: %s", bots.size()), StatefulBotProviderService.class);

        BotImpl bot = injector.getInstance(botTypes.get(botType));
        bot.setBotId(botId);
        bots.put(botId, bot);

        LOG.info(String.format("Adding  Bot %s", botId), StatefulBotProviderService.class);
        LOG.info(String.format("Number of Bots: %s", bots.size()), StatefulBotProviderService.class);

        return String.format("Added new bot with id %s", botId);
    }

    /**
     * Add a new FacebookClient for a bot
     *
     * @param botId          The botId the FacebookClient belongs to
     * @param facebookClient A FacebookClient containint the access token
     * @return String
     */
    @Override
    public String putFacebookClient(Long botId, FacebookClient facebookClient) {
        if (facebookClients.containsKey(botId)) {
            if (!bots.containsKey(botId)) {
                LOG.warn(String.format("Facebook Client with ID %s has no corresponding Bot", botId));
            }
            return String.format("A Facebook Client for Bot %s already exists", botId);
        }
        LOG.info(String.format("Adding new FacebookClient for bot%s", botId), StatefulBotProviderService.class);
        facebookClients.put(botId, facebookClient);
        return String.format("Added new facebookClient for bot %s", botId);
    }

    /**
     * Add a new Bot class to @botTypes, to be called at application start to provide custom Bot implementations
     */
    @Override
    public String registerBot(Class<? extends BotImpl> botClass, String botType) {

        LOG.info(String.format("Registering new BotType %s", botType), StatefulBotProviderService.class);
        if (botTypes.containsKey(botType)) {
            LOG.warn(String.format("Duplicate Bot Type %s. Initial Bot Type will be overwritten.", botType));
        }
        botTypes.put(botType, botClass);
        return String.format("Added new BotType %s", botType);
    }

    @Override
    public String updateBot(Long botId, String botType, FacebookClient facebookClient) {
        //Currently unused
        if (!bots.containsKey(botId)) {
            return String.format("Bot cannot be updated. No Bot with id %s", botId);
        }

        LOG.info(String.format("Updating Bot with id %s to new type %s", botId, botType), StatefulBotProviderService.class);
        LOG.info(String.format("Number of Bots: %s", bots.size()), StatefulBotProviderService.class);

        bots.remove(botId);
        Bot bot = injector.getInstance(botTypes.get(botType));
        bots.put(botId, bot);

        facebookClients.remove(botId);
        facebookClients.put(botId, facebookClient);

        return String.format(
                "Updated Type and Access Token for Bot %s. \n" +
                        "New type: %s \n", botId, botType);
    }
}
