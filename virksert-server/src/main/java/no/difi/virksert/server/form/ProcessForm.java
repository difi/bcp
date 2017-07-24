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

package no.difi.virksert.server.form;

import no.difi.virksert.server.domain.Process;

/**
 * @author erlend
 */
public class ProcessForm {

    private boolean exists;

    private String identifier;

    private String scheme;

    private String title;

    private Process.Type type = Process.Type.ONE_WAY;

    public ProcessForm() {
        exists = false;
    }

    public ProcessForm(Process process) {
        exists = true;

        setIdentifier(process.getIdentifier());
        setScheme(process.getScheme());
        setTitle(process.getTitle());
        setType(process.getType());
    }

    public boolean isExists() {
        return exists;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Process.Type getType() {
        return type;
    }

    public void setType(Process.Type type) {
        this.type = type;
    }

    public Process update(Process process) {
        process.setIdentifier(getIdentifier());
        process.setScheme(getScheme());
        process.setTitle(getTitle());
        process.setType(getType());

        return process;
    }
}
