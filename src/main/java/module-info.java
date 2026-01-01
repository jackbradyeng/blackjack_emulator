module org.example.blackjack_emulator {
    requires java.sql;
    requires java.desktop;
    requires jdk.jfr;
    requires static org.junit.jupiter.api;
    // requires org.example.blackjack_emulator;
    opens org.example.blackjack_emulator;
    exports org.example.blackjack_emulator;
}