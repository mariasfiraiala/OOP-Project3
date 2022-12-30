package input;

public class UserInput {
    private CredentialsInput credentials;
    public UserInput() { }
    public UserInput(final UserInput user) {
        this.credentials = new CredentialsInput(user.credentials);
    }

    public final CredentialsInput getCredentials() {
        return credentials;
    }

    public final void setCredentials(final CredentialsInput credentials) {
        this.credentials = credentials;
    }
}
