class Number {
    int i;  // Khai báo biến thành viên 'i' của lớp Number
}

    class PassObject {
    // Phương thức tĩnh nhận một đối tượng Number và thay đổi giá trị của thuộc tính 'i'
    static void f(Number m) {
        m.i = 15;  // Đặt giá trị của thuộc tính 'i' của đối tượng m thành 15
    }

    public static void main(String[] args) {
        Number n = new Number();  // Tạo đối tượng Number và gán cho biến n
        n.i = 14;  // Đặt giá trị của thuộc tính 'i' của n là 14

        f(n);  // Gọi phương thức f với đối tượng n. Điều này sẽ thay đổi giá trị của n.i thành 15

        // In ra kết quả để kiểm tra
        System.out.println("n.i = " + n.i);  // Sẽ in ra: n.i = 15
    }
}
