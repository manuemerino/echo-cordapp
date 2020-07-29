package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.node.ServiceHub;
import net.corda.core.node.services.IdentityService;
import net.corda.core.utilities.ProgressTracker;
import net.corda.core.utilities.UntrustworthyData;

// ******************
// * EchoInitiator flow *
// ******************
@InitiatingFlow
@StartableByRPC
public class EchoInitiator extends FlowLogic<Void> {
    private final ProgressTracker progressTracker = new ProgressTracker();
    private final String message;
    private final String counterparty;

    @Override
    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    public EchoInitiator (String message, String counterparty) {
        this.message = message;
        this.counterparty = counterparty;
    }
    @Suspendable
    @Override
    public Void call() throws FlowException {
        // EchoInitiator flow logic goes here.
        Party otherParty = getServiceHub().getIdentityService().partiesFromName(counterparty, true).iterator().next();
        FlowSession otherPartySession = initiateFlow(otherParty);
        UntrustworthyData<String> rawReceivedData = otherPartySession.sendAndReceive(String.class, message);
        String receivedData = rawReceivedData.unwrap(data -> {return data;});
        System.out.println(receivedData);
        return null;
    }
}
