package giuaki;

public class Main {
    public static void main(String[] args) {
        // Tạo đối tượng tín hiệu rời rạc
        DiscreteSignal discreteSignal = new DiscreteSignal(5.0, 100.0);
        discreteSignal.displaySignal();

        // Tạo đối tượng tín hiệu liên tục
        ContinuousSignal continuousSignal = new ContinuousSignal( 3.5, 0.005);
        continuousSignal.displaySignal();

        // Phân tích tín hiệu bằng radar
        Radar radar = new Radar();
        System.out.println("Phân tích tín hiệu: ");
        for (int n = 0; n <= 15; n++) {
            double ketqua = radar.phanTichTinHieu(n);
            System.out.println("X(" + n + ") = " + ketqua);
        }

        // Kiểm tra tính toán tín hiệu rời rạc
        double discreteResult = discreteSignal.tinhTinHieu(4);
        System.out.println("Tín hiệu với n = 4: " + discreteResult);
    }
}
