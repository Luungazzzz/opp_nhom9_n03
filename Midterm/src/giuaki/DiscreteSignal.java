package giuaki;

public class DiscreteSignal implements giuaki.Signal {
    private double bienDo;
    private double tinhThuongXuyen;

    public DiscreteSignal(double bienDo, double tinhThuongXuyen) {
        this.bienDo = bienDo;
        this.tinhThuongXuyen = tinhThuongXuyen;
    }

    // Hiển thị tín hiệu
    @Override
    public void displaySignal() {
        System.out.println("Tín hiệu rời rạc với: Biên độ = " + bienDo + ", tính thường xuyên = " + tinhThuongXuyen);
    }

    // Tính tín hiệu rời rạc với giới hạn phạm vi hợp lý
    public double tinhTinHieu(int n) {
        double result = 0;
        int lowerBound = -10; // giới hạn phạm vi tính toán
        int upperBound = 10;

        for (int k = lowerBound; k <= upperBound; k++) {
            result += x(k) * delta(n - k);
        }
        return result;
    }

    // Giá trị x(k), hiện tại giả định bằng 1
    private double x(int k) {
        return 1.0; // giá trị mặc định là 1
    }

    // Hàm tạo dãy xung đơn vị
    private int delta(int n) {
        return (n == 0) ? 1 : 0;
    }
}
