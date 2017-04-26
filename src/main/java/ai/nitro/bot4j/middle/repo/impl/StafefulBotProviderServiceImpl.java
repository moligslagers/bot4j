package ai.nitro.bot4j.middle.repo.impl;

import ai.nitro.bot4j.bot.Bot;
import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.inject.Injector;

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

    protected Map<String, Class<? extends Bot>> botTypes = new HashMap<>();

    @Override
    public void putBot(Long botId, String botType){
        Bot bot = injector.getInstance(botTypes.get(botType));
        bots.put(botId, bot);
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
    public void registerBot(Class<? extends Bot> botClass, String botType) {
        botTypes.put(botType, botClass);
    }
}
