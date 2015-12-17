package io.njlr.bytes;

import java.nio.ByteOrder;

public final class BytesReader {
	
	private final Bytes bytes;
	
	private int index;
	
	private ByteOrder endianness;

	public BytesReader(final Bytes bytes) {
		
		super();
		
		this.bytes = bytes;
		
		endianness = ByteOrder.BIG_ENDIAN;
	}
	
	public BytesReader skip(final int n) {
		
		index += n;
		
		return this;
	}
	
	public byte readByte() {
		
		return bytes.get(index++);
	}
	
	public int readInt(final ByteOrder endianness) {
		
		byte b3 = bytes.get(index++);
		byte b2 = bytes.get(index++);
		byte b1 = bytes.get(index++);
		byte b0 = bytes.get(index++);
		
		if (endianness == ByteOrder.BIG_ENDIAN) {
			
			return (((b3 & 0xff) << 24) |
	                ((b2 & 0xff) << 16) |
	                ((b1 & 0xff) <<  8) |
	                ((b0 & 0xff) <<  0));
		} else {
			
			return (((b0 & 0xff) << 24) |
	                ((b1 & 0xff) << 16) |
	                ((b2 & 0xff) <<  8) |
	                ((b3 & 0xff) <<  0));
		}
	}
	
	public int readInt() {
		
		return readInt(endianness);
	}
	
	public Bytes readRemaining() {
		
		return bytes.sub(index);
	}
	
	public int remaining() {
		
		return bytes.length() - index;
	}
	
	public void reset() {
		
		index = 0;
		
		endianness = ByteOrder.BIG_ENDIAN;
	}
}
