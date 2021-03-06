/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.osgi.service;

import static org.jboss.as.osgi.OSGiLogger.LOGGER;
import static org.jboss.osgi.framework.IntegrationServices.PERSISTENT_BUNDLES;

import java.util.Collections;
import java.util.List;

import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.osgi.deployment.deployer.Deployment;
import org.jboss.osgi.framework.BootstrapBundlesInstall;

/**
 * A service that provides persistent bundles on framework startup.
 *
 * @author thomas.diesler@jboss.com
 * @since 12-Apr-2012
 */
public class PersistentBundlesIntegration extends BootstrapBundlesInstall<Void> {

    PersistentBundlesIntegration() {
        super(PERSISTENT_BUNDLES);
    }

    @Override
    protected void addServiceDependencies(ServiceBuilder<Void> builder) {
        builder.addDependencies(InitialDeploymentTracker.INITIAL_DEPLOYMENTS_COMPLETE);
        builder.addDependencies(ModuleRegistrationTracker.MODULE_REGISTRATION_COMPLETE);
    }

    @Override
    public void start(StartContext context) throws StartException {
        ServiceController<?> serviceController = context.getController();
        LOGGER.tracef("Starting: %s in mode %s", serviceController.getName(), serviceController.getMode());

        // This actually does not install any bundle deployments
        // At server startup the persistet bundles are deployed like any other persistet deployment
        List<Deployment> deployments = Collections.emptyList();
        installBootstrapBundles(context.getChildTarget(), deployments);
    }
}
