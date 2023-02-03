class Lecturer: Person{
    var lecturer_id: Int
    var type: String
    var modul: String

    constructor(id: Int, name: String, last_name: String, address: String, postcode: Int, city: String, gender: String, birthday: String, telephone_number: String, email: String, lecturer_id: Int, type: String, modul: String): super(id, name, last_name, address, postcode, city, gender, birthday, telephone_number, email){
        this.lecturer_id = lecturer_id
        this.type = type
        this.modul = modul
    }

    fun printLecturer(counter: Int){
        println("$counter   |  ${spaceGenerator(name, false)}|  ${spaceGenerator(last_name, false)}|  ${spaceGenerator(address, false)}|  ${spaceGenerator(postcode.toString(), false)}|  ${spaceGenerator(city, false)}|  ${spaceGenerator(gender, false)}|  ${spaceGenerator(birthday, false)}|  ${spaceGenerator(telephone_number, false)}|  ${spaceGenerator(email, true)}|  ${spaceGenerator(lecturer_id.toString(), false)}|  ${spaceGenerator(type, false)}|  ${spaceGenerator(modul, false)}")
    }

    fun spaceGenerator(label: String, long: Boolean): String{
        val space = " "
        var newLabel = label
        val difference = 25 - label.length

        if(difference > 0){
            for(i in 1..difference){
                newLabel += space
            }
        }

        if(long){
            newLabel += "          "
        }

        return newLabel
    }
}