public class Client {
    private final long id;

    public Client(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String toString() {
        return "Client id: " + this.getId();
    }

    @Override
    public boolean equals(Object c) {
        if (c == this) {
            return true;
        }
        if (!(c instanceof final Client other)) {
            return false;
        }
        return id == (other.getId());
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }


}
