package servlets_jdbc.models.reviews;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Mark {
    M_ONE(-1), ZERO(0), ONE(1);

    private final Integer value;

    private final boolean _default;

    Mark(Integer value) {
        this.value = value;
        _default = value.equals(0);
    }

    @JsonValue
    public Integer toInt() {
        return value;
    }

    private static final Map<Integer, Mark> reverseLookup =
            Arrays.stream(Mark.values()).collect(Collectors.toMap(Mark::toInt, Function.identity()));

    public static Mark from(final Integer value) {
//        FIXME: save null to database or save certain specific value
        return reverseLookup.getOrDefault(value, null);
    }

    public boolean isDefault() {
        return _default;
    }
}
