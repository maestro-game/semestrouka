package servlets_jdbc.services.security;

import servlets_jdbc.services.security.models.ErrorMapPair;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneralValidator {
    protected ErrorMapPair<Boolean, Map<String, String>> validateForEmptiness(Map<String, String[]> parameters) {

        ErrorMapPair<Boolean, Map<String, String>> res = new ErrorMapPair<>();

//                Simplifying for single value forms (or queries)
        Supplier<Stream<Map.Entry<String, String>>> simplifiedParameters = () -> parameters.entrySet()
                .parallelStream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()[0]));

//                Filtering for empty entries
        Supplier<Stream<Map.Entry<String, String>>> filteredForEmptyParameters = () -> simplifiedParameters
                .get()
                .filter(entry -> entry.getValue().isBlank());

        if (filteredForEmptyParameters.get().findAny().isPresent()) {
            res.errors(Boolean.TRUE);
            res.setMap(filteredForEmptyParameters
                    .get()
                    .map(entry -> new AbstractMap.SimpleEntry<>(
                            entry.getKey(),
                            entry.getKey() + " should not be empty"))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        } else {
            res.errors(Boolean.FALSE);
            res.setMap(simplifiedParameters
                    .get()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        }

        return res;
    }


    /**
     * Validate request parameters map by given arguments
     *
     * @param args -    Arguments must be pairs of [parameterName, minLength]
     */
    public ErrorMapPair<Boolean, Map<String, String>> validate(Map<String, String[]> parameters, Object... args) throws IllegalArgumentException {

        ErrorMapPair<Boolean, Map<String, String>> res = validateForEmptiness(parameters);

        if (args != null) {
            if (res.hasErrors()) {
                return res;
            } else {
                Map<String, String> resultMap = res.getMap();

                if (args.length % 2 != 0) {
                    throw new IllegalArgumentException("Arguments must be pairs of [parameterName, minLength]");
                }

                for (int i = 0; i < args.length; i += 2) {
                    String parameterName = (String) args[i];

                    if (resultMap.containsKey(parameterName)
                            && resultMap.get(parameterName).length() < (int) args[i + 1]) {

                        if (!res.hasErrors()) {
                            res.errors(Boolean.TRUE);

                            resultMap.clear();
                        }
                        resultMap.put(parameterName,
                                parameterName + " should be at least " + args[i + 1] + " characters long");
                    }
                }

                res.setMap(resultMap);
            }
        }
        return res;
    }
}
