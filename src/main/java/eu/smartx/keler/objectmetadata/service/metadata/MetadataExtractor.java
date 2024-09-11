package eu.smartx.keler.objectmetadata.service.metadata;

import eu.smartx.keler.objectmetadata.service.metadata.primitive.PrimitiveTypeClassifier;
import eu.smartx.keler.objectmetadata.service.metadata.primitive.PrimitiveValueExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class MetadataExtractor {

    private final PrimitiveTypeClassifier primitiveTypeClassifier;
    private final Set<PrimitiveValueExtractor> primitiveValueExtractors;

    public ObjectWithMetaData extract(Object o) {
        if (o == null){
            return null;
        }
        return extract0(o, "root", o.getClass(), null);
    }

    private ObjectWithMetaData extract0(Object o, String fieldName, Class fieldClass, TranslationKey translationKey){
        if (o == null) {
            return mapNull(fieldName, fieldClass);
        } if (primitiveTypeClassifier.isPrimitiveType(o)){
            return mapPrimitive(fieldName, fieldClass, translationKey, o);
        } else {
            var nonStaticFields = Arrays.stream(fieldClass.getDeclaredFields()) //excludes inherited fields
                    .filter(field -> !java.lang.reflect.Modifier.isStatic(field.getModifiers()))
                    .collect(Collectors.toSet());

            Set<ObjectWithMetaData> children = new HashSet<>();
            for (Field field : nonStaticFields){
                System.out.println(field);
                children.add(extract0(fieldValue(field, o), field.getName(), field.getType(), translationKeyAnnotation(field)));
            }
            return mapComposite(fieldName, fieldClass, translationKey, o, children);
        }
    }

    private TranslationKey translationKeyAnnotation(Field field) {
        return field.isAnnotationPresent(TranslationKey.class) ? field.getAnnotation(TranslationKey.class) : null;
    }

    private ObjectWithMetaData mapNull(String fieldName, Class fieldClass) {
        return new ObjectWithMetaData(fieldName, fieldClass.getName(), null, null, null);
    }

    private ObjectWithMetaData mapComposite(String fieldName, Class fieldClass, TranslationKey translationKey, Object o, Set<ObjectWithMetaData>  children) {
        return new ObjectWithMetaData(fieldName, fieldClass.getName(), null, null,
                children.stream().collect(Collectors.toMap(ObjectWithMetaData::getPropertyName, Function.identity())));
    }

    private ObjectWithMetaData mapPrimitive(String fieldName, Class fieldClass, TranslationKey translationKey, Object o) {
        return new ObjectWithMetaData(fieldName, fieldClass.getName(), translationKey(translationKey, primitiveValue(o)), primitiveValue(o), null);
    }

    private String primitiveValue(Object o) {
        var valueExtractor = primitiveValueExtractors.stream()
                .filter(extractor -> extractor.supports(o))
                .findFirst();
        //FIXME nincs extractor
        return valueExtractor
                .map(ve -> ve.extract(o))
                .orElse("");
    }

    private String translationKey(TranslationKey translationKey, String postfix) {
        return translationKey != null ? translationKey.prefix() + ":" + postfix: null;
    }


    private Object fieldValue(Field field, Object o) {
        field.setAccessible(true);
        try {
            return o != null ? field.get(o) : null; //FIXME ide el se kene jutni
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
