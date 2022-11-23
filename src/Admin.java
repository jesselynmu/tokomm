public class Admin extends User{
    public Admin() {
        System.out.println("Admin Created!!");
    }

    @Override
    public String toString() {
        return "Selamat Datang Admin " +  this.getNama();
    }
}
