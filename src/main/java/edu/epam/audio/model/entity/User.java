package edu.epam.audio.model.entity;

//todo: change photo and role to blob and enum
public class User extends Entity {
    private Long userId;
    private String email;
    private String name;
    private String photo;
    private String role;
    private Double bonus;

    public User(){

    }

    public User(Long userId, String email, String name, String photo, String role, Double bonus) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.photo = photo;
        this.role = role;
        this.bonus = bonus;
    }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (photo != null ? !photo.equals(user.photo) : user.photo != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        return bonus != null ? bonus.equals(user.bonus) : user.bonus == null;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (bonus != null ? bonus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", role='" + role + '\'' +
                ", bonus=" + bonus +
                '}';
    }
}
