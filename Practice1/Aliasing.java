package OOP_GROUP9;

public class Aliasing {
	public static class Number{
		public int i; //đối tượng i của lớp Number
	}
	public static void f(Number m) { //nhận Number làm tham số và thay đổi thuộc tính i của đối tượng
		m.i = 15;//thay đổi thuộc tính i của m
	}
	public static void main(String[] args) {
		//tạo Number
		Number n = new Number();
		n.i = 14; //gán giá trị cho thộc tính i của đối tượng n	
		//gọi f và truyền đối tượng n
		f(n); 
		
		System.out.println(n.i); //kết quả thu được n.i =15
	}

}
