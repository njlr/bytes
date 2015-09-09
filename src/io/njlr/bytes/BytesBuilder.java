package io.njlr.bytes;

import java.io.Serializable;
import java.nio.ByteOrder;
import java.util.Arrays;

/** 
 * <code>BytesBuilder</code> is a helper class for building sequences of bytes. 
 * As <code>Bytes</code> is analogous to <code>String</code>, <code>BytesBuilder</code> is analogous to <code>StringBuilder</code>. 
 * 
 * The class works by creating an array of bytes and allowing the user to manipulate that array through a series of convenient functions. 
 * The array is automatically enlarged as the caller appends values. 
 * To avoid unnecessary array copying, the allocation can be specified in the constructor. 
 * 
 * When primitive types are written to the underlying array, they must be converted to sequences of bytes. 
 * The endianness of these sequences can be specified explicitly for each append or the default can be used. 
 * The default is <code>ByteOrder.BIG_ENDIAN</code>, but this can be changed either via the constructor or using the <code>order</code> method.
 * Changing the endianness does not change values that have already been appended. 
 * 
 * The functions for appending primitives are names after their argument type so that callers are less likely to accidently forget a cast. 
 * 
 * Multiple different <code>Bytes</code> instances can be built using one <code>BytesBuilder</code>. 
 * 
 * <code>BytesBuilder</code> is mutable and not thread-safe. 
 * 
 */
