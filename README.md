# Bytes
It's like `String` but for bytes. `Bytes` is an essential building block in immutable network messages for high concurrency programs. 

## 5 Second Demo

    // Easily wrap existing arrays
    Bytes bytes = new Bytes(new byte[] { (byte) 1, (byte) 2, (byte) 3 });
    
    // Efficiently build sequences with a fluent API
    Bytes payload = new BytesBuilder().
        append(senderId).
        append(topicId).
        append(messageBytes).toBytes();
    
    // Don't worry; it's thread-safe
    notifyListeners(payload);
