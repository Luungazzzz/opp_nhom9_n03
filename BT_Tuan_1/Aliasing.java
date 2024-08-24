package oop_group9_week1;

// Định nghĩa lớp Aliasing
public class Aliasing {
    // Định nghĩa lớp Number
    public static class Number {
        public int i; // Thuộc tính i của lớp Number
    }

    // Phương thức static f nhận đối tượng Number làm tham số và thay đổi thuộc tính i của đối tượng đó
    static void f(Number m) {
        m.i = 15; // Thay đổi giá trị thuộc tính i của đối tượng m thành 15
    }

    public static void main(String[] args) {
        // Tạo đối tượng Number
        Number n = new Number();
        n.i = 14; // Gán giá trị 14 cho thuộc tính i của đối tượng n

        // Gọi phương thức f và truyền đối tượng n vào
        f(n); // Sau khi gọi phương thức f, thuộc tính i của n sẽ thay đổi thành 15

        // In giá trị thuộc tính i của đối tượng n
        System.out.println(n.i); // Kết quả sẽ là 15
    }
}
