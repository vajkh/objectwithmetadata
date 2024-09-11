package eu.smartx.keler.objectmetadata.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.smartx.keler.objectmetadata.service.metadata.ObjectWithMetaData;

public class JsonUtil {
        public static String serializeDtoToJson(ObjectWithMetaData o) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(o);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
}
