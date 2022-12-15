package com.drinkscabinet.aoc2020

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class PassportTest {

    @Test
    fun fieldContent() {
        assertTrue(Passport.contentValid("byr", "2002"))
        assertFalse(Passport.contentValid("byr", "2003"))

        assertTrue(Passport.contentValid("hgt", "60in"))
        assertTrue(Passport.contentValid("hgt", "190cm"))
        assertFalse(Passport.contentValid("hgt", "190in"))
        assertFalse(Passport.contentValid("hgt", "190"))

        assertTrue(Passport.contentValid("hcl", "#123abc"))
        assertFalse(Passport.contentValid("hcl", "#123abz"))
        assertFalse(Passport.contentValid("hcl", "123abc"))

        assertTrue(Passport.contentValid("ecl", "brn"))
        assertFalse(Passport.contentValid("ecl", "wat"))

        assertTrue(Passport.contentValid("pid", "000000001"))
        assertFalse(Passport.contentValid("pid", "0123456789"))
    }

    @Test
    fun testParse() {
        assertTrue(Passport.parse("pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980\n" +
                "hcl:#623a2f").isValid(true))
    }
}