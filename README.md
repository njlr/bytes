<a target='_blank' rel='nofollow' href='https://app.codesponsor.io/link/hq7GXSxnYW3qEM98fqMVob9v/njlr/Bytes'>  <img alt='Sponsor' width='888' height='68' src='https://app.codesponsor.io/embed/hq7GXSxnYW3qEM98fqMVob9v/njlr/Bytes.svg' /></a>

# Bytes

It's like `String` but for bytes. `Bytes` is an essential building block in immutable network messages for high concurrency programs. 

## 5 Second Demo

    // Easily wrap existing arrays
    final Bytes bytes = new Bytes(new byte[] { (byte) 1, (byte) 2, (byte) 3 });
    
    // Efficiently build sequences with a fluent API
    final Bytes payload = new BytesBuilder().
        append(senderId).
        append(topicId).
        append(messageBytes).toBytes();
    
    // Don't worry; it's thread-safe
    notifyListeners(payload);
