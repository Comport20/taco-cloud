package sia.tacocloud.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class OrderSubmitMessageHandler
        implements GenericHandler<EmailOrder> {
    private final RestTemplate rest;
    private final ApiProperties apiProperties;
    @Autowired
    public OrderSubmitMessageHandler(RestTemplate rest, ApiProperties apiProperties){
        this.rest = rest;
        this.apiProperties = apiProperties;
    }
    @Override
    public Object handle(EmailOrder payload, MessageHeaders headers) {
        rest.postForObject(apiProperties.getUrl(), payload, String.class);
        return null;
    }
}
