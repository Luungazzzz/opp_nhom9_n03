package OOP_GROUP9;


public class Assignments2 {
	public static class Number{
		public int i; //đối tượng i của lớp Number
	}
	public static void main(String[] args) {
		 Number n1 = new Number();
		 Number n2 = new Number();
		 //gán giá trị cho thuộc tính i của n1 và n2
		 n1.i = 2;
		 n2.i = 5;
		 //gán n = n2
		 n1 = n2;
		 n2.i = 10;// what is n1.i?
		 n1.i = 20;// what is n2.i? 
		 System.out.println(n2.i);
		 System.out.println(n1.i);
		 //kết quả thu được n1.i = n2.i =20 do cuối cùng n1.i được gán bằng 20 mà n1 được gán với n2
		 
		 }
		}


