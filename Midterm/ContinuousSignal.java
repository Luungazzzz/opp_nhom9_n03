public class ContinuousSignal implements Signal {
    private double amplitude; // Biên độ của tín hiệu
    private double frequency; // Tần số của tín hiệu

    public ContinuousSignal(double amplitude, double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    // Triển khai phương thức lấy giá trị tín hiệu tại thời điểm t
    @Override
    public double getValue(double t) {
// Ví dụ tín hiệu liên tục dạng sóng sin
        return amplitude * Math.sin(2 * Math.PI * frequency * t);
    }

    // Triển khai phương thức hiển thị thông tin tín hiệu
    @Override
    public void displayInfo() {
        System.out.println("Continuous Signal: Amplitude = " + amplitude + ", Frequency = " + frequency);
    }
}