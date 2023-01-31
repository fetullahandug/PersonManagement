open class Person{
    var id: Int
    var name: String
    var last_name: String
    var address: String
    var postcode: Int
    var city: String
    var gender: String
    var birthday: String
    var telephone_number: String
    var email: String

    constructor(id: Int, name: String, last_name: String, address: String, postcode: Int, city: String, gender: String, birthday: String, telephone_number: String, email: String){
        this.id = id
        this.name = name
        this.last_name = last_name
        this.address = address
        this.postcode = postcode
        this.city = city
        this.gender = gender
        this.birthday = birthday
        this.telephone_number = telephone_number
        this.email = email
    }
}