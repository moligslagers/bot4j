package ai.nitro.bot4j.integration.deployment.domain;

/**
 * Created by Markus on 24.04.2017.
 */
public class FacebookSpecPayload {

    String platformName = "Facebook";

    String accessToken;

    public FacebookSpecPayload(String accessToken) {
        this.accessToken = accessToken;
    }
}
