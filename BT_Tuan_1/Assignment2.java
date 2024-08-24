class Number {
    int i;  // Khai báo biến thành viên 'i' của lớp Number
}

class Assignment2 {
    public static void main(String[] args) {
        Number n1 = new Number();  // Khởi tạo đối tượng Number cho n1
        Number n2 = new Number();  // Khởi tạo đối tượng Number cho n2

        n1.i = 2;  // Đặt giá trị n1.i là 2
        n2.i = 5;  // Đặt giá trị n2.i là 5

        n1 = n2;   // n1 và n2 giờ cùng tham chiếu đến cùng một đối tượng Number

        n2.i = 10; // Thay đổi giá trị n2.i thành 10. Vì n1 và n2 cùng tham chiếu đến cùng một đối tượng, nên n1.i cũng sẽ là 10.

        n1.i = 20; // Thay đổi giá trị n1.i thành 20. Tương tự, n2.i cũng sẽ là 20 vì chúng cùng tham chiếu đến một đối tượng.

        // In ra kết quả để kiểm tra
        System.out.println("n1.i = " + n1.i);  // Sẽ in ra: n1.i = 20
        System.out.println("n2.i = " + n2.i);  // Sẽ in ra: n2.i = 20
    }
}
