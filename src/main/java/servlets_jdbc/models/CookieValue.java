package servlets_jdbc.models;


public class CookieValue {

    private String value;

    private String user;

    public CookieValue(String value, String user) {
        this.value = value;
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String value;

        private String user;

        public Builder user(String person) {
            this.user = person;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public CookieValue build() {
            return new CookieValue(this.value, this.user);
        }
    }
}
