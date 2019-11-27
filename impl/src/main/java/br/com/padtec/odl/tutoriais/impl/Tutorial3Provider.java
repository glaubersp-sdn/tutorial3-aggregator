/*
 * Copyright © 2018 Padtec and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package br.com.padtec.odl.tutoriais.impl;

import org.opendaylight.mdsal.binding.api.DataBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tutorial3Provider {

    private static final Logger LOG = LoggerFactory.getLogger(Tutorial3Provider.class);

    private final DataBroker dataBroker;

    public Tutorial3Provider(final DataBroker dataBroker) {
        this.dataBroker = dataBroker;
    }

    /**
     * Method called when the blueprint container is created.
     */
    public void init() {
        LOG.info("Tutorial3Provider Session Initiated");
    }

    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("Tutorial3Provider Closed");
    }
}