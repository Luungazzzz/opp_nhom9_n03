
public class App {
    public static void main(String[] args) {
        Book myBook = new Book("Khong diet khong sinh dung so hai", "Thich Nhat Hanh", 74);
        System.out.println("Book title: " + myBook.title);
        System.out.println("Author: " + myBook.author);
        System.out.println("Number Page: " + myBook.numPages);

        NNCollection collection = new NNCollection();

        collection.insert(new NameNumber("NGA", "22010181"));
        collection.insert(new NameNumber("DUONG", "23345678"));
        System.out.println("MSSV NGA: " + collection.findNumber("NGA"));
        System.out.println("MSSV DUONG: " + collection.findNumber("DUONG"));

        Recursion recursion = new Recursion();
        System.out.println("Factorial: " + recursion.factorial(4));
        System.out.println("Hello");
        Time time = new Time(7, 40, 45);
        System.out.println("Time: " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
    }
}