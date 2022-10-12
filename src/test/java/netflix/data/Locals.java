package netflix.data;

public enum Locals {
    NL("nl"),
    NL_EN("nl-en");
    private final String value;
    Locals(String value) {
        this.value = value;
    }
    public String asValue() {
        return value;
    }

}
