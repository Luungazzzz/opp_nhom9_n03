package giuaki;

public class Radar {
    // Phân tích tín hiệu theo công thức trong đề bài
    public double phanTichTinHieu(int n) {
        if (n >= 0 && n <= 15) {
            return 1 - (n / 15.0);
        }
        return 0;
    }
}
