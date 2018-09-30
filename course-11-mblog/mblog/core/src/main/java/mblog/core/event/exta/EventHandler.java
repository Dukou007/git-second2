package mblog.core.event.exta;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {

    @EventListener(condition="#testEvent.isImport")
    public void TestEventTest(TestEvent testEvent) {
        System.out.println("==============TestEvent==============" + testEvent.toString());
    }



}
