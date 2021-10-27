package ru.psv.mj.www.pl.jsolve;

public class TextVariable implements Variable {

    private final String key;
    private final String value;

    public TextVariable(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
