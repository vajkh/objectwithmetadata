package eu.smartx.keler.objectmetadata.service;

import eu.smartx.keler.objectmetadata.repository.ConversionEntity;
import eu.smartx.keler.objectmetadata.repository.ConversionRepository;
import eu.smartx.keler.objectmetadata.service.metadata.ObjectWithMetaData;
import eu.smartx.keler.objectmetadata.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MetaDataService {

    private final ConversionRepository conversionRepository;

    public BigInteger save(ObjectWithMetaData o){
        var conversionEntity = new ConversionEntity(null, LocalDateTime.now(), o.getClassName(), JsonUtil.serializeDtoToJson(o));
        return conversionRepository.save(conversionEntity).getId();
    }
}
