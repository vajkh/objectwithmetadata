package eu.smartx.keler.objectmetadata.service.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface TranslationKey {
    String prefix();
}
