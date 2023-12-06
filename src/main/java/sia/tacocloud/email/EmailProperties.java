package sia.tacocloud.email;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@Component
@ConfigurationProperties(prefix="tacocloud.email")
public class EmailProperties {
    private String username;
    private String password;
    private String host;
    private String mailbox;
    private long pollRate = 30000;
    public String getImapUrl(){
        return String.format("imaps://%s:%s@%S/%S",
                this.username,this.password,this.host,this.mailbox);
    }
}
