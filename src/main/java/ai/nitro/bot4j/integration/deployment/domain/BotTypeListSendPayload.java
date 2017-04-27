package ai.nitro.bot4j.integration.deployment.domain;

import java.util.List;
import java.util.Set;

/**
 * Created by Markus on 27.04.2017.
 */
public class BotTypeListSendPayload {
    Set<String> botTypes;

    public void setBotTypes(Set<String> botTypes) {
        this.botTypes = botTypes;
    }
}
