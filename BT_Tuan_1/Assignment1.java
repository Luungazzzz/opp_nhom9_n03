package oop_group9_week1;

public class Assignment1 {
    public static class Number{
        public int i;
    }
    public static void main(String[] args) {
        Number n1 = new Number();  // Tạo đối tượng Number đầu tiên và gán cho biến n1
        Number n2 = new Number();  // Tạo đối tượng Number thứ hai và gán cho biến n2

        n1.i = 2;  // Đặt giá trị của thuộc tính 'i' của đối tượng n1 là 2
        n2.i = 5;  // Đặt giá trị của thuộc tính 'i' của đối tượng n2 là 5

        n1.i = n2.i;  // Gán giá trị của thuộc tính 'i' của n2 (là 5) cho thuộc tính 'i' của n1. Bây giờ n1.i là 5

        n2.i = 10;  // Thay đổi giá trị của thuộc tính 'i' của đối tượng n2 thành 10. Giá trị của n2.i thay đổi, nhưng giá trị của n1.i không thay đổi

        // In ra kết quả để kiểm tra
        System.out.println("n1.i = " + n1.i);  // In ra giá trị của n1.i, sẽ là 5
    }
}
