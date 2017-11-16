package no.difi.bcp.client.model;

import lombok.Builder;
import lombok.Getter;
import no.difi.vefa.peppol.common.model.ParticipantIdentifier;
import no.difi.vefa.peppol.common.model.ProcessIdentifier;

import java.util.List;

/**
 * @author erlend
 */
@Builder
@Getter
public class ParticipantLookup {

    private ParticipantIdentifier participantIdentifier;

    private List<ProcessIdentifier> processIdentifiers;

}
