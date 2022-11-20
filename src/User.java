public class User {
    private String nama;
    private int id;
    private String alamat;

    private int uLevel;

    public User() {
        System.out.println("User Created!!");
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getuLevel() {
        return uLevel;
    }

    public void setuLevel(int uLevel) {
        this.uLevel = uLevel;
    }
}
