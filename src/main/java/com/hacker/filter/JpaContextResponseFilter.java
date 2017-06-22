package com.hacker.filter;

import com.hacker.config.HackConfiguration;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import org.activejpa.jpa.JPA;
import org.activejpa.jpa.JPAContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaContextResponseFilter implements ContainerResponseFilter {
    private static final Logger logger = LoggerFactory.getLogger(JpaContextRequestFilter.class);
    HackConfiguration configuration;
    private ThreadLocal<Boolean> contextCreated = new ThreadLocal<Boolean>();

    public JpaContextResponseFilter(HackConfiguration configuration) {
        this.configuration = configuration;
    }

    protected JPAContext getContext() {
        if (JPA.instance.getDefaultConfig() != null)
            return JPA.instance.getDefaultConfig().getContext(false);
        return null;
    }


    @Override
    public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse) {
        logger.info(" Destroy filter called");
        JPAContext context = getContext();
        if (context != null) {
            if (context.isTxnOpen()) {
                context.closeTxn(true);
            }
            if (context.getEntityManager() != null)
                context.close();
            logger.info("Context Destroyed");
        }

        return containerResponse;
    }
}