public final strictfp class BytesBuilder implements Serializable {
	
	private static final long serialVersionUID = 6090275957872248658L;
	
	private byte[] value;
	private int count;
	
	private ByteOrder endianness;

	/** 
	 * Creates a new <code>BytesBuilder</code> with the default endianness and allocation.
	 */
	public BytesBuilder() {
		
		super();
		
		value = new byte[8];
		
		endianness = ByteOrder.BIG_ENDIAN;
	}
	
	/** 
	 * Creates a new <code>BytesBuilder</code> where the backing array has the given allocation.
	 * 
	 * The allocation will be automatically increased as necessary, but this will trigger array copying.  
	 * 
	 * @param allocation The starting allocation in number of bytes
	 */
	public BytesBuilder(final int allocation) {
		
		super();
		
		value = new byte[allocation];
		
		endianness = ByteOrder.BIG_ENDIAN;
	}
	
	/** 
	 * Creates a new <code>BytesBuilder</code> with a backing array with the given allocation and 
	 * the given default endianness.
	 * 
	 * The allocation will be automatically increased as necessary, but this will trigger array copying. 
	 * The default endianness can be changed later, or overridden for a specific append. 
	 * 
	 * @param allocation The starting allocation in number of bytes
	 * @param endianness The default endianness to use when appending primitive types
	 */
	public BytesBuilder(final int allocation, final ByteOrder endianness) {
		
		super();
		
		value = new byte[allocation];
		
		this.endianness = endianness;
	}
	
	/**
	 * Appends the given byte to the underlying array. 
	 * 
	 * @param b The byte value to append
	 * @return This BytesBuilder
	 */
	public BytesBuilder append(final byte b) {
		
		expandCapacity(count + 1);
		
		value[count++] = b;
		
		return this;
	}
	
	/**
	 * Appends the given array of bytes to the underlying array. 
	 * 
	 * @param b The array of bytes to append
	 * @return This BytesBuilder
	 */
	public BytesBuilder append(final byte[] b) {
		
		final int newCount = count + b.length;
		
		expandCapacity(newCount);
		
		System.arraycopy(b, 0, value, count, b.length);
		
		count = newCount;
		
		return this;
	}
	
	/**
	 * Appends the given <code>Bytes</code> instance to the underlying array. 
	 * 
	 * @param b The instance to append
	 * @return This BytesBuilder
	 */
	public BytesBuilder append(final Bytes b) {
		
		return append(b.array());
	}
	
	/**
	 * Appends the given boolean to the underlying array. 
	 * 
	 * @param b The boolean to append
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendBoolean(final boolean b) {
		
		expandCapacity(count + 1);
		
		value[count++] = b ? (byte)1 : (byte)0;
		
		return this;
	}
	
	/**
	 * Appends the given char to the underlying array. 
	 * 
	 * The value is encoded using the given endianness, rather than the default. 
	 * 
	 * @param c The char to append
	 * @param endianness The endianness to use for the encoding. 
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendChar(final char c, final ByteOrder endianness) {
		
		expandCapacity(count + Character.BYTES);
		
		if (endianness == ByteOrder.BIG_ENDIAN) {
		
			value[count++] = (byte)(c >> 8);
			value[count++] = (byte)(c >> 0);
		} else {
			
			value[count++] = (byte)(c >> 0);
			value[count++] = (byte)(c >> 8);
		}
		
		return this;
	}
	
	/**
	 * Appends the given char to the underlying array. 
	 * 
	 * The value is encoded using the default endianness. 
	 * 
	 * @see io.njlr.bytes.BytesBuilder#order()
	 * 
	 * @param c The char to append
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendChar(final char c) {
		
		return appendChar(c, endianness);
	}
	
	/**
	 * Appends the given short to the underlying array. 
	 * 
	 * The value is encoded using the given endianness, rather than the default. 
	 * 
	 * The underlying array will be automatically extended if there is insufficient space. 
	 * 
	 * @param s The short to append
	 * @param endianness The endianness to use for the encoding. 
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendShort(final short s, final ByteOrder endianness) {
		
		expandCapacity(count + Short.BYTES);
		
		if (endianness == ByteOrder.BIG_ENDIAN) {
		
			value[count++] = (byte) (s >> 8);
			value[count++] = (byte) s;
		} else {
			
			value[count++] = (byte) s;
			value[count++] = (byte) (s >> 8);
		}
		
		return this;
	}
	
	/**
	 * Appends the given short to the underlying array. 
	 * 
	 * The value is encoded using the default endianness. 
	 * 
	 * @see io.njlr.bytes.BytesBuilder#order()
	 * 
	 * @param s The short to append
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendShort(final short s) {
		
		return appendShort(s, endianness);
	}
	
	/**
	 * Appends the given int to the underlying array. 
	 * 
	 * The value is encoded using the given endianness, rather than the default. 
	 * 
	 * The underlying array will be automatically extended if there is insufficient space. 
	 * 
	 * @param i The int to append
	 * @param endianness The endianness to use for the encoding. 
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendInt(final int i, final ByteOrder endianness) {
		
		expandCapacity(count + Integer.BYTES);
		
		if (endianness == ByteOrder.BIG_ENDIAN) {
			
			value[count++] = (byte) (i >> 24);
			value[count++] = (byte) (i >> 16);
			value[count++] = (byte) (i >> 8);
			value[count++] = (byte) (i);
		} else {
			
			value[count++] = (byte) (i);
			value[count++] = (byte) (i >> 8);
			value[count++] = (byte) (i >> 16);
			value[count++] = (byte) (i >> 24);
		}
		
		return this;
	}
	
	/**
	 * Appends the given int to the underlying array. 
	 * 
	 * The value is encoded using the default endianness. 
	 * 
	 * @see io.njlr.bytes.BytesBuilder#order()
	 * 
	 * @param i The int to append
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendInt(final int i) {
		
		return appendInt(i, endianness);
	}
	
	/**
	 * Appends the given long to the underlying array. 
	 * 
	 * The value is encoded using the given endianness, rather than the default. 
	 * 
	 * The underlying array will be automatically extended if there is insufficient space. 
	 * 
	 * @param l The long to append
	 * @param endianness The endianness to use for the encoding. 
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendLong(final long l, final ByteOrder endianness) {
		
		expandCapacity(count + Long.BYTES);
		
		if (endianness == ByteOrder.BIG_ENDIAN) {
			
			value[count++] = (byte) (l >> 56);
			value[count++] = (byte) (l >> 48);
			value[count++] = (byte) (l >> 40);
			value[count++] = (byte) (l >> 32);
			value[count++] = (byte) (l >> 24);
			value[count++] = (byte) (l >> 16);
			value[count++] = (byte) (l >> 8);
			value[count++] = (byte) (l >> 0);
		} else {
			
			value[count++] = (byte) (l >> 0);
			value[count++] = (byte) (l >> 8);
			value[count++] = (byte) (l >> 16);
			value[count++] = (byte) (l >> 24);
			value[count++] = (byte) (l >> 32);
			value[count++] = (byte) (l >> 40);
			value[count++] = (byte) (l >> 48);
			value[count++] = (byte) (l >> 56);
		}
		
		return this;
	}
	
	/**
	 * Appends the given long to the underlying array. 
	 * 
	 * The value is encoded using the default endianness. 
	 * 
	 * @see io.njlr.bytes.BytesBuilder#order()
	 * 
	 * @param l The long to append
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendLong(final long l) {
		
		return appendLong(l, endianness);
	}
	
	/**
	 * Appends the given float to the underlying array. 
	 * 
	 * The value is encoded using the given endianness, rather than the default. 
	 * 
	 * The underlying array will be automatically extended if there is insufficient space. 
	 * 
	 * @param f The float to append
	 * @param endianness The endianness to use for the encoding. 
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendFloat(final float f, final ByteOrder endianness) {
		
		return appendInt(Float.floatToIntBits(f), endianness);
	}
	
	/**
	 * Appends the given float to the underlying array. 
	 * 
	 * The value is encoded using the default endianness. 
	 * 
	 * @see io.njlr.bytes.BytesBuilder#order()
	 * 
	 * @param f The float to append
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendFloat(final float f) {
		
		return appendFloat(f, endianness);
	}
	
	/**
	 * Appends the given double to the underlying array. 
	 * 
	 * The value is encoded using the given endianness, rather than the default. 
	 * 
	 * The underlying array will be automatically extended if there is insufficient space. 
	 * 
	 * @param d The double to append
	 * @param endianness The endianness to use for the encoding. 
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendDouble(final double d, final ByteOrder endianness) {
		
		return appendLong(Double.doubleToLongBits(d), endianness);
	}
	
	/**
	 * Appends the given double to the underlying array. 
	 * 
	 * The value is encoded using the default endianness. 
	 * 
	 * @see io.njlr.bytes.BytesBuilder#order()
	 * 
	 * @param d The double to append
	 * @return This BytesBuilder
	 */
	public BytesBuilder appendDouble(final double d) {
		
		return appendDouble(d, endianness);
	}
	
	/**
	 * Gets the current number of bytes being used. 
	 * 
	 * This is the length of the <code>Bytes</code> object that would be generated now. 
	 * 
	 * @see io.njlr.bytes.BytesBuilder#allocation()
	 * 
	 * @return The current number of bytes being used
	 */
	public int length() {
		
		return count;
	}
	
	/**
	 * Gets the current number of bytes allocated. 
	 * 
	 * @see io.njlr.bytes.BytesBuilder#length()
	 * 
	 * @return The current number of bytes allocated
	 */
	public int allocation() {
		
		return value.length;
	}
	
	/**
	 * Gets the current default endianness. 
	 * 
	 * @return The current default endianness
	 */
	public ByteOrder order() {
		
		return endianness;
	}
	
	/**
	 * Changes the default endianness when primitives are appended. 
	 * 
	 * Note that this does not affect previously appended values. 
	 * 
	 * @param endianness
	 * @return This BytesBuilder
	 */
	public BytesBuilder order(final ByteOrder endianness) {
		
		this.endianness = endianness;
		
		return this;
	}
	
	/** 
	 * Trims the underlying array so that it is only big enough to contain the appended values.
	 *  
	 * @return This BytesBuilder
	 */
    public BytesBuilder compact() {
    	
        if (count < value.length) {
        	
            value = Arrays.copyOf(value, count);
        }
        
        return this;
    }
    
    /** 
     * Reverses the order of the bytes already appended to the underlying array.
     *  
     * @return This BytesBuilder
     */
    public BytesBuilder reverse() {

        int i = 0;
        int j = value.length - 1;

        byte k;

        while (j > i) {
        	
            k = value[j];

            value[j] = value[i];
            value[i] = k;

            j--;
            i++;
        }
    	
    	return this;
    }
    
    /**
     * Empties the <code>BytesBuilder</code> so that the instance can be reused.
     * 
     * The intention of this method is that it will be called between each <code>Bytes</code> instance built. 
     * Therefore, the interface is not fluent, so that it will occupy it own line-of-code, and be clear to the user.
     *  
     * This method does not clear the allocation. 
     *  
     * @see io.njlr.bytes.BytesBuilder#compact()
     * @see io.njlr.bytes.BytesBuilder#allocate()
     * 
     */
    public void clear() {

        count = 0;
    }
    
	/**
	 * Changes the allocation of the underlying array to the given number of bytes. 
	 * 
	 * Any values outside of the allocation will be lost. 
	 * The write pointer will be set to the new allocation if it is greater than the new allocation. 
	 * 
     * The intention of this method is that it will be called between each <code>Bytes</code> instance built. 
     * Therefore, the interface is not fluent, so that it will occupy it own line-of-code, and be clear to the user. 
	 * 
	 * Note that it is more efficient to set a sufficient allocation in the constructor. 
	 * 
	 * @param allocation The number of bytes to allocate
	 */
	public void allocate(final int allocation) {
		
		value = Arrays.copyOf(value, allocation);
		
		if (count > allocation) {
			
			count = allocation;
		}
	}
    
    /** 
     * Creates a <code>Bytes</code> instance from the bytes appended so far. 
     * 
     * Since <code>Bytes</code> is immutable, future appends will not change the result. 
     * 
     * Note that this method does not change the allocation of the underlying array. 
     */
    public Bytes toBytes() {
    	
    	compact();
    	
    	return new Bytes(Arrays.copyOf(value, count));
    }
    
	private void expandCapacity(final int minimumCapacity) {
		
        int newCapacity = (value.length + 1) * 2;
        
        if (newCapacity < 0) {
        	
            newCapacity = Integer.MAX_VALUE;

        } else if (minimumCapacity > newCapacity) {

            newCapacity = minimumCapacity;
        }

        value = Arrays.copyOf(value, newCapacity);
	}
}
