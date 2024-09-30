module example.deloy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Mở gói com.finalproj cho JavaFX
    opens com.finalproj.Controller to javafx.fxml;
    opens com.finalproj.Model to javafx.base; // Nếu bạn sử dụng các lớp Model trong JavaFX

    // Xuất gói com.finalproj
    exports com.finalproj.Controller;
    exports com.finalproj.Model;
}
