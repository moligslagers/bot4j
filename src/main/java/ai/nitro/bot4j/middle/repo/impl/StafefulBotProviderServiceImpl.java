package ai.nitro.bot4j.middle.repo.impl;

import ai.nitro.bot4j.bot.Bot;
import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import ai.nitro.bot4j.integration.facebook.receive.webhook.impl.FacebookWebhookImpl;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.inject.Injector;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Markus on 26.04.2017.
 */
@Singleton
public class StafefulBotProviderServiceImpl implements StatefulBotProviderService{

    private final static Logger LOG = LogManager.getLogger(FacebookWebhookImpl.class);


    @Inject
    Injector injector;

    protected Map<Long, Bot> bots = new HashMap<>();

    protected Map<String, Class<? extends BotImpl>> botTypes = new HashMap<>();

    protected Map<Long, FacebookClient> facebookClients = new HashMap<>();

    @Override
    public String deleteBot(Long botId) {
        LOG.info(String.format("Number of Bots: %s", bots.size()), StatefulBotProviderService.class);
        LOG.info(String.format("Deleting Bot %s", botId), StatefulBotProviderService.class);

        bots.remove(botId);
        facebookClients.remove(botId);

        LOG.info(String.format("Deleted Bot %s", botId), StatefulBotProviderService.class);
        LOG.info(String.format("Number of Bots: %s", bots.size()), StatefulBotProviderService.class);
        return String.format("Successfully deleted bot %s", botId);
    }

    @Override
    public Bot getBot(Long botId){
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
    public FacebookClient getFacebookClient(Long botId){
        LOG.info(String.format("Getting FacebookClient for Bot: %s", botId), StatefulBotProviderService.class);
        return facebookClients.get(botId);
    }

    @Override
    public Map<Long, FacebookClient> getFacebookClients() {
        LOG.info(String.format("Getting List of %s FacebookClients", facebookClients.size()), StatefulBotProviderService.class);
        return this.facebookClients;
    }

    @Override
    public String putBot(Long botId, String botType){
        LOG.info(String.format("Adding new Bot with id %s and type %s", botId, botType), StatefulBotProviderService.class);
        BotImpl bot = injector.getInstance(botTypes.get(botType));
        bot.setBotId(botId);
        bots.put(botId, bot);
        return String.format("Added new bot with id %s", botId);
    }

    @Override
    public String putFacebookClient(Long botId, FacebookClient facebookClient){
        LOG.info(String.format("Adding new FacebookClient for bot%s", botId), StatefulBotProviderService.class);
        facebookClients.put(botId, facebookClient);
        return String.format("Added new facebookClient for bot %s", botId);
    }

    @Override
    public String registerBot(Class<? extends BotImpl> botClass, String botType) {
        LOG.info(String.format("Registering new BotType %s", botType), StatefulBotProviderService.class);
        botTypes.put(botType, botClass);
        return String.format("Added new BotType %s", botType);
    }

    @Override
    public String updateBot(Long botId, String botType, FacebookClient facebookClient) {
        LOG.info(String.format("Updating Bot with id %s to new type %s", botId, botType), StatefulBotProviderService.class);

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
