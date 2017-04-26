package ai.nitro.bot4j.middle.repo;

import ai.nitro.bot4j.bot.Bot;
import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.integration.deployment.domain.FacebookSpecPayload;
import com.restfb.FacebookClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Markus on 26.04.2017.
 */
public interface StatefulBotProviderService {

    Bot getBot(Long botId);

    Map<Long, Bot> getBots();

    FacebookClient getFacebookClient(Long botId);

    Map<Long, FacebookClient> getFacebookClients();

    void putBot(Long botId, String botType);

    void putFacebookClient(Long botId, String accessToken);

    void registerBot(Class<? extends BotImpl> botClass, String identifier);
}
