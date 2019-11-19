package com.marketdata.contracts

// TODO: Can be deleted
//
//import com.marketdata.ALICE
//import com.marketdata.BOB
//import com.marketdata.CHARLIE
//import com.marketdata.MEGACORP
//import com.marketdata.MINICORP
//import com.marketdata.states.PermissionState
//import net.corda.core.contracts.TypeOnlyCommandData
//import net.corda.testing.node.MockServices
//import net.corda.testing.node.ledger
//import org.junit.Test
//import java.io.File
//
//class PermissionContractIssueTests {
//    private val ledgerServices = MockServices()
//
//    class DummyCommand : TypeOnlyCommandData()
//// val attachmentFile = File("src/test/resources/prices.zip")
//
//    @Test
//    fun vanilla2Party() {
//
//        ledgerServices.ledger {
//            transaction {
//                output(PermissionContract.ID, PermissionState("data_set", ALICE.party, BOB.party))
//                command(listOf(ALICE.publicKey, BOB.publicKey), PermissionContract.Commands.Issue()) // Correct type.
//// attachment(attachment(attachmentFile.inputStream()))
//                this.verifies()
//            }
//            // explicit null
//            transaction {
//                output(PermissionContract.ID,  PermissionState("data_set", ALICE.party, BOB.party, null))
//                command(listOf(ALICE.publicKey, BOB.publicKey), PermissionContract.Commands.Issue()) // Correct type.
//// attachment(attachment(attachmentFile.inputStream()))
//                this.verifies()
//            }
//        }
//    }
//
//    @Test
//    fun vanilla3Party() {
//        ledgerServices.ledger {
//            transaction {
//                output(PermissionContract.ID, PermissionState("data_set", ALICE.party, BOB.party, CHARLIE.party))
//                command(listOf(ALICE.publicKey, BOB.publicKey, CHARLIE.publicKey), PermissionContract.Commands.Issue()) // Correct type.
//// attachment(attachment(attachmentFile.inputStream()))
//                this.verifies()
//            }
//            transaction {
//                output(PermissionContract.ID, PermissionState("data_set", ALICE.party, BOB.party, ALICE.party))
//                command(listOf(ALICE.publicKey, BOB.publicKey), PermissionContract.Commands.Issue()) // Correct type.
//// attachment(attachment(attachmentFile.inputStream()))
//                this.verifies()
//            }
//            transaction {
//                output(PermissionContract.ID, PermissionState("data_set", ALICE.party, BOB.party, BOB.party))
//                command(listOf(ALICE.publicKey, BOB.publicKey), PermissionContract.Commands.Issue()) // Correct type.
//// attachment(attachment(attachmentFile.inputStream()))
//                this.verifies()
//            }
//        }
//    }
//
//    @Test
//    fun mustIncludeIssueCommand() {
//        val perm = PermissionState("data_set", ALICE.party, BOB.party)
//        ledgerServices.ledger {
//            transaction {
//                output(PermissionContract.ID, perm)
//                command(listOf(ALICE.publicKey, BOB.publicKey), DummyCommand()) // Wrong type.
//// attachment(attachment(attachmentFile.inputStream()))
//                this.fails()
//            }
//            transaction {
//                output(PermissionContract.ID, perm)
//                command(listOf(ALICE.publicKey, BOB.publicKey), PermissionContract.Commands.Issue()) // Correct type.
//// attachment(attachment(attachmentFile.inputStream()))
//                this.verifies()
//            }
//        }
//    }
//
//    @Test
//    fun noInputsAllowed() {
//        val perm = PermissionState("data_set", ALICE.party, BOB.party)
//        ledgerServices.ledger {
//            transaction {
//                command(listOf(ALICE.publicKey, BOB.publicKey),PermissionContract.Commands.Issue())
//// attachment(attachment(attachmentFile.inputStream()))
//                input(PermissionContract.ID, perm)
//                output(PermissionContract.ID, perm)
//                this `fails with` "No inputs should be consumed when issuing Permission."
//            }
//        }
//    }
//
//    @Test
//    fun singleOutputAllowed() {
//        val perm = PermissionState("data_set", ALICE.party, BOB.party)
//        ledgerServices.ledger {
//            transaction {
//                command(listOf(ALICE.publicKey, BOB.publicKey),PermissionContract.Commands.Issue())
//// attachment(attachment(attachmentFile.inputStream()))
//                output(PermissionContract.ID, perm)
//                output(PermissionContract.ID, perm)
//                this `fails with` "Only one output state should be created when issuing Permission."
//            }
//        }
//    }
//
//    @Test
//    fun allPartiesMustSign1() {
//        val perm = PermissionState("data_set", ALICE.party, BOB.party)
//        ledgerServices.ledger {
//            transaction {
//                command(listOf(ALICE.publicKey),PermissionContract.Commands.Issue())
//// attachment(attachment(attachmentFile.inputStream()))
//                output(PermissionContract.ID, perm)
//                this `fails with` "All parties involved must sign permission issue transaction."
//            }
//        }
//    }
//
//    @Test
//    fun allPartiesMustSign2() {
//        val perm = PermissionState("data_set", ALICE.party, BOB.party)
//        ledgerServices.ledger {
//            transaction {
//                command(listOf(BOB.publicKey),PermissionContract.Commands.Issue())
//// attachment(attachment(attachmentFile.inputStream()))
//                output(PermissionContract.ID, perm)
//                this `fails with` "All parties involved must sign permission issue transaction."
//            }
//        }
//    }
//
//    @Test
//    fun allPartiesMustSign3() {
//        val perm = PermissionState("data_set", ALICE.party, BOB.party, CHARLIE.party)
//        ledgerServices.ledger {
//            transaction {
//                command(listOf(ALICE.publicKey, BOB.publicKey),PermissionContract.Commands.Issue())
//// attachment(attachment(attachmentFile.inputStream()))
//                output(PermissionContract.ID, perm)
//                this `fails with` "All parties involved must sign permission issue transaction."
//            }
//        }
//    }
//
//    @Test
//    fun allPartiesMustSign4() {
//        val perm = PermissionState("data_set", ALICE.party, BOB.party, ALICE.party)
//        ledgerServices.ledger {
//            transaction {
//                command(listOf(ALICE.publicKey, BOB.publicKey),PermissionContract.Commands.Issue())
//// attachment(attachment(attachmentFile.inputStream()))
//                output(PermissionContract.ID, perm)
//                this.verifies()
//            }
//        }
//    }
//
//    @Test
//    fun allPartiesMustSign5() {
//        val perm = PermissionState("data_set", ALICE.party, BOB.party, BOB.party)
//        ledgerServices.ledger {
//            transaction {
//                command(listOf(ALICE.publicKey, BOB.publicKey),PermissionContract.Commands.Issue())
//// attachment(attachment(attachmentFile.inputStream()))
//                output(PermissionContract.ID, perm)
//                this.verifies()
//            }
//        }
//    }
//
//    @Test
//    fun subscriberAndProviderCannotBeTheSame() {
//        val perm = PermissionState("data_set", ALICE.party, BOB.party)
//        val subIsProv = PermissionState("data_set", ALICE.party, ALICE.party)
//        ledgerServices.ledger {
//            transaction {
//                command(listOf(ALICE.publicKey, BOB.publicKey),PermissionContract.Commands.Issue())
//// attachment(attachment(attachmentFile.inputStream()))
//                output(PermissionContract.ID, subIsProv)
//                this `fails with` "The subscriber and provider cannot be the same."
//            }
//            transaction {
//                command(listOf(ALICE.publicKey, BOB.publicKey), PermissionContract.Commands.Issue())
//// attachment(attachment(attachmentFile.inputStream()))
//                output(PermissionContract.ID, perm)
//                this.verifies()
//            }
//        }
//    }
//}
