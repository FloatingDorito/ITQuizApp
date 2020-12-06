package student.inti.itquizapp;

public class User {

    public String username,email,highScore;
    public User(){

    }
    public User(String username,String email,String highScore){
        this.username = username;
        this.email = email;
        this.highScore = highScore;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHighScore() {
        return highScore;
    }

    public void setHighScore(String highScore) {
        this.highScore = highScore;
    }

}
