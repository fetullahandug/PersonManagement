class Student: Person{
    var student_id: Int
    var modul: String

    constructor(id: Int, name: String, last_name: String, address: String, postcode: Int, city: String, gender: String, birthday: String, telephone_number: String, email: String, student_id: Int, modul: String): super(id, name, last_name, address, postcode, city, gender, birthday, telephone_number, email){
        this.student_id = student_id
        this.modul = modul
    }

    fun printStudent(counter: Int){
        println("$counter   |  ${spaceGenerator(name, false)}|  ${spaceGenerator(last_name, false)}|  ${spaceGenerator(address, false)}|  ${spaceGenerator(postcode.toString(), false)}|  ${spaceGenerator(city, false)}|  ${spaceGenerator(gender, false)}|  ${spaceGenerator(birthday, false)}|  ${spaceGenerator(telephone_number, false)}|  ${spaceGenerator(email, true)}|  ${spaceGenerator(student_id.toString(), false)}|  ${spaceGenerator(modul, false)}")
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