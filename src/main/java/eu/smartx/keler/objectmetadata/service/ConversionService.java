package eu.smartx.keler.objectmetadata.service;

import eu.smartx.keler.objectmetadata.service.metadata.MetadataExtractor;
import eu.smartx.keler.objectmetadata.service.metadata.ObjectWithMetaData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@RequiredArgsConstructor
@Service
public class ConversionService {

    private final MetadataExtractor metadataExtractor;

    private final MetaDataService metaDataService;

    public SavedMetadata convert(Object o){
        var objectWithMetaData = metadataExtractor.extract(o);
        var id = metaDataService.save(objectWithMetaData);
        return new SavedMetadata(id, objectWithMetaData);
    }


    @Data
    public class SavedMetadata {
       private final BigInteger id;
       private final ObjectWithMetaData objectWithMetaData;
    }
}
