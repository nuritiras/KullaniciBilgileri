package tr.com.nuritiras.kullanicibilgileri;

public class Kullanici {
    private  String kullaniciId;
    private  String KullaniciIsmi;
    private  String kullaniciEmail;
    private  String kullaniciParola;

    public Kullanici() {
    }

    public Kullanici(String kullaniciId, String kullaniciIsmi, String kullaniciEmail, String kullaniciParola) {
        this.kullaniciId = kullaniciId;
        KullaniciIsmi = kullaniciIsmi;
        this.kullaniciEmail = kullaniciEmail;
        this.kullaniciParola = kullaniciParola;
    }

    public String getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(String kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getKullaniciIsmi() {
        return KullaniciIsmi;
    }

    public void setKullaniciIsmi(String kullaniciIsmi) {
        KullaniciIsmi = kullaniciIsmi;
    }

    public String getKullaniciEmail() {
        return kullaniciEmail;
    }

    public void setKullaniciEmail(String kullaniciEmail) {
        this.kullaniciEmail = kullaniciEmail;
    }

    public String getKullaniciParola() {
        return kullaniciParola;
    }

    public void setKullaniciParola(String kullaniciParola) {
        this.kullaniciParola = kullaniciParola;
    }
}
