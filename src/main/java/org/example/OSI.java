package org.example;

public class OSI {
    /* -------------------------------------------------- *
     * Nested RECORD to hold a Protocol-Data-Unit (PDU).   *
     * Records are available since Java 16 and give us     *
     * value-type semantics plus boiler-plate reduction.   *
     * -------------------------------------------------- */
    public record PDU(String header, String payload, String trailer) {
        @Override public String toString() {
            return header + payload + trailer;
        }
    }

    /* -------- OSI layer helpers (top → bottom) -------- */
    private static PDU application(String data)          { return new PDU("HTTP ", data, ""); }
    private static PDU presentation(PDU p)               { return new PDU("TLS  ",  p.toString(), ""); }
    private static PDU session(PDU p)                    { return new PDU("SESS ", p.toString(), ""); }
    private static PDU transport(PDU p)                  { return new PDU("TCP  ", p.toString(), ""); }
    private static PDU network(PDU p)                    { return new PDU("IP   ", p.toString(), ""); }
    private static PDU dataLink(PDU p)                   { return new PDU("ETH  ", p.toString(), "FCS "); }

    /** Encapsulate the message exactly once through all layers. */
    private static PDU encapsulate(String message) {
        PDU pdu = application(message);
        pdu = presentation(pdu);
        pdu = session(pdu);
        pdu = transport(pdu);
        pdu = network(pdu);
        pdu = dataLink(pdu);
        return pdu;          // ► what would actually hit the wire
    }

    public static void main(String[] args) {
        String message = "Hi OSI";           // arbitrary payload
        for (int i = 1; i <= 2; i++) {       // run twice for determinism check
            System.out.printf("Run %d:%n%s%n%n", i, encapsulate(message));
        }
    }
}
