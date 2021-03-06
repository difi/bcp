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

package no.difi.bcp.server.domain;

import no.difi.bcp.api.Role;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author erlend
 */
@Entity
@Table(
        indexes = {
                @Index(columnList = "application_id,process_id")
        },
        uniqueConstraints = @UniqueConstraint(columnNames = {"application_id", "process_id", "role"})
)
public class Registration implements Serializable {

    private static final long serialVersionUID = -326289302326969043L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Process process;

    @ManyToOne
    private Application application;

    @Enumerated(EnumType.STRING)
    private Role role = Role.REQUEST;

    public Registration() {
    }

    public Registration(Application application, ProcessSet processSet) {
        this.application = application;
        this.process = processSet.getProcess();
        this.role = processSet.getRole();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ProcessSet toProcessSet() {
        return new ProcessSet(process, role);
    }
}
