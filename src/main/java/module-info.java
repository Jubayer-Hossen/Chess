module jubayer.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens jubayer.chess to javafx.fxml;
    opens jubayer.chess.ModelClasses to javafx.fxml;
    opens jubayer.chess.ControllerClasses to javafx.fxml;
    exports jubayer.chess;
    exports jubayer.chess.ModelClasses;
    exports jubayer.chess.ControllerClasses;
}