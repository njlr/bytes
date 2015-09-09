package io.njlr.bytes;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

/** 
 * <code>Bytes</code> is used to represent immutable sequences of bytes. 
 * Think of it as <code>String</code> but for bytes.  
 * 
 * <code>Bytes</code> is fully immutable, so it is safe to share across threads. 
 * 
 * To create <code>Bytes</code> instances through a fluent API, see <code>BytesBuilder</code>. 
 */
public final class Bytes implements Comparable<Bytes>, Serializable {

	private static final long serialVersionUID = 5800213226235668619L;
	
	private final byte[] value;
	
	/**
	 * Creates a new <code>Bytes</code> instance for the given sequence of bytes. 
	 * 
	 * The sequence is copied, so subsequent changes to the array do not change the <code>Bytes</code> instance. 
	 * 
	 * @param value The sequence of bytes to wrap
	 */
	public Bytes(final byte[] value) {
		
		super();
		
		Objects.requireNonNull(value);
		
		this.value = Arrays.copyOf(value, value.length);
	}
	
	/**
	 * Gets the byte at the given index in the sequence. 
	 * 
	 * Using an index outside the length of the sequence will throw an exception. 
	 * 
	 * @param index The index to access
	 * @return The byte at the given index
	 */
	public byte get(final int index) {
		
		return value[index];
	}
	
	/**
	 * Gets the length of this sequence of bytes. 
	 * 
	 * @return The length of the sequence
	 */
	public int length() {
		
		return value.length;
	}
	
	/**
	 * Creates an array whose values are the same as those in this sequence. 
	 * 
	 * @return An array representing this sequence
	 */
	public byte[] array() {
		
		return Arrays.copyOf(value, value.length);
	}
	
    /**
     * Concatenates the values of another sequence to the end of this one. 
     * 
     * Since <code>Bytes</code> is immutable, this method does not change either existing sequence. 
     * 
     * @param that The sequence to concatenate
     * @return A new instance that is the result of the concatenation
     */
    public Bytes concat(final Bytes that) {

    	final int lengthOfThat = that.value.length;

        if (lengthOfThat == 0) {

            return this;
        }

        final int lengthOfThis = value.length;
        
        if (lengthOfThis == 0) {

            return that;
        }

        final byte buffer[] = Arrays.copyOf(value, lengthOfThis + lengthOfThat);
        
        System.arraycopy(value, 0, buffer, lengthOfThis, value.length);

        return new Bytes(buffer);
    }
    
    /**
     * Returns a new sequence that is a sub-sequence of this one. 
     * The sub-sequence ranges from the given index to the end of this one.  
     * 
     * @param beginIndex The inclusive start index
     * @return The resulting sub-sequence
     */
    public Bytes sub(final int beginIndex) {
    	
    	return new Bytes(Arrays.copyOfRange(value, beginIndex, value.length));
    }
    
    /**
     * Returns a new sequence that is a sub-sequence of this one. 
     * The sub-sequence ranges from the given start index to the given end index. 
     * 
     * @param beginIndex The inclusive start index
     * @param endIndex The exclusive ending index
     * @return The resulting sub-sequence
     */
    public Bytes sub(final int beginIndex, final int endIndex) {
    	
    	return new Bytes(Arrays.copyOfRange(value, beginIndex, endIndex));
    }

	@Override
	public int hashCode() {
		
        int hash = 0;

        for (int i = 0; i < value.length; i++) {

            hash = 37 * hash + value[i];
        }

        return hash;
	}
	
    /**
     * If <code>that</code> is a <code>Bytes</code> instance, then this tests if the two represent the same sequence of bytes. 
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param that The object to compare to
     * @return Whether or not the objects are equal
	 */
	@Override
	public boolean equals(final Object that) {
		
		if (this == that) {
			
			return true;
		} 
		
		if (that instanceof Bytes) {
			
			return Arrays.equals(this.value, ((Bytes) that).value);
		}
		
		return false;
	}
	

    /**
     * Creates a String representing the <code>Bytes</code> object. 
     * The underlying value is converted to UTF-8 for display. 
     *
     * @return A <code>String</code> representing the <code>Bytes</code> object. 
     */
	@Override
	public String toString() {
		
		return new StringBuilder().append("Bytes{").append(new String(value, StandardCharsets.UTF_8)).append("}").toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final Bytes that) {
		
        final int lengthOfThis = value.length;
        final int lengthOfThat = that.value.length;

        final int limit = Math.min(lengthOfThis, lengthOfThat);

        final byte[] v1 = value;
        final byte[] v2 = that.value;

        int k = 0;

        while (k < limit) {

            final byte c1 = v1[k];
            final byte c2 = v2[k];
            
            if (c1 != c2) {

                return c1 - c2;
            }

            k++;
        }

        return lengthOfThis - lengthOfThat;
	}
	
    /**
     * Returns the <code>Bytes</code> representation of a given <code>byte</code> array. 
     * The array is copied to protect the immutability of the <code>Bytes</code> object. 
     *
     * @param   value	a <code>byte</code> array.
     * @return  a new <code>Bytes</code> instance representing the given <code>byte</code> array argument.
     */
	public static Bytes of(final byte[] value) {
		
		return new Bytes(value);
	}
}
