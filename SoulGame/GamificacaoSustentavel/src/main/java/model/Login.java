package model;

public class Login {

    private String email;
    private String senha;

    public Login() {
    }

    public Login(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public boolean validarLogin() {
        return email != null
                && !email.isBlank()
                && senha != null
                && !senha.isBlank();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}