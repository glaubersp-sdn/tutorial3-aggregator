/*
 * Copyright (c) 2016 Ericsson India Global Services Pvt Ltd. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package br.com.padtec.odl.tutoriais.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.opendaylight.genius.mdsalutil.ActionInfo;
import org.opendaylight.genius.mdsalutil.FlowEntity;
import org.opendaylight.genius.mdsalutil.FlowEntityBuilder;
import org.opendaylight.genius.mdsalutil.InstructionInfo;
import org.opendaylight.genius.mdsalutil.MatchInfo;
import org.opendaylight.genius.mdsalutil.actions.ActionOutput;
import org.opendaylight.genius.mdsalutil.instructions.InstructionApplyActions;
import org.opendaylight.genius.mdsalutil.interfaces.IMdsalApiManager;
import org.opendaylight.genius.mdsalutil.matches.MatchEthernetType;
import org.opendaylight.genius.mdsalutil.matches.MatchIpv4Destination;
import org.opendaylight.mdsal.binding.api.DataBroker;
import org.opendaylight.mdsal.binding.api.DataObjectModification;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.Uri;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tutorial3.rev191119.AccessList;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tutorial3.rev191119.access.list.AccessListEntry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tutorial3.rev191119.access.list.access.list.entry.actions.packet.handling.Permit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tutorial3AppDataStoreHandler {

    private static final Logger LOG = LoggerFactory.getLogger(Tutorial3AppDataStoreHandler.class);

    private DataBroker dataBroker;
    private IMdsalApiManager mdsalApiManager;

    public Tutorial3AppDataStoreHandler(DataBroker dataBroker, IMdsalApiManager mdsalApiManager) {
        this.dataBroker = dataBroker;
        this.mdsalApiManager = mdsalApiManager;
    }

    public void installFlow(DataObjectModification<AccessList> change) {
        LOG.info(" installflow with data {} ", change);

        AccessList accList = change.getDataAfter();

        List<AccessListEntry> entries = accList.getAccessListEntry();
        for (AccessListEntry entry : entries) {
            FlowData flowData = new FlowData(entry);
            populateMDsalUtilWay(flowData);
        }
    }

    private void populateMDsalUtilWay(FlowData flowData) {
        FlowEntity flowEntity = getFlowEntity(flowData, flowData.getNodeId());
        mdsalApiManager.installFlow(flowEntity);
        if (flowData.getPacketHandlingAction() instanceof Permit) {
            mdsalApiManager.installFlow(getDownLinkFlowEntity(flowData, flowData.getNodeId()));
        }
        LOG.info("SampleApp default flow is installed successfully in node {}.", flowData.getNodeId());
    }

    private String getFlowRef(final String dstIp, final String ruleName) {
        return new StringBuilder(256).append(Tutorial3AppConstants.FLOWID_PREFIX)
                .append(Tutorial3AppConstants.RULENAME_SEPARATOR)
                .append(ruleName).append(Tutorial3AppConstants.RULENAME_SEPARATOR)
                .append(Tutorial3AppConstants.FLOWID_SEPARATOR).append(dstIp).toString();
    }

    private String getNodeId(String nodeString) {
        String[] parts = nodeString
                .split(Tutorial3AppConstants.RULENAME_SEPARATOR);
        return parts[1];
    }

    private FlowEntity getFlowEntity(FlowData flowBuilder, String nodeId) {

        long outPortNum = 2L;
        List<MatchInfo> mkMatches = new ArrayList<MatchInfo>();
        mkMatches.add(new MatchEthernetType(0x0800L));
        if (flowBuilder.getDstPrefix() != 0) {
            mkMatches.add(new MatchIpv4Destination(flowBuilder.getDstIpAddress(),
                                                   Integer.toString(flowBuilder.getDstPrefix())));
        }
        List<InstructionInfo> mkInstructions = new ArrayList<InstructionInfo>();
        List<ActionInfo> listActionInfo = new ArrayList<ActionInfo>();
        FlowEntityBuilder flowEntity = new FlowEntityBuilder().setFlowId(getNodeId(nodeId));
        if (flowBuilder.getPacketHandlingAction() instanceof Permit) {
            listActionInfo.add(new ActionOutput(new Uri(Long.toString(outPortNum))));
            flowEntity.setPriority(5);
        } else {
            flowEntity.setPriority(4);
        }
        mkInstructions.add(new InstructionApplyActions(listActionInfo));
        String flowId = getFlowRef(flowBuilder.getDstIpAddress(), flowBuilder.getRuleName());
        BigInteger cookieValue = Tutorial3AppConstants.COOKIE_TUTORIAL2APP_BASE
                .add(new BigInteger("0120000", 16)).add(BigInteger.valueOf(10L));
        flowEntity.setCookie(cookieValue);
        flowEntity.setFlowId(flowId);
        flowEntity.setFlowName(Tutorial3AppConstants.DEFAULT_RULE_NAME);
        flowEntity.setHardTimeOut(flowBuilder.getHardTimeout());
        flowEntity.setIdleTimeOut(flowBuilder.getIdleTimeout());
        flowEntity.setInstructionInfoList(mkInstructions);
        flowEntity.setMatchInfoList(mkMatches);
        flowEntity.setSendFlowRemFlag(false);
        flowEntity.setStrictFlag(false);
        flowEntity.setTableId((short) 0);
        flowEntity.setDpnId(new BigInteger(getNodeId(nodeId)));
        return flowEntity.build();
    }

    private FlowEntity getDownLinkFlowEntity(FlowData flowBuilder, String nodeId) {

        long intPortNum = 1L;
        List<MatchInfo> mkMatches = new ArrayList<MatchInfo>();
        mkMatches.add(new MatchEthernetType(0x0800L));
        if (flowBuilder.getSrcPrefix() != 0) {
            mkMatches.add(new MatchIpv4Destination(new Ipv4Prefix(Integer.toString(flowBuilder.getSrcPrefix()))));
            //flowBuilder.getSrcIpAddress(),
        }
        List<InstructionInfo> mkInstructions = new ArrayList<>();
        List<ActionInfo> listActionInfo = new ArrayList<>();
        listActionInfo.add(new ActionOutput(new Uri(Long.toString(intPortNum))));
        FlowEntityBuilder flowEntity = new FlowEntityBuilder().setFlowId((getNodeId(nodeId)));
        flowEntity.setPriority(5);
        mkInstructions.add(new InstructionApplyActions(listActionInfo));
        String flowId = getFlowRef(flowBuilder.getSrcIpAddress(), "DOWNLINKPERMIT");
        BigInteger cookieValue = Tutorial3AppConstants.COOKIE_TUTORIAL2APP_BASE
                .add(new BigInteger("0230000", 16)).add(BigInteger.valueOf(20L));
        flowEntity.setCookie(cookieValue);
        flowEntity.setFlowId(flowId);
        flowEntity.setFlowName("DOWNLINKPERMITFLOW");
        flowEntity.setHardTimeOut(flowBuilder.getHardTimeout());
        flowEntity.setIdleTimeOut(flowBuilder.getIdleTimeout());
        flowEntity.setInstructionInfoList(mkInstructions);
        flowEntity.setMatchInfoList(mkMatches);
        flowEntity.setSendFlowRemFlag(false);
        flowEntity.setStrictFlag(false);
        flowEntity.setTableId((short) 0);
        flowEntity.setDpnId(new BigInteger(getNodeId(nodeId)));
        return flowEntity.build();
    }
}
