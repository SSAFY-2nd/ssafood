export default class User {
    constructor (username, email, password, confirmPassword, age, gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.age = age;
        this.gender = gender;
    }
}