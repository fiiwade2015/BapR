package ro.bapr.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import ro.bapr.services.response.Result;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 06.12.2015.
 */
@Aspect
@Component
public class SeeAlsoAspect {

    @Around( value = "@annotation(seeAlso)" )
    private Object seeAlso(ProceedingJoinPoint call, SeeAlso seeAlso) throws Throwable {
        Object callResult = call.proceed();
        Result result = getResultItems(callResult);
        addSeeAlso(result, seeAlso);

        return callResult;
    }

    /**
     * Adds to the each item of controllers response an "seeAlso"
     * @param result
     * @param seeAlso
     */
    private void addSeeAlso(Result result, SeeAlso seeAlso) {
        List<String> seeAlsoUrls = Arrays.asList(seeAlso.value());

        Map<String, String> parameters = getSeeAlsoParameter(seeAlsoUrls);
        result.getItems()
                .stream()
                .forEach(item -> {
                    Objects.requireNonNull(item, "Map with mapped properties (name, id, type, etc) IS NULL, MF. WHY???");

                    List<String> formattedUrls = buildSeeAlsoUrls(item, parameters, seeAlsoUrls);
                    if (item.get("seeAlso") == null) {
                        item.put("seeAlso", formattedUrls);
                    } else {
                        ((List) item.get("seeAlso")).addAll(formattedUrls);
                    }
                });
    }

    private List<String> buildSeeAlsoUrls(Map<String, Object> item,
                                          Map<String, String> parameters,
                                          List<String> seeAlsoUrls) {
        List<String> resultUrls = new ArrayList<>();
        seeAlsoUrls.stream().forEach(url ->
                parameters.forEach((key, value) ->
                        resultUrls.add(url.replaceAll(Pattern.quote(key), item.get(value).toString()))));

        return resultUrls;
    }

    /**
     * Finds the parameters to be replaced and returns a map with the string to be replaced and
     * the key to be replaced with.
     *
     * E.g. If the url is /entities/{id}/test, the returned map is {({id}, id)}.
     * The key is the substring to be replaced in the url and the value is the parameter name
     *
     * @param seeAlsoUrls urls to be processed
     *
     * @return            a map with the string to be replaced and
     *                    the key to be replaced with.
     */
    private Map<String, String> getSeeAlsoParameter(Collection<String> seeAlsoUrls) {
        Map<String, String> params = new HashMap<>();
        Pattern pattern = Pattern.compile("/\\{\\w+\\}/");

        seeAlsoUrls.stream().forEach(url -> {
            Matcher matcher = pattern.matcher(url);
            while (matcher.find()) {
                String param = matcher.group();
                params.put(param.substring(1, param.length() - 1),
                        param.substring(2, param.length() - 2));
            }
        });

        return params;
    }

    private Result getResultItems(Object result) {
        Result finalResult = null;
        if(result instanceof ResponseEntity) {
            ResponseEntity responseEntity = (ResponseEntity)result;

            if(responseEntity.getStatusCode().equals(HttpStatus.OK)
                    &&responseEntity.getBody() instanceof Result) {
                finalResult = (Result)responseEntity.getBody();
            }
        }

        return finalResult;
    }
}
