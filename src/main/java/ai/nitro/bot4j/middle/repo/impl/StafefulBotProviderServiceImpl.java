package ai.nitro.bot4j.middle.repo.impl;

import ai.nitro.bot4j.bot.Bot;
import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.inject.Injector;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

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
    public Bot getBot(Long botId){
        return bots.get(botId);
    }

    @Override
    public Map<Long, Bot> getBots() {
        return bots;
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
    public void putBot(Long botId, String botType){
        BotImpl bot = injector.getInstance(botTypes.get(botType));
        bot.setBotId(botId);
        bots.put(botId, bot);
    }

    @Override
    public void putFacebookClient(Long botId, String accessToken){
        DefaultFacebookClient defaultFacebookClient = new DefaultFacebookClient(accessToken,
                Version.VERSION_2_8);
        facebookClients.put(botId, defaultFacebookClient);
    }

    @Override
    public void registerBot(Class<? extends BotImpl> botClass, String botType) {
        botTypes.put(botType, botClass);
    }
}
