package io.njlr.bytes.tests;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.Test;

import io.njlr.bytes.Bytes;
import io.njlr.bytes.BytesBuilder;

public final class BytesBuilderTests {
	
	// TODO: More test coverage

	@Test
	public void testBasics() {
		
		BytesBuilder builder = new BytesBuilder();
		
		Bytes bytes = builder.
				append((byte) 0).
				append((byte) 1).
				append((byte) 2).
				append((byte) 3).
				append((byte) 4).
				append((byte) 5).
				append((byte) 6).
				append((byte) 7).toBytes();
		
		assert(bytes.length() == 8);
		
		assert(bytes.get(0) == (byte) 0);
		assert(bytes.get(1) == (byte) 1);
		assert(bytes.get(2) == (byte) 2);
		assert(bytes.get(3) == (byte) 3);
		assert(bytes.get(4) == (byte) 4);
		assert(bytes.get(5) == (byte) 5);
		assert(bytes.get(6) == (byte) 6);
		assert(bytes.get(7) == (byte) 7);
	}
	
	@Test
	public void testLength() {
		
		BytesBuilder builder = new BytesBuilder(10);
		
		assert(builder.length() == 0);
		
		builder.append((byte) 1);
		
		assert(builder.length() == 1);
		
		builder.append((byte) 1).append((byte) 2).append((byte) 3);
		
		assert(builder.length() == 4);
	}
	
	@Test
	public void testAppendByteArray() {
		
		BytesBuilder builder = new BytesBuilder();
		
		byte[] array = new byte[] {(byte) 0, (byte) 1, (byte) 2, (byte) 3 };
		
		Bytes bytes = builder.append(array).toBytes();
		
		assert(bytes.get(0) == array[0]);
		assert(bytes.get(1) == array[1]);
		assert(bytes.get(2) == array[2]);
		assert(bytes.get(3) == array[3]);
	}
	
	@Test
	public void testAppendBytes() {
		
		BytesBuilder builder = new BytesBuilder();
		
		Bytes bytes = builder.
				append((byte) 0).
				append((byte) 1).
				append((byte) 2).
				append((byte) 3).toBytes();
		
		builder.append(bytes);
		
		Bytes moreBytes = builder.toBytes();
		
		assert(moreBytes.length() == bytes.length() * 2);
		
		assert(bytes.get(0) == moreBytes.get(0));
		assert(bytes.get(1) == moreBytes.get(1));
		assert(bytes.get(2) == moreBytes.get(2));
		assert(bytes.get(3) == moreBytes.get(3));
		assert(bytes.get(0) == moreBytes.get(4));
		assert(bytes.get(1) == moreBytes.get(5));
		assert(bytes.get(2) == moreBytes.get(6));
		assert(bytes.get(3) == moreBytes.get(7));
	}
	
	@Test
	public void testAppendBoolean() {
		
		BytesBuilder builder = new BytesBuilder();
		
		Bytes bytes = builder.appendBoolean(true).appendBoolean(false).appendBoolean(true).appendBoolean(true).toBytes();
		
		assert(builder.length() == 4);
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes.array());
		
