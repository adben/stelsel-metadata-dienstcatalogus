package org.acme.model;

import java.time.LocalDateTime;
import java.util.List;

public class StelselMetadata {
    public String iss;
    public String classification;
    public String schemeVersion;
    public String src;
    public String archiveURI;
    public Long publicationSerial;
    public LocalDateTime publicationDate;
    public LocalDateTime nextUpdate;
    public LocalDateTime validUntil;

    public List<AuthenticatieDienst> ADs;
}
