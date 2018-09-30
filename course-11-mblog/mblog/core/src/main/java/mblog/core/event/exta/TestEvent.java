package mblog.core.event.exta;

import org.springframework.context.ApplicationEvent;

public class TestEvent extends ApplicationEvent {

    public boolean isImport;

    public TestEvent(Object source, boolean isImport) {
        super(source);
        this.isImport = isImport;
    }

    public boolean isImport() {
        return isImport;
    }

    public void setImport(boolean anImport) {
        isImport = anImport;
    }

    @Override
    public String toString() {
        return "TestEvent{" +
                "isImport=" + isImport +
                '}';
    }
}
