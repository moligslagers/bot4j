package ai.nitro.bot4j.middle.repo;

import ai.nitro.bot4j.bot.Bot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Markus on 26.04.2017.
 */
public interface StatefulBotProviderService {

    Bot getBot(Long botId);

    Map<Long, Bot> getBots();

    void putBot(Long botId, String botType);

    void registerBot(Class<? extends Bot> botClass, String identifier);
}