		assert((byteBuffer.get() == 1) == true);
		assert((byteBuffer.get() == 1) == false);
		assert((byteBuffer.get() == 1) == true);
		assert((byteBuffer.get() == 1) == true);
	}
	
	@Test
	public void testAppendChar() {
		
		BytesBuilder builder = new BytesBuilder();
		
		builder.appendChar('A').appendChar('1').appendChar('?').appendChar('m', ByteOrder.LITTLE_ENDIAN);
		
		assert(builder.length() ==  Character.BYTES * 4);
		
		Bytes bytes = builder.toBytes();
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes.array());
		
		assert(byteBuffer.getChar() == 'A');
		assert(byteBuffer.getChar() == '1');
		assert(byteBuffer.getChar() == '?');
		
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		
		assert(byteBuffer.getChar() == 'm');
	}
	
	@Test
	public void testAppendShort() {
		
		BytesBuilder builder = new BytesBuilder();
		
		builder.appendShort((short) 100).appendShort((short) 2000).appendShort((short) -30000).appendShort((short) 12345, ByteOrder.LITTLE_ENDIAN);
		
		assert(builder.length() == Short.BYTES * 4);
		
		Bytes bytes = builder.toBytes();
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes.array());
		
		assert(byteBuffer.getShort() == 100);
		assert(byteBuffer.getShort() == 2000);
		assert(byteBuffer.getShort() == -30000);
		
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		
		assert(byteBuffer.getShort() == 12345);
	}
	
	@Test
	public void testAppendInt() {
		
		BytesBuilder builder = new BytesBuilder();
		
		builder.appendInt(100).appendInt(-2000).appendInt(30000).appendInt(12345, ByteOrder.LITTLE_ENDIAN);
		
		assert(builder.length() == Integer.BYTES * 4);
		
		Bytes bytes = builder.toBytes();
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes.array());
		
		assert(byteBuffer.getInt() == 100);
		assert(byteBuffer.getInt() == -2000);
		assert(byteBuffer.getInt() == 30000);
		
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		
		assert(byteBuffer.getInt() == 12345);
	}
	
	@Test
	public void testAppendLong() {
		
		BytesBuilder builder = new BytesBuilder();
		
		builder.appendLong(100l).appendLong(-456l).appendLong(999999999999999l).appendLong(64000l, ByteOrder.LITTLE_ENDIAN);
		
		assert(builder.length() ==  Long.BYTES * 4);
		
		Bytes bytes = builder.toBytes();
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes.array());
		
		assert(byteBuffer.getLong() == 100l);
		assert(byteBuffer.getLong() == -456l);
		assert(byteBuffer.getLong() == 999999999999999l);
		
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		
		assert(byteBuffer.getLong() == 64000l);
	}
	
	@Test
	public strictfp void testAppendFloat() {
		
		BytesBuilder builder = new BytesBuilder();
		
		builder.appendFloat(100f).appendFloat(-456f).appendFloat(999999999.999999f).appendFloat(64000f, ByteOrder.LITTLE_ENDIAN);
		
		assert(builder.length() ==  Float.BYTES * 4);
		
		Bytes bytes = builder.toBytes();
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes.array());
		
		assert(byteBuffer.getFloat() == 100f);
		assert(byteBuffer.getFloat() == -456f);
		assert(byteBuffer.getFloat() == 999999999.999999f);
		
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		
		assert(byteBuffer.getFloat() == 64000f);
	}
	
	@Test
	public strictfp void testAppendDouble() {
		
		BytesBuilder builder = new BytesBuilder();
		
		builder.appendDouble(100d).appendDouble(-456d).appendDouble(9999999999.9999999d).appendDouble(64000d, ByteOrder.LITTLE_ENDIAN);
		
		assert(builder.length() ==  Double.BYTES * 4);
		
		Bytes bytes = builder.toBytes();
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes.array());
		
		assert(byteBuffer.getDouble() == 100d);
		assert(byteBuffer.getDouble() == -456d);
		assert(byteBuffer.getDouble() == 9999999999.9999999d);
		
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		
		assert(byteBuffer.getDouble() == 64000d);
	}
	
	@Test
	public void testClear() {
		
		BytesBuilder builder = new BytesBuilder(10);
		
		assert(builder.length() == 0);
		
		builder.appendInt(100).appendInt(200).appendInt(300);
		
		assert(builder.length() == Integer.BYTES * 3);
		
		builder.clear();
		
		assert(builder.length() == 0);
	}
	
	@Test
	public void testAllocate() {
		
		BytesBuilder builder = new BytesBuilder(10);
		
		assert(builder.allocation() == 10);
		
		builder.allocate(20);
		
		assert(builder.allocation() == 20);
	}
	
	@Test
	public void testCompact() {
		
		BytesBuilder builder = new BytesBuilder(10);
		
		assert(builder.allocation() == 10);
		
		builder.compact();
		
		assert(builder.allocation() == 0);
	}
}
