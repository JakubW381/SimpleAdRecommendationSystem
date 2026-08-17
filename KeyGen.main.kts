#!/usr/bin/kotlin

import java.security.KeyPairGenerator
import java.util.Base64

val gen = KeyPairGenerator.getInstance("RSA")
gen.initialize(2048)
val pair = gen.generateKeyPair()

val pub = Base64.getEncoder().encodeToString(pair.public.encoded)
val priv = Base64.getEncoder().encodeToString(pair.private.encoded)

println("\nKeys for the ars-secret.env file:\n")
println("ARS_RSA_PUBLIC_KEY=$pub")
println("ARS_RSA_PRIVATE_KEY=$priv")
