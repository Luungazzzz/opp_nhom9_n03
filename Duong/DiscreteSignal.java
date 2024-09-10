import java.util.Arrays;

public class DiscreteSignal implements Signal {
    private double[] values; // Mảng chứa các giá trị tín hiệu rời rạc

    // Constructor: Khởi tạo tín hiệu rời rạc với mảng giá trị
    public DiscreteSignal(double[] values) {
        this.values = values;
    }

    // Triển khai phương thức lấy giá trị tín hiệu tại thời điểm t
    @Override
    public double getValue(double t) {
        int n = (int) t; // Chuyển đổi thời gian t sang chỉ số nguyên n
        if (n >= 0 && n < values.length) {
            return values[n];
        } else {
            return 0; // Giá trị mặc định ngoài phạm vi
        }
    }

    // Triển khai phương thức hiển thị thông tin tín hiệu
    @Override
    public void displayInfo() {
        System.out.println("Discrete Signal: " + Arrays.toString(values));
    }

    // Phương thức tính toán tín hiệu rời rạc theo định nghĩa toán học [1]
    public double calculateDiscreteSignal(int n) {
        double result = 0.0;
        for (int k = 0; k < values.length; k++) {
            result += values[k] * delta(n - k);
        }
        return result;
    }

    // Hàm xung đơn vị (delta function)
    private int delta(int n) {
        return (n == 0) ? 1 : 0;
    }

    // Phương thức hiển thị các giá trị tín hiệu
    public void displaySignal() {
        System.out.println("Discrete Signal values: " + Arrays.toString(values));
    }

    // Phương thức main để chạy chương trình
    public static void main(String[] args) {
        // Tạo mảng các giá trị tín hiệu rời rạc
        double[] signalValues = {1.0, 2.0, 3.0, 4.0, 5.0};

        // Khởi tạo đối tượng DiscreteSignal với mảng giá trị tín hiệu
        DiscreteSignal signal = new DiscreteSignal(signalValues);

        // Hiển thị thông tin tín hiệu
        signal.displayInfo();

        // Hiển thị giá trị tín hiệu tại n = 2
        double valueAt2 = signal.getValue(2);
        System.out.println("Signal value at n = 2: " + valueAt2);

        // Tính toán tín hiệu rời rạc tại n = 3
        double calculatedValue = signal.calculateDiscreteSignal(3);
        System.out.println("Calculated Discrete Signal at n = 3: " + calculatedValue);

        // Hiển thị tất cả các giá trị tín hiệu
        signal.displaySignal();
    }
}

