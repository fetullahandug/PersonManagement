class Lecturer: Person{
    var lecturer_id: Int
    var type: String
    var modul: String

    constructor(id: Int, name: String, last_name: String, address: String, postcode: Int, city: String, gender: String, birthday: String, telephone_number: String, email: String, lecturer_id: Int, type: String, modul: String): super(id, name, last_name, address, postcode, city, gender, birthday, telephone_number, email){
        this.lecturer_id = lecturer_id
        this.type = type
        this.modul = modul
    }

    fun printLecturer(){
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
        println("Dozent-Nr.: $lecturer_id")
        println("Dozent-Typ: $type")
        println("Modul: $modul")
    }
}