package giuaki;

public class ContinuousSignal implements giuaki.Signal {
    private double bienDo;
    private double buocSong;

    public ContinuousSignal(double bienDo, double buocSong) {
        this.bienDo = bienDo;
        this.buocSong = buocSong;
    }

    // Hiển thị tín hiệu liên tục
    @Override
    public void displaySignal() {
        System.out.println("Tín hiệu liên tục với: Biên độ: " + bienDo + ", bước sóng: " + buocSong);
    }
}
