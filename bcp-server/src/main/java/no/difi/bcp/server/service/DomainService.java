/*
 *  Copyright 2017 Norwegian Agency for Public Management and eGovernment (Difi)
 *
 *  Licensed under the EUPL, Version 1.1 or – as soon they
 *  will be approved by the European Commission - subsequent
 *  versions of the EUPL (the "Licence");
 *
 *  You may not use this work except in compliance with the Licence.
 *
 *  You may obtain a copy of the Licence at:
 *
 *  https://joinup.ec.europa.eu/community/eupl/og_page/eupl
 *
 *  Unless required by applicable law or agreed to in
 *  writing, software distributed under the Licence is
 *  distributed on an "AS IS" basis,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied.
 *  See the Licence for the specific language governing
 *  permissions and limitations under the Licence.
 */

package no.difi.bcp.server.service;

import no.difi.bcp.server.domain.Domain;
import no.difi.bcp.server.domain.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author erlend
 */
@Service
public class DomainService {

    @Autowired
    private DomainRepository domainRepository;

    @Transactional(readOnly = true)
    public List<Domain> findAll() {
        return domainRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Domain get(String identifier) {
        return domainRepository.findByIdentifier(identifier);
    }

    @Transactional
    public Domain save(Domain domain) {
        return domainRepository.save(domain);
    }

}
