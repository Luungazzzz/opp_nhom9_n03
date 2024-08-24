class Number {
    int i;  // Khai báo biến thành viên 'i' của lớp Number
}

class Assignment1 {
    public static void main(String[] args) {
        Number n1 = new Number();  // Tạo đối tượng Number đầu tiên và gán cho n1
        Number n2 = new Number();  // Tạo đối tượng Number thứ hai và gán cho n2

        n1.i = 2;  // Gán giá trị 2 cho thuộc tính 'i' của n1
        n2.i = 5;  // Gán giá trị 5 cho thuộc tính 'i' của n2

        n1.i = n2.i;  // Gán giá trị của n2.i (là 5) cho n1.i. Bây giờ n1.i là 5.

        n2.i = 10;  // Gán giá trị 10 cho n2.i. Bây giờ n2.i là 10, nhưng n1.i vẫn là 5.

        // Tại thời điểm này, giá trị của n1.i vẫn là 5.
        System.out.println("n1.i = " + n1.i);  // Dòng này sẽ in ra: n1.i = 5
    }
}
