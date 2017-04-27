package ai.nitro.bot4j.middle.repo.impl;

import ai.nitro.bot4j.bot.Bot;
import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.inject.Injector;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.apache.commons.io.input.BOMInputStream;

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


    @Inject
    Injector injector;

    protected Map<Long, Bot> bots = new HashMap<>();

    protected Map<String, Class<? extends BotImpl>> botTypes = new HashMap<>();

    protected Map<Long, FacebookClient> facebookClients = new HashMap<>();

    @Override
    public String deleteBot(Long botId) {
        bots.remove(botId);
        facebookClients.remove(botId);
        return String.format("Successfully deleted bot %s", botId);
    }

    @Override
    public Bot getBot(Long botId){
        return bots.get(botId);
    }

    @Override
    public Map<Long, Bot> getBots() {
        return bots;
    }

    @Override
    public Set<String> getBotTypes() {
        return this.botTypes.keySet();
    }

    @Override
    public FacebookClient getFacebookClient(Long botId){
        return facebookClients.get(botId);
    }

    @Override
    public Map<Long, FacebookClient> getFacebookClients() {
        return this.facebookClients;
    }

    @Override
    public String putBot(Long botId, String botType){
        BotImpl bot = injector.getInstance(botTypes.get(botType));
        bot.setBotId(botId);
        bots.put(botId, bot);
        return String.format("Added new bot with id %s", botId);
    }

    @Override
    public String putFacebookClient(Long botId, FacebookClient facebookClient){
        facebookClients.put(botId, facebookClient);
        return String.format("Added new facebookClient for bot %s", botId);
    }

    @Override
    public String registerBot(Class<? extends BotImpl> botClass, String botType) {
        botTypes.put(botType, botClass);
        return String.format("Added new BotType %s", botType);
    }

    @Override
    public String updateBot(Long botId, String botType, FacebookClient facebookClient) {

        bots.remove(botId);
        Bot bot = injector.getInstance(botTypes.get(botType));
        bots.put(botId, bot);

        facebookClients.remove(botId);
        facebookClients.put(botId, facebookClient);

        return String.format(
                "Updated Typen and Access Token for Bot %s. \n" +
                        "New type: %s \n", botId, botType);
    }
}
