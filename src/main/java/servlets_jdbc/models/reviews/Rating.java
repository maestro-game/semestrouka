package servlets_jdbc.models.reviews;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Rating {
    R0(0), R1(1), R2(2), R3(3), R4(4), R5(5);

    private final Integer value;
    private final boolean _default;

    Rating(Integer value) {
//        this._default = value.equals(0) || Math.abs(value.compareTo(1) + value.compareTo(5)) == 0;
        this._default = !(value >= 1 && value <= 5);
        this.value = _default ? 0 : value;
    }

    @JsonValue
    public Integer toInt() {
        return value;
    }

    private static final Map<Integer, Rating> reverseLookup =
            Arrays.stream(Rating.values()).collect(Collectors.toMap(Rating::toInt, Function.identity()));

    public static Rating from(final Integer value) {
        return reverseLookup.getOrDefault(value, null);
    }

    public boolean isDefault() {
        return _default;
    }
}
