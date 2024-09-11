package eu.smartx.keler.objectmetadata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ConversionRepository extends JpaRepository<ConversionEntity, BigInteger> {
}
