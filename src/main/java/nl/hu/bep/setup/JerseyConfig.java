package nl.hu.bep.setup;

import nl.hu.bep.battlesnek.persistence.PersistenceManager;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;

import static nl.hu.bep.battlesnek.webservices.appearance.AppearanceResource.loadAppearanceAtStartup;

@ApplicationPath("restservices")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(RolesAllowedDynamicFeature.class);
        packages("nl.hu.bep.battlesnek.webservices");
    }

    static {
        PersistenceManager.init();
        loadAppearanceAtStartup();
    }
}
