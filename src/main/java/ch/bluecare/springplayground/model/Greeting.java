package ch.bluecare.springplayground.model;

public final class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static Greeting withId(long id) {
        return new Greeting(id, null);
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
