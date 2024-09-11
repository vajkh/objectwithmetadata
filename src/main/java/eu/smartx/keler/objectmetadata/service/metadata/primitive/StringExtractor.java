package eu.smartx.keler.objectmetadata.service.metadata.primitive;

public class StringExtractor implements PrimitiveValueExtractor {
    @Override
    public boolean supports(Object o) {
        return o !=null && o instanceof String;
    }

    @Override
    public String extract(Object o) {
        if (o == null) {
            return null;
        }
        return o.toString();
    }
}
