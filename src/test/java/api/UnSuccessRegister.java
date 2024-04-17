package api;

public class UnSuccessRegister {
    private String error;

    public UnSuccessRegister(String error) {
        this.error = error;
    }

    public UnSuccessRegister() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
