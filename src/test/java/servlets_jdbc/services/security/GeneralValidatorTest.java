package servlets_jdbc.services.security;

import org.junit.jupiter.api.Test;
import servlets_jdbc.services.security.models.ErrorMapPair;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class GeneralValidatorTest extends GeneralValidator {

    ErrorMapPair<Boolean, Map<String, String>> errorMapPair;

    @Test
    void validateForEmptinessForEmpty() {
        Map<String, String[]> parameters = Map.ofEntries(new AbstractMap.SimpleEntry<>("Some", new String[]{""}));

        assumingThat((errorMapPair = super.validateForEmptiness(parameters)).hasErrors(),
                () -> assertEquals(errorMapPair.getMap().get("Some"), "Some should not be empty"));
    }

    @Test
    void validateForEmptinessForNotEmpty() {
        Map<String, String[]> parameters = Map.ofEntries(new AbstractMap.SimpleEntry<>("Some", new String[]{"Value"}));

        assertFalse((errorMapPair = super.validateForEmptiness(parameters)).hasErrors());
    }

    @Test
    void validateWithRightParameters() {
        Map<String, String[]> parameters = Map.ofEntries(
                new AbstractMap.SimpleEntry<>("Some", new String[]{"Value"}),
                new AbstractMap.SimpleEntry<>("Other", new String[]{"ValueValue"})
        );

        Object[] objects = new Object[]{"Some", 4, "Other", 8};

        assertFalse((errorMapPair = super.validate(parameters, objects)).hasErrors());
    }

    @Test
    void validateWithWrongParameters() {
        Map<String, String[]> parameters = Map.ofEntries(
                new AbstractMap.SimpleEntry<>("Some", new String[]{"Val"}),
                new AbstractMap.SimpleEntry<>("Other", new String[]{"ValueValue"})
        );

        Object[] objects = new Object[]{"Some", 4};

        assumingThat(!(errorMapPair = super.validate(parameters, objects)).hasErrors(),
                () -> assertEquals(errorMapPair.getMap().get("Some"),
                        "Some should be at least " + objects[1] + " characters long"));
    }
}
