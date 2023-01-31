class Student: Person{
    var student_id: Int
    var modul: String

    constructor(id: Int, name: String, last_name: String, address: String, postcode: Int, city: String, gender: String, birthday: String, telephone_number: String, email: String, student_id: Int, modul: String): super(id, name, last_name, address, postcode, city, gender, birthday, telephone_number, email){
        this.student_id = student_id
        this.modul = modul
    }

    fun printStudent(){
        println()
        println("Name: ${super.name}")
        println("Nachname: ${super.last_name}")
        println("Adresse: ${super.address}")
        println("Postleitzahl: ${super.postcode}")
        println("Ort: ${super.city}")
        println("Geschlecht: ${super.gender}")
        println("Geburtsdatum: ${super.birthday}")
        println("Telefonnummer: ${super.telephone_number}")
        println("Email: ${super.email}")
        println("Student-Nr.: $student_id")
        println("Modul: $modul")
    }

}