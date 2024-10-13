module example.deloy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.finalproj.Controller to javafx.fxml;
    opens com.finalproj.Model to javafx.base;
    opens com.finalproj.View to javafx.fxml;

    // Xuất gói com.finalproj
    exports com.finalproj.Controller;
    exports com.finalproj.Model;
    exports com.finalproj.View;
}
