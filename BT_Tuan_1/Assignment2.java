package oop_group9_week1;

public class Assignment2 {
    public static class Number{
        public int i;
    }

    public static void main(String[] args) {
        // Tạo hai đối tượng NumberCustom
        Number n1 = new Number();
        Number n2 = new Number();

        // Gán giá trị cho thuộc tính i của n1 và n2
        n1.i = 2;
        n2.i = 5;

        // Gán n1 trỏ tới đối tượng mà n2 đang trỏ tới
        n1 = n2;

        // Thay đổi giá trị thuộc tính i của đối tượng mà n2 đang trỏ tới
        n2.i = 10; // Do n1 đã trỏ tới cùng đối tượng với n2, nên n1.i cũng thay đổi thành 10

        // Thay đổi giá trị thuộc tính i của đối tượng mà n2 đang trỏ tới
        n1.i = 20; // Vì n1 và n2 đều trỏ tới cùng một đối tượng, nên n2.i cũng thay đổi thành 20

        // In kết quả ra màn hình để kiểm tra
        System.out.println("n1.i = " + n1.i); // Kết quả sẽ là 20
        System.out.println("n2.i = " + n2.i); // Kết quả sẽ là 20
    }
}
