package input;

public final class FiltersInput {
    private SortInput sort;
    private ContainsInput contains;

    public FiltersInput() { }

    public SortInput getSort() {
        return sort;
    }

    public ContainsInput getContains() {
        return contains;
    }
}
