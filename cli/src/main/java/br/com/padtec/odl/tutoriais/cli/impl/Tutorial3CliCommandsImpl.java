/*
 * Copyright © 2018 Padtec and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package br.com.padtec.odl.tutoriais.cli.impl;

import br.com.padtec.odl.tutoriais.cli.api.Tutorial3CliCommands;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tutorial3CliCommandsImpl implements Tutorial3CliCommands {

    private static final Logger LOG = LoggerFactory.getLogger(Tutorial3CliCommandsImpl.class);
    private final DataBroker dataBroker;

    public Tutorial3CliCommandsImpl(final DataBroker db) {
        this.dataBroker = db;
        LOG.info("Tutorial3CliCommandImpl initialized");
    }

    @Override
    public Object testCommand(Object testArgument) {
        return "This is a test implementation of test-command";
    }
}
