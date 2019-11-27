/*
 * Copyright (c) 2016 Ericsson India Global Services Pvt Ltd. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package br.com.padtec.odl.tutoriais.impl;

import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.Table;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;

import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.NodeId;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Node;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.NodeBuilder;

import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Tutorial3AppUtils {

    private static final Logger LOG = LoggerFactory.getLogger(Tutorial3AppUtils.class);

    private Tutorial3AppUtils(){

    }

    public static NodeBuilder getNodeBuilder(final String nodeId) {
        NodeBuilder nodeBuilder = new NodeBuilder();
        nodeBuilder.setNodeId(new NodeId(nodeId));
        return nodeBuilder;
    }

    public static InstanceIdentifier<Flow> createFlowIdentifier(
            FlowBuilder flowBuilder, NodeBuilder nodeBuilder) {
//        InstanceIdentifier<Flow> flowIdentifier = InstanceIdentifier
//                .builder(Nodes.class).child(Node.class, nodeBuilder.getId())
//                .augmentation(FlowCapableNode.class)
//                .child(Table.class, new TableKey(flowBuilder.getTableId()))
//                .child(Flow.class, flowBuilder.getId()).build();
//        return flowIdentifier;
        return null;
    }

    public static InstanceIdentifier<Node> createNodeIdentifier(
            NodeBuilder nodeBuilder) {
//        InstanceIdentifier<Node> nodeIdentifier = InstanceIdentifier
//                .builder(Nodes.class).child(Node.class, nodeBuilder.getId())
//                .toInstance();
//        return nodeIdentifier;
        return null;
    }

    public static InstanceIdentifier<Table> createTableIdentifier(FlowBuilder flowBuilder,
                                                                  NodeBuilder nodeBuilder) {
//        InstanceIdentifier<Table> tableInstanceId = InstanceIdentifier
//                .builder(Nodes.class).child(Node.class, nodeBuilder.getId())
//                .augmentation(FlowCapableNode.class)
//                .child(Table.class, new TableKey(flowBuilder.getTableId())).build();
//        return tableInstanceId;
        return null;
    }

}
