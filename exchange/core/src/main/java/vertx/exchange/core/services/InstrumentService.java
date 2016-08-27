package vertx.exchange.core.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InstrumentService {

    public List<String> getInstruments(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> "instr" + i).collect(Collectors.toList());
    }

}
