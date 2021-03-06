package com.marketdata

import net.corda.core.identity.CordaX500Name
import net.corda.testing.core.TestIdentity

val EMPTY_IDENTITY = TestIdentity(CordaX500Name(organisation = "Empty", locality = "TestLand", country = "US"))
val ALICE = TestIdentity(CordaX500Name(organisation = "Alice", locality = "TestLand", country = "US"))
val BOB = TestIdentity(CordaX500Name(organisation = "Bob", locality = "TestCity", country = "US"))
var CHARLIE = TestIdentity(CordaX500Name(organisation = "Charlie", locality = "TestVillage", country = "US"))
var DAN = TestIdentity(CordaX500Name(organisation = "Dan", locality = "TestHamlet", country = "US"))
val MINICORP = TestIdentity(CordaX500Name(organisation = "MiniCorp", locality = "MiniLand", country = "US"))
val MEGACORP = TestIdentity(CordaX500Name(organisation = "MegaCorp", locality = "MiniLand", country = "US"))
val DUMMY = TestIdentity(CordaX500Name(organisation = "Dummy", locality = "FakeLand", country = "US"))
