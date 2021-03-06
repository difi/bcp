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

package no.difi.bcp.server.form;

import no.difi.bcp.server.domain.Participant;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class SepForm extends AbstractForm {

    @NotEmpty
    @Pattern(regexp = "[0-9a-z]+")
    private String identifier;

    @NotEmpty
    private String name;

    public SepForm() {
        super(false);
    }

    public SepForm(Participant participant) {
        super(true);

        setIdentifier(participant.getIdentifier());
        setName(participant.getName());
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Participant update(Participant participant) {
        participant.setIdentifier(getIdentifier());
        participant.setName(getName());

        return participant;
    }

}
