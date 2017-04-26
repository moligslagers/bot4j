package ai.nitro.bot4j.integration.deployment.domain;

/**
 * Created by Markus on 24.04.2017.
 */
public class SlackSpecPayload {

    String platformName = "Slack";

    String accessToken;
    String clientId;
    String clientSecret;
    String userName;

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SlackSpecPayload(String accessToken, String clientId, String clientSecret, String userName) {
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.userName = userName;
    }
}
