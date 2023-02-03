import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import javax.xml.crypto.Data

class DataManager {
    var students: MutableList<Student> = mutableListOf()
    var lecturers: MutableList<Lecturer> = mutableListOf()

    fun initializeAllStudents(database: Database) {
        students.clear()
        var sql = "SELECT * from Student"
        var preparedStatement: PreparedStatement = database.connection!!.prepareStatement(sql)

        try {
            var result: ResultSet = preparedStatement.executeQuery()

            while (result.next()) {
                var map_person = database.getPersonDataById(result.getString("person_id").toInt())

                var new_student = Student(
                    map_person.get("ID")!!.toInt(),
                    map_person.get("NAME").toString(),
                    map_person.get("LAST_NAME").toString(),
                    map_person.get("ADDRESS").toString(),
                    map_person.get("POSTCODE")!!.toInt(),
                    map_person.get("CITY").toString(),
                    map_person.get("GENDER").toString(),
                    map_person.get("BIRTHDAY").toString(),
                    map_person.get("TELEPHONE_NUMBER").toString(),
                    map_person.get("EMAIL").toString(),
                    result.getString("id").toInt(),
                    result.getString("module")
                )
                students.add(new_student)
            }
        } catch (e: Exception) {
            println("Failed on fetching data from Result (Table: Person)")
            println(e.message)
        }
    }

    fun initializeAllLecturers(database: Database) {
        lecturers.clear()
        var sql = "SELECT * from Lecturer"
        var preparedStatement: PreparedStatement = database.connection!!.prepareStatement(sql)

        try {
            var result: ResultSet = preparedStatement.executeQuery()

            while (result.next()) {
                var map_person = database.getPersonDataById(result.getString("person_id").toInt())

                var new_lecturer = Lecturer(
                    map_person.get("ID")!!.toInt(),
                    map_person.get("NAME").toString(),
                    map_person.get("LAST_NAME").toString(),
                    map_person.get("ADDRESS").toString(),
                    map_person.get("POSTCODE")!!.toInt(),
                    map_person.get("CITY").toString(),
                    map_person.get("GENDER").toString(),
                    map_person.get("BIRTHDAY").toString(),
                    map_person.get("TELEPHONE_NUMBER").toString(),
                    map_person.get("EMAIL").toString(),
                    result.getString("id").toInt(),
                    result.getString("type").toString(),
                    result.getString("module").toString()
                )
                lecturers.add(new_lecturer)
            }
        } catch (e: Exception) {
            println("Failed on fetching data from Result (Table: Person)")
            println(e.message)
        }
    }

    fun printAllStudents(database: Database) {
        println()
        println("STUDENTEN -")
        println()
        println(
            "Nr. |  ${spaceGenerator("Name", false)}|  ${
                spaceGenerator(
                    "Nachname",
                    false
                )
            }|  ${spaceGenerator("Adresse", false)}|  ${spaceGenerator("Postleitzahl", false)}|  ${
                spaceGenerator(
                    "Ort",
                    false
                )
            }|  ${spaceGenerator("Geschlecht", false)}|  ${
                spaceGenerator(
                    "Geburtsdatum",
                    false
                )
            }|  ${spaceGenerator("Telefonnummer", false)}|  ${
                spaceGenerator(
                    "Email",
                    true
                )
            }|  ${spaceGenerator("Studenten-ID", false)}|  ${spaceGenerator("Modul", false)}"
        )
        println("________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________")
        println()

        var counter = 1

        for (student in students) {
            student.printStudent(counter)
            counter++
        }

        println("\nZum editieren eines Datensatzes: edit [Nr. des Datensatzes]\nZum löschen eines Datensatzes delete [Nr. des Datensatzes]\n\nUm zurückkehren in das Hauptmenü -> Leere Eingabe")

        var eingabe = readln()
        var result: MutableMap<String, Boolean> = mutableMapOf()

