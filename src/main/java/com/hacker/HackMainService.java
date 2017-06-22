package com.hacker;

import com.hacker.config.HackConfiguration;
import com.hacker.filter.JpaContextRequestFilter;
import com.hacker.filter.JpaContextResponseFilter;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.activejpa.enhancer.ActiveJpaAgentLoader;
import org.activejpa.jpa.JPA;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.TimeZone;

public class HackMainService extends Application<HackConfiguration>
{
    public static void main(String[] args) throws Exception
    {
        new HackMainService().run(args);
    }

    @Override
    public void initialize(Bootstrap<HackConfiguration> bootstrap)
    {
        ActiveJpaAgentLoader.instance().loadAgent();

        GuiceBundle<HackConfiguration> guiceBundle = GuiceBundle
            .<HackConfiguration> newBuilder().addModule(new HackModule())
                .enableAutoConfig(getClass().getPackage().getName()).build();
        bootstrap.addBundle(guiceBundle);
        bootstrap.getObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        bootstrap.getObjectMapper().setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
    }

    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }

    @Override
    public void run(HackConfiguration configuration, Environment environment) throws Exception
    {
        configureCors(environment);

        JPA.instance.addPersistenceUnit("hack", configuration.getDb(), true);

        environment.jersey().getResourceConfig().getContainerRequestFilters().add(new JpaContextRequestFilter(configuration));
        environment.jersey().getResourceConfig().getContainerResponseFilters().add(new JpaContextResponseFilter(configuration));

    }
}
