package com.marketdata.flows

import co.paralleluniverse.fibers.Suspendable
import com.marketdata.contracts.DataSetContract
import com.marketdata.data.PricingParameter
import com.marketdata.schema.DataSetSchemaV1
import com.marketdata.states.*
import net.corda.core.contracts.*
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.node.services.vault.Builder.equal
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.node.services.vault.builder
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// *********
// * Flows *
// *********
@InitiatingFlow
@StartableByRPC
class DataSetIssue(val name: String,
                   val tandc: TermsAndConditionsState,
                   val pricing : PricingParameter) : FlowLogic<SignedTransaction>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call() : SignedTransaction{
        val txb = TransactionBuilder(notary = serviceHub.networkMapCache.notaryIdentities.first())
        val cmd = DataSetContract.Commands.Issue()

        val results = builder {
            val nameIdx = DataSetSchemaV1.PersistentDataSet::name.equal(name)

            val customCriteria1 = QueryCriteria.VaultCustomQueryCriteria(nameIdx)

            val criteria = QueryCriteria.VaultQueryCriteria()
                    .and(customCriteria1)

            serviceHub.vaultService.queryBy(DataSetState::class.java,criteria)
        }.states

        check(results.isEmpty())

        val state = DataSetState(
                name,
                ourIdentity,
                listOf(pricing),
                LinearPointer(tandc.linearId, TermsAndConditionsState::class.java))

        txb.withItems(
                StateAndContract(state, DataSetContract.ID ),
                Command(cmd, state.participants.map { it.owningKey }),
                tandc.termsAndConditions
                )

        txb.verify(serviceHub)

        val ourSignedTx = serviceHub.signInitialTransaction(txb)
        return subFlow(FinalityFlow(ourSignedTx, emptyList()));
    }
}
