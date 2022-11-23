public class Owner extends User {
    public Owner() {
        System.out.println("Owner Created!!");
    }

    @Override
    public String toString() {
        return "Selamat Datang Owner " +  this.getNama();
    }
}
