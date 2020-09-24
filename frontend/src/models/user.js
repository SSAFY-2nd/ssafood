export default class User {
    constructor (username, email, password, confirmPassword, age, sex) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.age = age;
        this.sex = sex;
    }
}