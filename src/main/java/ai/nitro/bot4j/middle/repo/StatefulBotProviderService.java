package ai.nitro.bot4j.middle.repo;

import ai.nitro.bot4j.bot.Bot;
import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import com.restfb.FacebookClient;

import java.util.Map;
import java.util.Set;

/**
 * Created by Markus on 26.04.2017.
 */
public interface StatefulBotProviderService {

    String deleteBot(Long botId);

    Bot getBot(Long botId);

    Map<Long, Bot> getBots();

    Set<String> getBotTypes();

    FacebookClient getFacebookClient(Long botId);

    Map<Long, FacebookClient> getFacebookClients();

    String putBot(Long botId, String botType);

    String putFacebookClient(Long botId, FacebookClient facebookClient);

    String registerBot(Class<? extends BotImpl> botClass, String identifier);

    String updateBot(Long botId, String BotType, FacebookClient facebookClient);
}
