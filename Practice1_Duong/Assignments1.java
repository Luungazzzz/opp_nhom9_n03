
public class Assignments1 {
	public static class Number{
		public int i; //đối tượng i của lớp Number
	}
	public static void main(String[] args) {
		//tạo 2 đối tượng Number
		Number n1 = new Number(); 
		Number n2 = new Number();
		
		n1.i = 2;//gán giá triij cho i của đối tượng n1, n2
		n2.i = 5;
		n1.i = n2.i; //gán giá trị thuộc tính i của n1 thành i của n2
		n2.i = 10; //thay đổi giá trị của i n2
		System.out.println(n1.i);//trả về kết quả n1.i = 5	
	}
}


