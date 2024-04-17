package api;

public class SuccessRegister {
    private Integer id;
    private String token;

    public SuccessRegister(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public SuccessRegister() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
