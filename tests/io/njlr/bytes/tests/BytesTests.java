package io.njlr.bytes.tests;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.Test;

import io.njlr.bytes.Bytes;

/** 
 * Bytes is a simple class; these tests are more sanity checks than anything else. 
 * 
 */
public final class BytesTests {
	
	// TODO: More test coverage
	
	@Test
	public void testBasics() {
		
		byte[] myArray = "Hello world".getBytes(StandardCharsets.UTF_8);
		
		Bytes bytes = new Bytes(myArray);
		
		assert(myArray.length == bytes.length());
		
		for (int i = 0; i < myArray.length; i++) {
			
			assert(myArray[i] == bytes.get(i));
		}
		
		assert(Arrays.equals(myArray, bytes.array()));
	}
	
	@Test
	public void testEquals() {
		
		byte[] myArray = "Hello world".getBytes(StandardCharsets.UTF_8);
		
		Bytes bytes = new Bytes(myArray);
		Bytes moreBytes = new Bytes(myArray);
		
		assert(bytes.equals(moreBytes));
		
		byte[] myOtherArray = "Hi!".getBytes(StandardCharsets.UTF_8);
		
		Bytes evenMoreBytes = new Bytes(myOtherArray);
		
		assert(!bytes.equals(evenMoreBytes));
	}
	
	@Test
	public void testHashCode() {
		
		byte[] myArray = "How are you?".getBytes(StandardCharsets.UTF_8);
		
		Bytes bytes = new Bytes(myArray);
		
		// Two instances with equivalent arrays should have the same hash code
		Bytes moreBytes = new Bytes(myArray);
		
		assert(bytes.hashCode() == moreBytes.hashCode());
		
		// Two instances with different arrays should have different hash codes
		byte[] myOtherArray = "Just fine thank-you".getBytes(StandardCharsets.UTF_8);
		
		Bytes evenMoreBytes = new Bytes(myOtherArray);
		
		assert(bytes.hashCode() != evenMoreBytes.hashCode());
	}
	
	@Test
	public void testImmutability() {
		
		byte[] myArray = "Hello world".getBytes(StandardCharsets.UTF_8);
		
		Bytes bytes = new Bytes(myArray);
		
		assert(Arrays.equals(myArray, bytes.array()));
		
		// Does modifying the result of array change the Bytes instance? 
		byte[] myOtherArray = bytes.array();
		
		myOtherArray[5] = '?';
		
		assert(Arrays.equals(myArray, bytes.array()));
		
		// Does modifying the original change the Bytes instance to match? 
		myArray[3] = '?';
		
		assert(!Arrays.equals(myArray, bytes.array()));
	}
	
	@Test
	public void testStaticConstructor() {
		
		byte[] myArray = "Salut".getBytes(StandardCharsets.UTF_8);
		
		Bytes bytes = new Bytes(myArray);
		Bytes moreBytes = Bytes.of(myArray);
		
		assert(bytes.equals(moreBytes));
		
		// ... But did it copy the array? 
		myArray[2] = '?';
		
		assert(bytes.equals(moreBytes));
	}
}
