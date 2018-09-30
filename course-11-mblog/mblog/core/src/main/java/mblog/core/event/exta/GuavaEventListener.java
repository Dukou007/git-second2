package mblog.core.event.exta;

import com.google.common.eventbus.Subscribe;

public class GuavaEventListener {
    public int lastMessage = 0;

    @Subscribe
    public void listen(GuavaEvent event) {

        lastMessage = event.getMessage();
        System.out.println("guava--------Message:"+lastMessage);
    }

    public int getLastMessage() {      
        return lastMessage;
    }

}