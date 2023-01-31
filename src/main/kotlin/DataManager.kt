import java.sql.PreparedStatement
import java.sql.ResultSet

class DataManager{
    var students: MutableList<Student> = mutableListOf()
    var lecturers: MutableList<Lecturer> = mutableListOf()

    fun initializeAllStudents(database: Database){
        var sql = "SELECT * from Student"
        var preparedStatement: PreparedStatement = database.connection!!.prepareStatement(sql)

        try{
            var result: ResultSet = preparedStatement.executeQuery()

            while(result.next()){
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
        }catch(e: Exception){
            println("Failed on fetching data from Result (Table: Person)")
            println(e.message)
        }
    }

    fun initializeAllLecturers(database: Database){
        var sql = "SELECT * from Lecturer"
        var preparedStatement: PreparedStatement = database.connection!!.prepareStatement(sql)

        try{
            var result: ResultSet = preparedStatement.executeQuery()

            while(result.next()){
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
        }catch(e: Exception){
            println("Failed on fetching data from Result (Table: Person)")
            println(e.message)
        }
    }

    fun printAllStudents(){
        for(student in students){
            println()
            student.printStudent()
        }
        println("\nKlicke Enter um fortzufahren ..")
        readln()
    }

    fun printAllLecturers(){
        for(lecturer in lecturers){
            println()
            lecturer.printLecturer()
        }
        println("\nKlicke Enter um fortzufahren ..")
        readln()
    }

    fun createStudent(datamanager: DataManager, database: Database){
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

        do{
            println("Name: ")
            name = readln()

            if(name.isNotBlank() && !containsNumbers(name)){
                data_set.put("NAME", name)
                break
            }else{
                println("Bitte gebe einen Namen ein der keine Zahlen beinhaltet ..!")
                continue
            }
        }while(true)

        do{
            println("Nachname: ")
            last_name = readln()

            if(name.isNotBlank() && !containsNumbers(last_name)){
                data_set.put("LAST_NAME", last_name)
                break
            }else{
                println("Bitte gebe einen Namen ein der keine Zahlen beinhaltet ..!")
                continue
            }
        }while(true)

        do{
            println("Adresse: ")
            address = readln()

            if(name.isNotBlank()){
                data_set.put("ADDRESS", address)
                break
            }else{
                println("Bitte gebe eine korrekte Adresse ein ..!")
                continue
            }
        }while(true)

        do{
            println("Postleitzahl: ")
            postcode = readln()

            if(postcode.isNotBlank() && containsNumbers(postcode) && !containsChars(postcode)){
                data_set.put("POSTCODE", postcode)
                break
            }else{
                println("Bitte gebe einen korrekte Postleitzahl ein.\nDiese darf keine Buchstaben oder Sonderzeichen enthalten!")
                continue
            }
        }while(true)

        do{
            println("Ort: ")
            city = readln()

            if(city.isNotBlank() && !containsNumbers(city)){
                data_set.put("CITY", city)
                break
            }else{
                println("Bitte gebe einen korrekten Ort ein.\nDiese darf keine Zahlen enthalten!")
                continue
            }
        }while(true)

        do{
            println("Geschlecht (M/W): ")
            gender = readln()

            if(gender.isNotBlank() && !containsNumbers(gender)){
                data_set.put("GENDER", gender)
                break
            }else{
                println("Bitte gebe entweder M oder W an!")
                continue
            }
        }while(true)

        do{
            println("Geburtsdatum (TT/MM/JJJJ): ")
            birthday = readln()

            if(birthday.isNotBlank()){
                data_set.put("BIRTHDAY", birthday)
                break
            }else{
                println("Bitte gebe ein gültiges Geburtsdatum ein!")
                continue
            }
        }while(true)

        do{
            println("Telefonnummer: (Ohne Vorwahl): ")
            telephone_number = readln()

            if(telephone_number.isNotBlank() && !containsChars(telephone_number)){
                data_set.put("TELEPHONE_NUMBER", telephone_number)
                break
            }else{
                println("Bitte gebe eine gültige Telefonnummer ein!")
                continue
            }
        }while(true)

        do{
            println("Email: ")
            email = readln()

            if(email.isNotBlank()){
                data_set.put("EMAIL", email)
                break
            }else{
                println("Bitte gebe eine gültige Email-Adresse ein!")
                continue
            }
        }while(true)

        do{
            println("Modul: ")
            module = readln()

            if(module.isNotBlank()){
                data_set.put("MODULE", module)
                break
            }else{
                println("Bitte gebe ein gültiges Modul ein!")
                continue
            }
        }while(true)

        //Hier muss noch das Objekt der aktuellen Liste angehängt werden oder irgendwo anders die Daten nochmal initialisiert werden.
        database.insertStudent(data_set)
        initializeAllStudents(database)
    }

    fun createLecturer(datamanager: DataManager, database: Database){
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

        do{
            println("Name: ")
            name = readln()

            if(name.isNotBlank() && !containsNumbers(name)){
                data_set.put("NAME", name)
                break
            }else{
                println("Bitte gebe einen Namen ein der keine Zahlen beinhaltet ..!")
                continue
            }
        }while(true)

        do{
            println("Nachname: ")
            last_name = readln()

            if(name.isNotBlank() && !containsNumbers(last_name)){
                data_set.put("LAST_NAME", last_name)
                break
            }else{
                println("Bitte gebe einen Namen ein der keine Zahlen beinhaltet ..!")
                continue
            }
        }while(true)

        do{
            println("Adresse: ")
            address = readln()

            if(name.isNotBlank()){
                data_set.put("ADDRESS", address)
                break
            }else{
                println("Bitte gebe eine korrekte Adresse ein ..!")
                continue
            }
        }while(true)

        do{
            println("Postleitzahl: ")
            postcode = readln()

            if(postcode.isNotBlank() && containsNumbers(postcode) && !containsChars(postcode)){
                data_set.put("POSTCODE", postcode)
                break
            }else{
                println("Bitte gebe einen korrekte Postleitzahl ein.\nDiese darf keine Buchstaben oder Sonderzeichen enthalten!")
                continue
            }
        }while(true)

        do{
            println("Ort: ")
            city = readln()

            if(city.isNotBlank() && !containsNumbers(city)){
                data_set.put("CITY", city)
                break
            }else{
                println("Bitte gebe einen korrekten Ort ein.\nDiese darf keine Zahlen enthalten!")
                continue
            }
        }while(true)

        do{
            println("Geschlecht (M/W): ")
            gender = readln()

            if(gender.isNotBlank() && !containsNumbers(gender)){
                data_set.put("GENDER", gender)
                break
            }else{
                println("Bitte gebe entweder M oder W an!")
                continue
            }
        }while(true)

        do{
            println("Geburtsdatum (TT/MM/JJJJ): ")
            birthday = readln()

            if(birthday.isNotBlank()){
                data_set.put("BIRTHDAY", birthday)
                break
            }else{
                println("Bitte gebe ein gültiges Geburtsdatum ein!")
                continue
            }
        }while(true)

        do{
            println("Telefonnummer: (Ohne Vorwahl): ")
            telephone_number = readln()

            if(telephone_number.isNotBlank() && !containsChars(telephone_number)){
                data_set.put("TELEPHONE_NUMBER", telephone_number)
                break
            }else{
                println("Bitte gebe eine gültige Telefonnummer ein!")
                continue
            }
        }while(true)

        do{
            println("Email: ")
            email = readln()

            if(email.isNotBlank()){
                data_set.put("EMAIL", email)
                break
            }else{
                println("Bitte gebe eine gültige Email-Adresse ein!")
                continue
            }
        }while(true)

        do{
            println("Dozent-Typ: ")
            type = readln()

            if(type.isNotBlank() && !containsNumbers(type)){
                data_set.put("TYPE", type)
                break
            }else{
                println("Bitte gebe einen gültigen Dozent-Typen ein!")
                continue
            }
        }while(true)

        do{
            println("Modul: ")
            module = readln()

            if(module.isNotBlank()){
                data_set.put("MODULE", module)
                break
            }else{
                println("Bitte gebe ein gültiges Modul ein!")
                continue
            }
        }while(true)

        //Hier muss noch das Objekt der aktuellen Liste angehängt werden oder irgendwo anders die Daten nochmal initialisiert werden.
        database.insertLecturer(data_set)
        initializeAllLecturers(database)
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

}