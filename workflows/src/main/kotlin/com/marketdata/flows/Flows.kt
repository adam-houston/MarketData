package com.marketdata.flows

import co.paralleluniverse.fibers.Suspendable
import com.marketdata.contracts.PermissionContract
import com.marketdata.states.PermissionState
import net.corda.core.contracts.Command
import net.corda.core.contracts.ReferencedStateAndRef
import net.corda.core.contracts.Requirements.using
import net.corda.core.contracts.StateAndContract
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.transactions.ReferenceStateRef
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// *********
// * Flows *
// *********
@InitiatingFlow
@StartableByRPC
class PermissionInitiator(val state: PermissionState) : FlowLogic<SignedTransaction>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call() : SignedTransaction{
        val txb = TransactionBuilder(notary = serviceHub.networkMapCache.notaryIdentities.first())
        val cmd = PermissionContract.Commands.Issue()

        txb.withItems(
                StateAndContract(state, PermissionContract.ID ),
                Command(cmd, state.participants.map { it.owningKey }))


        txb.verify(serviceHub)
        val partialTx = serviceHub.signInitialTransaction(txb)

        val sessions = mutableListOf<FlowSession>()
        state.participants.filter { it != ourIdentity }.forEach { p -> sessions.add(initiateFlow(p)) }

        val signedTx = subFlow(CollectSignaturesFlow(partialTx, sessions))

        return subFlow(FinalityFlow(signedTx, sessions));
    }
}

@InitiatedBy(PermissionInitiator::class)
class PermissionResponder(val counterpartySession: FlowSession) : FlowLogic<Unit>() {
    @Suspendable
    override fun call() {
        val signedTransactionFlow = object : SignTransactionFlow(counterpartySession) {
            override fun checkTransaction(stx: SignedTransaction) = requireThat {
                val output = stx.tx.outputs.single().data
                "This must be a permission transaction" using (output is PermissionState)
            }
        }
        val expectedTxId = subFlow(signedTransactionFlow).id

        subFlow(ReceiveFinalityFlow(counterpartySession, expectedTxId))
    }
}