        when {
            eingabe.contains("edit") -> {
                result.put("EDIT", updateStudent(eingabe, database))
                initializeAllStudents(database)
            }

            eingabe.contains("delete") -> {
                result.put("DELETE", deleteStudent(eingabe, database))
                initializeAllStudents(database)
            }
        }

        if (result.get("EDIT") == true) {
            println("\nDatensatz erfolgreich abgeändert!")
        } else if (result.get("DELETE") == true) {
            println("\nDatensatz erfolgreich gelöscht!")
        } else if(eingabe == ""){

        } else {
            println("\nEin Fehler ist aufgetreten!\nBitte checken Sie die Datensätze auf ungewollte Änderungen!")
        }
    }

    fun printAllLecturers(database: Database) {
        println()
        println("DOZENTEN -")
        println()
        println(
            "Nr. |  ${spaceGenerator("Name", false)}|  ${
                spaceGenerator(
                    "Nachname",
                    false
                )
            }|  ${spaceGenerator("Adresse", false)}|  ${spaceGenerator("Postleitzahl", false)}|  ${
                spaceGenerator(
                    "Ort",
                    false
                )
            }|  ${spaceGenerator("Geschlecht", false)}|  ${
                spaceGenerator(
                    "Geburtsdatum",
                    false
                )
            }|  ${spaceGenerator("Telefonnummer", false)}|  ${spaceGenerator("Email", true)}|  ${
                spaceGenerator(
                    "Dozent-ID",
                    false
                )
            }|  ${spaceGenerator("Dozent-Typ", false)}|  ${spaceGenerator("Modul", false)}"
        )
        println("________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________")
        println()

        var counter = 1

        for (lecturer in lecturers) {
            lecturer.printLecturer(counter)
            counter++
        }

        println("\nZum editieren eines Datensatzes: edit [Nr. des Datensatzes]\nZum löschen eines Datensatzes delete [Nr. des Datensatzes]\n\nUm zurückkehren in das Hauptmenü -> Leere Eingabe")
        var eingabe = readln()

        var result: MutableMap<String, Boolean> = mutableMapOf()
        when {
            eingabe.contains("edit") -> {
                result.put("EDIT", updateLecturer(eingabe, database))
                initializeAllLecturers(database)
            }

            eingabe.contains("delete") -> {
                result.put("DELETE", deleteLecturer(eingabe, database))
                initializeAllLecturers(database)
            }
        }

        if (result.get("EDIT") == true) {
            println("\nDatensatz erfolgreich abgeändert!")
        } else if (result.get("DELETE") == true) {
            println("\nDatensatz erfolgreich gelöscht!")
        } else if(eingabe == ""){

        } else {
            println("\nEin Fehler ist aufgetreten!\nBitte checken Sie die Datensätze auf ungewollte Änderungen!")
        }
    }

    fun createStudent(datamanager: DataManager, database: Database) {
        var id: Int
        var name: String
        var last_name: String
        var address: String
        var postcode: String
        var city: String
        var gender: String
        var birthday: String
        var telephone_number: String
        var email: String
        var module: String

        var data_set: MutableMap<String, String> = mutableMapOf()

        println("STUDENT EINFÜGEN\n_________________________")
        var input_control = true;

        do {
            println("Name: ")
            name = readln()

            if (name.isNotBlank() && !containsNumbers(name)) {
                data_set.put("NAME", name)
                break
            } else {
                println("Bitte gebe einen Namen ein der keine Zahlen beinhaltet ..!")
                continue
            }
        } while (true)

        do {
            println("Nachname: ")
            last_name = readln()

            if (name.isNotBlank() && !containsNumbers(last_name)) {
                data_set.put("LAST_NAME", last_name)
                break
            } else {
                println("Bitte gebe einen Namen ein der keine Zahlen beinhaltet ..!")
                continue
            }
        } while (true)

        do {
            println("Adresse: ")
            address = readln()

            if (name.isNotBlank()) {
                data_set.put("ADDRESS", address)
                break
            } else {
                println("Bitte gebe eine korrekte Adresse ein ..!")
                continue
            }
        } while (true)

        do {
            println("Postleitzahl: ")
            postcode = readln()

            if (postcode.isNotBlank() && containsNumbers(postcode) && !containsChars(postcode)) {
                data_set.put("POSTCODE", postcode)
                break
            } else {
                println("Bitte gebe einen korrekte Postleitzahl ein.\nDiese darf keine Buchstaben oder Sonderzeichen enthalten!")
                continue
            }
        } while (true)

        do {
            println("Ort: ")
            city = readln()

            if (city.isNotBlank() && !containsNumbers(city)) {
                data_set.put("CITY", city)
                break
            } else {
                println("Bitte gebe einen korrekten Ort ein.\nDiese darf keine Zahlen enthalten!")
                continue
            }
        } while (true)

        do {
            println("Geschlecht (M/W): ")
            gender = readln()

            if (gender.isNotBlank() && !containsNumbers(gender)) {
                data_set.put("GENDER", gender)
                break
            } else {
                println("Bitte gebe entweder M oder W an!")
                continue
            }
        } while (true)

        do {
            println("Geburtsdatum (TT/MM/JJJJ): ")
            birthday = readln()

            if (birthday.isNotBlank()) {
                data_set.put("BIRTHDAY", birthday)
                break
            } else {
                println("Bitte gebe ein gültiges Geburtsdatum ein!")
                continue
            }
        } while (true)

        do {
            println("Telefonnummer: (Ohne Vorwahl): ")
            telephone_number = readln()

            if (telephone_number.isNotBlank() && !containsChars(telephone_number)) {
                data_set.put("TELEPHONE_NUMBER", telephone_number)
                break
            } else {
                println("Bitte gebe eine gültige Telefonnummer ein!")
                continue
            }
        } while (true)

        do {
            println("Email: ")
            email = readln()

            if (email.isNotBlank()) {
                data_set.put("EMAIL", email)
                break
            } else {
                println("Bitte gebe eine gültige Email-Adresse ein!")
                continue
            }
        } while (true)

        do {
            println("Modul: ")
            module = readln()

            if (module.isNotBlank()) {
                data_set.put("MODULE", module)
                break
            } else {
                println("Bitte gebe ein gültiges Modul ein!")
                continue
            }
        } while (true)

        //Hier muss noch das Objekt der aktuellen Liste angehängt werden oder irgendwo anders die Daten nochmal initialisiert werden.
        database.insertStudent(data_set)
        initializeAllStudents(database)
    }

    fun createLecturer(datamanager: DataManager, database: Database) {
        var id: Int
        var name: String
        var last_name: String
        var address: String
        var postcode: String
        var city: String
        var gender: String
        var birthday: String
        var telephone_number: String
        var email: String
        var type: String
        var module: String

        var data_set: MutableMap<String, String> = mutableMapOf()

        println("Dozent EINFÜGEN\n_________________________")
        var input_control = true;

        do {
            println("Name: ")
            name = readln()

            if (name.isNotBlank() && !containsNumbers(name)) {
                data_set.put("NAME", name)
                break
            } else {
                println("Bitte gebe einen Namen ein der keine Zahlen beinhaltet ..!")
                continue
            }
        } while (true)

        do {
            println("Nachname: ")
            last_name = readln()

            if (name.isNotBlank() && !containsNumbers(last_name)) {
                data_set.put("LAST_NAME", last_name)
                break
            } else {
                println("Bitte gebe einen Namen ein der keine Zahlen beinhaltet ..!")
                continue
            }
        } while (true)

        do {
            println("Adresse: ")
            address = readln()

            if (name.isNotBlank()) {
                data_set.put("ADDRESS", address)
                break
            } else {
                println("Bitte gebe eine korrekte Adresse ein ..!")
                continue
            }
        } while (true)

        do {
            println("Postleitzahl: ")
            postcode = readln()

            if (postcode.isNotBlank() && containsNumbers(postcode) && !containsChars(postcode)) {
                data_set.put("POSTCODE", postcode)
                break
            } else {
                println("Bitte gebe einen korrekte Postleitzahl ein.\nDiese darf keine Buchstaben oder Sonderzeichen enthalten!")
                continue
            }
        } while (true)

        do {
            println("Ort: ")
            city = readln()

            if (city.isNotBlank() && !containsNumbers(city)) {
                data_set.put("CITY", city)
                break
            } else {
                println("Bitte gebe einen korrekten Ort ein.\nDiese darf keine Zahlen enthalten!")
                continue
            }
        } while (true)

        do {
            println("Geschlecht (M/W): ")
            gender = readln()

            if (gender.isNotBlank() && !containsNumbers(gender)) {
                data_set.put("GENDER", gender)
                break
            } else {
                println("Bitte gebe entweder M oder W an!")
                continue
            }
        } while (true)

        do {
            println("Geburtsdatum (TT/MM/JJJJ): ")
            birthday = readln()

            if (birthday.isNotBlank()) {
                data_set.put("BIRTHDAY", birthday)
                break
            } else {
                println("Bitte gebe ein gültiges Geburtsdatum ein!")
                continue
            }
        } while (true)

        do {
            println("Telefonnummer: (Ohne Vorwahl): ")
            telephone_number = readln()

            if (telephone_number.isNotBlank() && !containsChars(telephone_number)) {
                data_set.put("TELEPHONE_NUMBER", telephone_number)
                break
            } else {
                println("Bitte gebe eine gültige Telefonnummer ein!")
                continue
            }
        } while (true)

        do {
            println("Email: ")
            email = readln()

            if (email.isNotBlank()) {
                data_set.put("EMAIL", email)
                break
            } else {
                println("Bitte gebe eine gültige Email-Adresse ein!")
                continue
            }
        } while (true)

        do {
            println("Dozent-Typ: ")
            type = readln()

            if (type.isNotBlank() && !containsNumbers(type)) {
                data_set.put("TYPE", type)
                break
            } else {
                println("Bitte gebe einen gültigen Dozent-Typen ein!")
                continue
            }
        } while (true)

        do {
            println("Modul: ")
            module = readln()

            if (module.isNotBlank()) {
                data_set.put("MODULE", module)
                break
            } else {
                println("Bitte gebe ein gültiges Modul ein!")
                continue
            }
        } while (true)

        //Hier muss noch das Objekt der aktuellen Liste angehängt werden oder irgendwo anders die Daten nochmal initialisiert werden.
        database.insertLecturer(data_set)
        initializeAllLecturers(database)
    }

    fun findStudent() {
        println("Suche Student\n________________________________")
        println("Bitte gebe einen Vornamen ein:")
        var name = readln()
        println("Bitte gebe einen Nachnamen ein:")
        var lastname = readln()

        var students_existing = false

        println("\nGefundene/r Student/en\n_____________________________________")

        for (student in students) {
            if (student.name == name && student.last_name == lastname) {
                println("Name: ${student.name}")
                println("Nachname: ${student.last_name}")
                println("Adresse: ${student.address}")
                println("Postleitzahl: ${student.postcode}")
                println("Ort: ${student.city}")
                println("Geschlecht: ${student.gender}")
                println("Geburtstag: ${student.birthday}")
                println("Telefonnummer: ${student.telephone_number}")
                println("Email: ${student.email}")
                println("Studenten-Id.: ${student.student_id}")
                println("Modul: ${student.modul}")

                students_existing = true
            }
        }

        if (!students_existing) {
            println("Keine Studenten gefunden!")
        }

        println("\nEnter zum fortfahren ..")
        readln()
    }

    fun findLecturer() {
        println("Suche Dozent\n________________________________")
        println("Bitte gebe einen Vornamen ein:")
        var name = readln()
        println("Bitte gebe einen Nachnamen ein:")
        var lastname = readln()

        var lecturers_existing = false

        println("\nGefundene/r Dozent/en\n_________________________________________________________________________________________________________________________________________________________________________________________")

        for (lecturer in lecturers) {
            if (lecturer.name == name && lecturer.last_name == lastname) {
                println("Name: ${lecturer.name}")
                println("Nachname: ${lecturer.last_name}")
                println("Adresse: ${lecturer.address}")
                println("Postleitzahl: ${lecturer.postcode}")
                println("Ort: ${lecturer.city}")
                println("Geschlecht: ${lecturer.gender}")
                println("Geburtstag: ${lecturer.birthday}")
                println("Telefonnummer: ${lecturer.telephone_number}")
                println("Email: ${lecturer.email}")
                println("Dozent-Id.: ${lecturer.lecturer_id}")
                println("Modul: ${lecturer.modul}")

                lecturers_existing = true
            }
        }

        /*if(!students_existing){
            println("Keine Studenten gefunden!")
        }*/

        println("\nEnter zum fortfahren ..")
        readln()
    }

    fun updateStudent(operation: String, database: Database): Boolean {

        var operation = operation
        var operationSplitted = operation.split(" ")
        var index: Int
        var new_name: String
        var new_lastname: String
        var new_address: String
        var new_postcode: String
        var new_city: String
        var new_gender: String
        var new_birthday: String
        var new_telephonenumber: String
        var new_email: String
        var new_module: String
        var person_id: Int

        if (operationSplitted[0] == "edit" && !containsChars(operationSplitted[1]) && operationSplitted[1].toInt() > 0 && operationSplitted[1].toInt() <= students.size) {
            index = operationSplitted[1].toInt() - 1
            person_id = students[index].id
        } else {
            println("Du hast eine falsche Eingabe getätigt.\nBitte versuche es erneut!")
            return false
        }

        println("\nStudent bearbeiten")
        println(
            "Nr. |  ${spaceGenerator("Name", false)}|  ${
                spaceGenerator(
                    "Nachname",
                    false
                )
            }|  ${spaceGenerator("Adresse", false)}|  ${spaceGenerator("Postleitzahl", false)}|  ${
                spaceGenerator(
                    "Ort",
                    false
                )
            }|  ${spaceGenerator("Geschlecht", false)}|  ${
                spaceGenerator(
                    "Geburtsdatum",
                    false
                )
            }|  ${spaceGenerator("Telefonnummer", false)}|  ${
                spaceGenerator(
                    "Email",
                    true
                )
            }|  ${spaceGenerator("Studenten-ID", false)}|  ${spaceGenerator("Modul", false)}"
        )
        println("________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________")
        students[index].printStudent(1)

        while (true) {
            println("\nName:  ")
            new_name = readln()

            if (new_name.isBlank()) {
                new_name = students[index].name
                break
            } else if (!containsNumbers(new_name)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        }

        do {
            println("Nachname:  ")
            new_lastname = readln()

            if (new_lastname.isBlank()) {
                new_lastname = students[index].last_name
                break
            } else if (!containsNumbers(new_lastname)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        } while (true)

        do {
            println("Adresse:  ")
            new_address = readln()

            if (new_address.isBlank()) {
                new_address = students[index].address
                break
            } else {
                break
            }
        } while (true)

        do {
            println("Postleitzahl:  ")
            new_postcode = readln()

            if (new_postcode.isBlank()) {
                new_postcode = students[index].postcode.toString()
                break
            } else if (!containsChars(new_postcode)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        } while (true)

        do {
            println("Ort:  ")
            new_city = readln()

            if (new_city.isBlank()) {
                new_city = students[index].city
                break
            } else if (!containsNumbers(new_city)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        } while (true)

        do {
            println("Geschlecht:  ")
            new_gender = readln()

            if (new_gender.isBlank()) {
                new_gender = students[index].gender
                break
            } else if (!containsNumbers(new_gender)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        } while (true)

        do {
            println("Geburtsdatum:  ")
            new_birthday = readln()

            if (new_birthday.isBlank()) {
                new_birthday = students[index].birthday
                break
            } else {
                break
            }
        } while (true)

        do {
            println("Telefonnummer:  ")
            new_telephonenumber = readln()

            if (new_telephonenumber.isBlank()) {
                new_telephonenumber = students[index].telephone_number
                break
            } else if (!containsChars(new_telephonenumber)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        } while (true)

        do {
            println("Email:  ")
            new_email = readln()

            if (new_email.isBlank()) {
                new_email = students[index].email
                break
            } else {
                break
            }
        } while (true)

        do {
            println("Modul:  ")
            new_module = readln()

            if (new_module.isBlank()) {
                new_module = students[index].modul
                break
            } else {
                break
            }
        } while (true)

        var updatedStudent = Student(
            person_id,
            new_name,
            new_lastname,
            new_address,
            new_postcode.toInt(),
            new_city,
            new_gender,
            new_birthday,
            new_telephonenumber,
            new_email,
            students[index].student_id,
            new_module
        )
        database.updateStudent(updatedStudent)

        return true
    }

    fun updateLecturer(operation: String, database: Database): Boolean {

        var operation = operation
        var operationSplitted = operation.split(" ")
        var index: Int
        var new_name: String
        var new_lastname: String
        var new_address: String
        var new_postcode: String
        var new_city: String
        var new_gender: String
        var new_birthday: String
        var new_telephonenumber: String
        var new_email: String
        var new_type: String
        var new_module: String
        var person_id: Int

        if (operationSplitted[0] == "edit" && !containsChars(operationSplitted[1]) && operationSplitted[1].toInt() > 0 && operationSplitted[1].toInt() <= lecturers.size) {
            index = operationSplitted[1].toInt() - 1
            person_id = lecturers[index].id
        } else {
            println("Du hast eine falsche Eingabe getätigt.\nBitte versuche es erneut!")
            return false
        }

        println("\nDozent bearbeiten")
        println(
            "Nr. |  ${spaceGenerator("Name", false)}|  ${
                spaceGenerator(
                    "Nachname",
                    false
                )
            }|  ${spaceGenerator("Adresse", false)}|  ${spaceGenerator("Postleitzahl", false)}|  ${
                spaceGenerator(
                    "Ort",
                    false
                )
            }|  ${spaceGenerator("Geschlecht", false)}|  ${
                spaceGenerator(
                    "Geburtsdatum",
                    false
                )
            }|  ${spaceGenerator("Telefonnummer", false)}|  ${spaceGenerator("Email", true)}|  ${
                spaceGenerator(
                    "Dozent-ID",
                    false
                )
            }|  ${spaceGenerator("Dozent-Typ", false)}|  ${spaceGenerator("Modul", false)}"
        )
        println("________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________")
        lecturers[index].printLecturer(1)

        while (true) {
            println("\nName:  ")
            new_name = readln()

            if (new_name.isBlank()) {
                new_name = lecturers[index].name
                break
            } else if (!containsNumbers(new_name)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        }

        do {
            println("Nachname:  ")
            new_lastname = readln()

            if (new_lastname.isBlank()) {
                new_lastname = lecturers[index].last_name
                break
            } else if (!containsNumbers(new_lastname)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        } while (true)

        do {
            println("Adresse:  ")
            new_address = readln()

            if (new_address.isBlank()) {
                new_address = lecturers[index].address
                break
            } else {
                break
            }
        } while (true)

        do {
            println("Postleitzahl:  ")
            new_postcode = readln()

            if (new_postcode.isBlank()) {
                new_postcode = lecturers[index].postcode.toString()
                break
            } else if (!containsChars(new_postcode)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        } while (true)

        do {
            println("Ort:  ")
            new_city = readln()

            if (new_city.isBlank()) {
                new_city = lecturers[index].city
                break
            } else if (!containsNumbers(new_city)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        } while (true)

        do {
            println("Geschlecht:  ")
            new_gender = readln()

            if (new_gender.isBlank()) {
                new_gender = lecturers[index].gender
                break
            } else if (!containsNumbers(new_gender)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        } while (true)

        do {
            println("Geburtsdatum:  ")
            new_birthday = readln()

            if (new_birthday.isBlank()) {
                new_birthday = lecturers[index].birthday
                break
            } else {
                break
            }
        } while (true)

        do {
            println("Telefonnummer:  ")
            new_telephonenumber = readln()

            if (new_telephonenumber.isBlank()) {
                new_telephonenumber = lecturers[index].telephone_number
                break
            } else if (!containsChars(new_telephonenumber)) {
                break
            } else {
                println("Deine Eingabe war fehlerhaft\nBitte versuche es erneut!")
            }
        } while (true)

        do {
            println("Email:  ")
            new_email = readln()

            if (new_email.isBlank()) {
                new_email = lecturers[index].email
                break
            } else {
                break
            }
        } while (true)

        do {
            println("Dozent-Typ:  ")
            new_type = readln()

            if (new_type.isBlank()) {
                new_type = lecturers[index].type
                break
            } else {
                break
            }
        } while (true)

        do {
            println("Modul:  ")
            new_module = readln()

            if (new_module.isBlank()) {
                new_module = lecturers[index].modul
                break
            } else {
                break
            }
        } while (true)

        var updatedLecturer = Lecturer(
            person_id,
            new_name,
            new_lastname,
            new_address,
            new_postcode.toInt(),
            new_city,
            new_gender,
            new_birthday,
            new_telephonenumber,
            new_email,
            lecturers[index].lecturer_id,
            new_type,
            new_module
        )
        database.updateLecturer(updatedLecturer)

        return true
    }

    fun deleteStudent(operation: String, database: Database): Boolean {
        var index: Int
        var person_id: Int
        var student_id: Int
        var operationSplitted = operation.split(" ")
        var student_deleted: Boolean = false

        if (operationSplitted[0] == "delete" && !containsChars(operationSplitted[1]) && operationSplitted[1].toInt() > 0 && operationSplitted[1].toInt() <= students.size) {
            index = operationSplitted[1].toInt() - 1
            person_id = students[index].id
            student_id = students[index].student_id
        } else {
            println("Du hast eine falsche Eingabe getätigt.\nBitte versuche es erneut!")
            return false
        }

        return database.deleteStudent(operation, person_id, student_id)
    }

    fun deleteLecturer(operation: String, database: Database): Boolean {
        var index: Int
        var person_id: Int
        var lecturer_id: Int
        var operationSplitted = operation.split(" ")
        var lecturer_deleted: Boolean = false

        if (operationSplitted[0] == "delete" && !containsChars(operationSplitted[1]) && operationSplitted[1].toInt() > 0 && operationSplitted[1].toInt() <= lecturers.size) {
            index = operationSplitted[1].toInt() - 1
            person_id = lecturers[index].id
            lecturer_id = lecturers[index].lecturer_id
        } else {
            println("Du hast eine falsche Eingabe getätigt.\nBitte versuche es erneut!")
            return false
        }

        return database.deleteLecturer(operation, person_id, lecturer_id)
    }

    fun containsNumbers(element: String): Boolean{
        for(i in (0..9)){
            if(element.contains(i.toString())) return true
        }
        return false
    }

    fun containsChars(element: String): Boolean{
        var to_proof = element.lowercase()
        for(i in ('a'..'z')){
            if(element.contains(i.toString())) return true
        }
        return false
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