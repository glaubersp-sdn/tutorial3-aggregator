/*
 * Copyright (c) 2016 Ericsson India Global Services Pvt Ltd. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package br.com.padtec.odl.tutoriais.impl;

import java.util.Collection;
import java.util.List;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.opendaylight.mdsal.binding.api.ClusteredDataTreeChangeListener;
import org.opendaylight.mdsal.binding.api.DataBroker;
import org.opendaylight.mdsal.binding.api.DataObjectModification;
import org.opendaylight.mdsal.binding.api.DataTreeIdentifier;
import org.opendaylight.mdsal.binding.api.DataTreeModification;
import org.opendaylight.mdsal.common.api.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tutorial3.rev191119.AccessList;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tutorial3.rev191119.access.list.AccessListEntry;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Tutorial3Listener implements ClusteredDataTreeChangeListener<AccessList> {
    private static final Logger LOG = LoggerFactory.getLogger(Tutorial3Listener.class);

    private final DataBroker dataBroker;

    public Tutorial3Listener(final DataBroker dataBroker) {
        this.dataBroker = dataBroker;
        registerListener();
    }

    /**
     * register listener to get AccessListEntry add/removed event.
     */
    private void registerListener() {
        final DataTreeIdentifier<AccessList> treeId =
                DataTreeIdentifier.create(LogicalDatastoreType.CONFIGURATION, getWildCardPath());
        dataBroker.registerDataTreeChangeListener(treeId, Tutorial3Listener.this);
        LOG.info("Tutorial3 App listener registration success");
    }


    protected InstanceIdentifier<AccessList> getWildCardPath() {
        return InstanceIdentifier.create(AccessList.class);
    }


    protected Tutorial3Listener getDataTreeChangeListener() {
        return this;
    }


    private void printData(DataObjectModification<AccessList> mod) {
        // TODO -- PreCheck !NULL
        AccessList accList = mod.getDataAfter();

        List<AccessListEntry> entries = accList.getAccessListEntry();
        LOG.info(" Printing the AccessList Entries : ");

        for (AccessListEntry entry : entries) {
            // AccessListEntry entry = entries.getAccessListEntry() ;
            @Nullable String name = " Rule Name : " + entry.getRuleName();
            LOG.info(name);
            @Nullable String nodeid = " NodeList :  " + entry.getNodeId();
            LOG.info(nodeid);
            @Nullable String actions = " Actions : " + entry.getActions();
            LOG.info(actions);
            @Nullable String matches = " Matches " + entry.getMatches();
            LOG.info(matches);
            @Nullable String alist = " AccessListEntry : " + entry.toString();
            LOG.info(alist);
        }

    }

    @Override
    public void onDataTreeChanged(@NonNull Collection<DataTreeModification<AccessList>> changes) {
        LOG.info("Tutorial3 App listener onDataTreeChanged {} ", changes);
        for (DataTreeModification<AccessList> change : changes) {
            DataObjectModification<AccessList> mod = change.getRootNode();
            switch (mod.getModificationType()) {
                case DELETE:
                    LOG.info(" Delete after data {} ", mod.getDataAfter());
                    break;
                case SUBTREE_MODIFIED:
                    break;
                case WRITE:
                    if (mod.getDataBefore() == null) {
                        LOG.info(" Write after data {} ", mod.getDataAfter());
                        printData(mod);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unhandled node modification type " + mod.getModificationType());
            }
        }
    }

}
