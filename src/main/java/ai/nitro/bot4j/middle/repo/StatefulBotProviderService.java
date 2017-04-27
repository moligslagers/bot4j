package ai.nitro.bot4j.middle.repo;

import ai.nitro.bot4j.bot.Bot;
import ai.nitro.bot4j.bot.impl.BotImpl;
import com.restfb.FacebookClient;

import java.util.Map;
import java.util.Set;

/**
 * Created by Markus on 26.04.2017.
 */
public interface StatefulBotProviderService {

    Bot getBot(Long botId);

    Map<Long, Bot> getBots();

    Set<String> getBotTypes();

    FacebookClient getFacebookClient(Long botId);

    Map<Long, FacebookClient> getFacebookClients();

    void putBot(Long botId, String botType);

    void putFacebookClient(Long botId, String accessToken);

    void registerBot(Class<? extends BotImpl> botClass, String identifier);
}
