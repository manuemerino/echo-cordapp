package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.FlowSession;
import net.corda.core.flows.InitiatedBy;
import net.corda.core.utilities.UntrustworthyData;

// ******************
// * EchoResponder flow *
// ******************
@InitiatedBy(EchoInitiator.class)
public class EchoResponder extends FlowLogic<Void> {
    private FlowSession counterpartySession;

    public EchoResponder(FlowSession counterpartySession) {
        this.counterpartySession = counterpartySession;
    }

    @Suspendable
    @Override
    public Void call() throws FlowException {
        // EchoResponder flow logic goes here.
        UntrustworthyData<String> rawReceivedData = counterpartySession.receive(String.class);
        String receivedData = rawReceivedData.unwrap(data -> {return data;});
        counterpartySession.send(receivedData);
        System.out.println(receivedData);
        return null;
    }
}
