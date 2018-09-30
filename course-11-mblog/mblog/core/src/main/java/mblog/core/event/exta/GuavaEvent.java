package mblog.core.event.exta;

public class GuavaEvent {

    private final int message;

    public GuavaEvent(int message) {
        this.message = message;
        System.out.println("event message:"+message);
    }

    public int getMessage() {
        return message;
    }

}
