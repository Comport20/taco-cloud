package sia.tacocloud.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import sia.tacocloud.data.TacoRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class TacoCountInfoContributor implements InfoContributor {
    private final TacoRepository tacoRepository;
    public TacoCountInfoContributor(TacoRepository tacoRepository){
        this.tacoRepository = tacoRepository;
    }
    @Override
    public void contribute(Info.Builder builder){
        long tacoCount = tacoRepository.count().block();
        Map<String, Object> tacoMap = new HashMap<>();
        tacoMap.put("count", tacoCount);
        builder.withDetail("taco-stats",tacoMap);
    }
}
