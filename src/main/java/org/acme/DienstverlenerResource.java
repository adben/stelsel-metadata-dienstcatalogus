package org.acme;

import org.acme.model.Dienstverlener;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/dienstverlener")
public class DienstverlenerResource {

    private final Set<Dienstverlener> dvs = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public DienstverlenerResource() {
        dvs.add(new Dienstverlener("DV1", "Eerste Dienstverlener"));
        dvs.add(new Dienstverlener("DV2", "Tweede Dienstverlener"));
    }

    @GET
    public Set<Dienstverlener> list() {
        return dvs;
    }

    @POST
    public Set<Dienstverlener> add(Dienstverlener Dienstverlener) {
        dvs.add(Dienstverlener);
        return dvs;
    }

    @DELETE
    public Set<Dienstverlener> delete(Dienstverlener Dienstverlener) {
        dvs.removeIf(existingDienstverlener -> existingDienstverlener.name.contentEquals(Dienstverlener.name));
        return dvs;
    }
}
