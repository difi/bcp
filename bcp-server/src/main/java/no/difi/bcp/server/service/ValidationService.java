/*
 * Copyright 2017 Norwegian Agency for Public Management and eGovernment (Difi)
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/community/eupl/og_page/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package no.difi.bcp.server.service;

import net.klakegg.pkix.ocsp.*;
import no.difi.bcp.server.domain.Certificate;
import no.difi.bcp.server.domain.CertificateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author erlend
 */
@Service
public class ValidationService {

    private static Logger logger = LoggerFactory.getLogger(ValidationService.class);

    private OcspMultiClient client = OcspMultiClient.builder()
            .build();

    @Autowired
    private CertificateRepository certificateRepository;

    @Scheduled(cron = "0 0 3 * * *")
    // @Scheduled(fixedDelay = 1000)
    public void run() {
        performValidation();
    }

    @Transactional
    public void performValidation() {
        logger.info("Validating registered certificates.");

        certificateRepository.findOcspEnabled()
                .collect(Collectors.toMap(Certificate::getIssuer, Arrays::asList))
                .forEach((issuer, certificates) -> {
                    try {
                        long timestamp = System.currentTimeMillis();

                        OcspResult result = client.verify(
                                certificates.get(0).getOcspUri(),
                                new CertificateIssuer(issuer.getNameHash(), issuer.getKeyHash()),
                                certificates.stream()
                                        .map(Certificate::getSerialNumber)
                                        .map(BigInteger::new)
                                        .collect(Collectors.toList())
                                        .toArray(new BigInteger[certificates.size()]));

                        List<Certificate> validCertificates = certificates.stream()
                                .filter(c -> result.get(new BigInteger(c.getSerialNumber())).getStatus() == CertificateStatus.GOOD)
                                .collect(Collectors.toList());
                        if (validCertificates.size() > 0)
                            certificateRepository.updateVerifiedValid(timestamp, validCertificates);

                        List<Certificate> invalidCertificates = certificates.stream()
                                .filter(c -> result.get(new BigInteger(c.getSerialNumber())).getStatus() != CertificateStatus.GOOD)
                                .collect(Collectors.toList());
                        if (invalidCertificates.size() > 0)
                            certificateRepository.updateVerifiedInvalid(timestamp, invalidCertificates);
                    } catch (OcspException e) {
                        logger.warn("Error while validating certificates.", e);
                    }
                });
    }
}
