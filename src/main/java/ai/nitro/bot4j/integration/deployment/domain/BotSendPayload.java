package ai.nitro.bot4j.integration.deployment.domain;

/**
 * Created by Markus on 24.04.2017.
 */
public class BotSendPayload {
    private Long id;
    private String name;
    private String botType;
    private String deploymentDestination;

    private FacebookSpecPayload facebookSpec;
    private SlackSpecPayload slackSpecPayload;
    private TelegramSpecPayload telegramSpecPayload;

    public Long getId() {
        return id;
    }

    public String getBotType() {
        return botType;
    }

    public FacebookSpecPayload getFacebookSpec() {
        return facebookSpec;
    }

    public SlackSpecPayload getSlackSpecPayload() {
        return slackSpecPayload;
    }

    public TelegramSpecPayload getTelegramSpecPayload() {
        return telegramSpecPayload;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BotSendPayload(Long id, String name, String botType, String deploymentDestination, FacebookSpecPayload facebookSpec, SlackSpecPayload slackSpecPayload, TelegramSpecPayload telegramSpecPayload) {
        this.id = id;
        this.name = name;
        this.botType = botType;
        this.deploymentDestination = deploymentDestination;
        this.facebookSpec = facebookSpec;
        this.slackSpecPayload = slackSpecPayload;
        this.telegramSpecPayload = telegramSpecPayload;
    }
}
