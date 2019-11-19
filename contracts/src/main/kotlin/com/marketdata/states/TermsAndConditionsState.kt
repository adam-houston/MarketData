package com.marketdata.states

import com.marketdata.contracts.DataSetContract
import com.marketdata.contracts.TermsAndConditionsContract
import com.marketdata.contracts.UsageContract
import com.marketdata.data.PricingParameter
import com.marketdata.schema.DataSetSchemaV1
import net.corda.core.contracts.*
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.node.services.AttachmentId
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import java.time.LocalDate

@BelongsToContract(TermsAndConditionsContract::class)
class TermsAndConditionsState(val name : String,
                              val issuer: Party,
                              val termsAndConditions: AttachmentId,
                              override val linearId: UniqueIdentifier = UniqueIdentifier()) : LinearState { //, QueryableState

    override val participants: List<Party> get() = listOf(issuer)

//    override fun generateMappedObject(schema: MappedSchema): PersistentState {
//        return when (schema) {
//            is DataSetSchemaV1 -> DataSetSchemaV1.PersistentDataSet(
//                    this.name,
//                    this.issuer.name.toString()
//            )
//            else -> throw IllegalArgumentException("Unrecognised schema $schema")
//        }
//    }
//
//    override fun toString() : String {
//        return  "$name/${issuer.name}"
//    }
//
//    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(DataSetSchemaV1)
}