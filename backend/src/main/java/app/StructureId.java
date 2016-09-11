package app;

public class StructureId {
    private final String id;

    public StructureId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StructureId{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StructureId that = (StructureId) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
