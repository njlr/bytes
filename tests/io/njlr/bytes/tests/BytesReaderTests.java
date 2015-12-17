package io.njlr.bytes.tests;

import java.nio.ByteOrder;

import org.junit.Test;

import io.njlr.bytes.Bytes;
import io.njlr.bytes.BytesBuilder;
import io.njlr.bytes.BytesReader;

public final class BytesReaderTests {

	@Test
	public void testReadInt() {
		
		final Bytes bytes = new BytesBuilder(8).appendInt(2).appendInt(36, ByteOrder.LITTLE_ENDIAN).appendInt(107, ByteOrder.BIG_ENDIAN).toBytes();
		
		final BytesReader reader = bytes.read();
		
		assert(reader.readInt() == 2);
		assert(reader.readInt(ByteOrder.LITTLE_ENDIAN) == 36);
		assert(reader.readInt(ByteOrder.BIG_ENDIAN) == 107);
	}
}
