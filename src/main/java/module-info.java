module org.example.blackjack_emulator {
    requires java.sql;
    requires java.desktop;
    requires jdk.jfr;
    requires static org.junit.jupiter.api; // optional at runtime
    opens org.example.blackjack_emulator;
    exports org.example.blackjack_emulator;
}