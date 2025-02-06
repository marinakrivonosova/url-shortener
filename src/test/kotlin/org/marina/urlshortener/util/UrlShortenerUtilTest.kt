package org.marina.urlshortener.util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


internal class UrlShortenerUtilTest {

    private val util = UrlShortenerUtil()

    @Test
    fun testEncode() {
        assertEquals("I", util.encode(44))
        assertEquals("c", util.encode(12))
        assertEquals("3V", util.encode(243))
        assertEquals("2gs", util.encode(8708))
        assertEquals("3d7", util.encode(12345))
        assertEquals("nIX6", util.encode(5654344))
        assertEquals("10000", util.encode(14776336))
    }

    @Test
    fun decode() {
        assertEquals(44, util.decode("I"))
        assertEquals(12, util.decode("c"))
        assertEquals(243, util.decode("3V"))
        assertEquals(8708, util.decode("2gs"))
        assertEquals(12345, util.decode("3d7"))
        assertEquals(5654344, util.decode("nIX6"))
        assertEquals(14776336, util.decode("10000"))
    }

    @Test
    fun decodeAndEncodeTest() {
        assertEquals(454433, util.decode(util.encode(454433)))
        assertEquals(454655433, util.decode(util.encode(454655433)))
        assertEquals(Int.MAX_VALUE, util.decode(util.encode(Int.MAX_VALUE.toLong())))
    }
}