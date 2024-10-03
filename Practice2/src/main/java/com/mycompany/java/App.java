package com.mycompany.java;

public class App {
    public static void main(String[] args) {
        Book myBook = new Book("Khong diet khong sinh dung so hai", "Thich Nhat Hanh", 68);
        System.out.println("Book title: " + myBook.title);
        System.out.println("Author: " + myBook.author);
        System.out.println("Number Page: " + myBook.numPages);

        NNCollection collection = new NNCollection();

        collection.insert(new NameNumber("Duong", "23010351"));
        collection.insert(new NameNumber("Nga", "22010181"));
        System.out.println("Duong number: " + collection.findNumber("Duong"));
        System.out.println("Nga number: " + collection.findNumber("Nga"));

        Recursion recursion = new Recursion();
        System.out.println("Factorial: " + recursion.factorial(4));
        System.out.println("Hello");
        Time time = new Time(06, 06, 16);
        System.out.println("Time: " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
    }
}