import java.sql.*


class Database{
    var host: String
    var port: Int
    var host_url: String
    var username: String
    var password: String
    var database: String
    var connection: Connection? = null

    constructor(host: String, port: Int, username: String, password: String, database: String){
        this.host = host
        this.port = port
        this.username = username
        this.password = password
        this.database = database
        host_url = "jdbc:mariadb://$host:$port/$database"
    }

    fun printConnection(){
        println()
        println("Host: $host")
        println("Port: $port")
        println("Database-Url: $host_url")
        println("Username: $username")
        println("Password: $password")
        println("Database: $database")
        println("\nTry to connect database ..")
    }

    fun createConnection(){
        try{
            connection = DriverManager.getConnection(host_url, username, password)
        }catch(e: java.sql.SQLException){
            println("Connection to database failed!\nException: ${e.message}")
        }

        println()

        if(connection!!.isValid(0)){
            println("Connection established!\n")
        }else{
            println("Not able to connect to the database!\n")
        }
    }

    fun getPersonDataById(id: Int): MutableMap<String, String>{
        var map_person_infos: MutableMap<String, String> = mutableMapOf()
        var sql_person = "SELECT * from Person where id=$id"
        var preparedStatement_person: PreparedStatement = connection!!.prepareStatement(sql_person)
        var result_from_person = preparedStatement_person.executeQuery()

        while(result_from_person.next()){
            try{
                map_person_infos.put("ID", result_from_person.getString("id"))
                map_person_infos.put("NAME", result_from_person.getString("name"))
                map_person_infos.put("LAST_NAME", result_from_person.getString("lastname"))
                map_person_infos.put("ADDRESS", result_from_person.getString("address"))
                map_person_infos.put("POSTCODE", result_from_person.getString("postcode"))
                map_person_infos.put("CITY", result_from_person.getString("city"))
                map_person_infos.put("GENDER", result_from_person.getString("gender"))
                map_person_infos.put("BIRTHDAY", result_from_person.getString("birthday"))
                map_person_infos.put("TELEPHONE_NUMBER", result_from_person.getString("telephone_number"))
                map_person_infos.put("EMAIL", result_from_person.getString("email"))
            }catch(e: Exception){
                println("Failed on fetch data from Result (Table: Person)")
                println(e.message)
            }
        }
        return map_person_infos
    }

    fun insertStudent(data_set: MutableMap<String, String>){
        var preparedStatement_person: PreparedStatement
        var student_id: Int
        var sql_person = "Insert into Person(name, lastname, address, postcode, city, gender, birthday, telephone_number, email) values" +
                "(" +
                    "'${data_set.get("NAME")}'," +
                    "'${data_set.get("LAST_NAME")}'," +
                    "'${data_set.get("ADDRESS")}'," +
                    "${data_set.get("POSTCODE")}," +
                    "'${data_set.get("CITY")}'," +
                    "'${data_set.get("GENDER")}'," +
                    "'${data_set.get("BIRTHDAY")}'," +
                    "'${data_set.get("TELEPHONE_NUMBER")}'," +
                    "'${data_set.get("EMAIL")}'" +
                ");"

        try{
            var preparedStatement_person: PreparedStatement = connection!!.prepareStatement(sql_person)
            var result_insert_person = preparedStatement_person.executeQuery()
            println("\nSchüler wurde erfolgreich in die Datenbanktabelle Person eingetragen!")
        }catch(exception: Exception){
            println("\nEin Fehler ist aufgetreten, der Schüler konnte nicht in die Datenbanktabelle: Person eingefügt werden!")
            println("Message: ${exception.message}")
        }

        try{
            var sql_get_student_id = "Select id from person where name='${data_set.get("NAME")}' and lastname='${data_set.get("LAST_NAME")}' and birthday='${data_set.get("BIRTHDAY")}'"
            preparedStatement_person = connection!!.prepareStatement(sql_get_student_id)
            var result_student_id = preparedStatement_person.executeQuery()
            result_student_id.next()
            student_id = result_student_id.getString("id").toInt()

            var sql_insert_student_list = "Insert into Student(id, person_id, module) values(null,${student_id},'${data_set.get("MODULE")}')"
            var preparedStatement_student: PreparedStatement = connection!!.prepareStatement(sql_insert_student_list)
            var result_insert_student = preparedStatement_student.executeQuery()
            println("\nSchüler wurde erfolgreich in die Datenbanktabelle: Student eingetragen!")
        }catch(exception: Exception){
            println("\nEin Fehler ist aufgetreten, der Schüler konnte nicht in Datenbanktabelle: Student eingefügt werden!")
            println("Message: ${exception.message}")
        }

    }

    fun insertLecturer(data_set: MutableMap<String, String>){
        var preparedStatement_person: PreparedStatement
        var prepared_statement_lecturer: PreparedStatement
        var lecturer_id: Int
        var sql_person = "Insert into Person(name, lastname, address, postcode, city, gender, birthday, telephone_number, email) values" +
                "(" +
                "'${data_set.get("NAME")}'," +
                "'${data_set.get("LAST_NAME")}'," +
                "'${data_set.get("ADDRESS")}'," +
                "${data_set.get("POSTCODE")}," +
                "'${data_set.get("CITY")}'," +
                "'${data_set.get("GENDER")}'," +
                "'${data_set.get("BIRTHDAY")}'," +
                "'${data_set.get("TELEPHONE_NUMBER")}'," +
                "'${data_set.get("EMAIL")}'" +
                ");"

        try{
            var preparedStatement_person: PreparedStatement = connection!!.prepareStatement(sql_person)
            var result_insert_person = preparedStatement_person.executeQuery()
            println("\nDozent wurde erfolgreich in die Datenbanktabelle Person eingetragen!")
        }catch(exception: Exception){
            println("\nEin Fehler ist aufgetreten, der Dozent konnte nicht in die Datenbanktabelle: Person eingefügt werden!")
            println("Message: ${exception.message}")
        }

        try{
            var sql_get_lecturer_id = "Select id from person where name='${data_set.get("NAME")}' and lastname='${data_set.get("LAST_NAME")}' and birthday='${data_set.get("BIRTHDAY")}'"
            prepared_statement_lecturer = connection!!.prepareStatement(sql_get_lecturer_id)
            var result_lecturer_id = prepared_statement_lecturer.executeQuery()
            result_lecturer_id.next()
            lecturer_id = result_lecturer_id.getString("id").toInt()

            var sql_insert_lecturer_list = "Insert into Lecturer(id, person_id, type, module) values(null,$lecturer_id,'${data_set.get("TYPE")}','${data_set.get("MODULE")}')"
            var preparedStatement_student: PreparedStatement = connection!!.prepareStatement(sql_insert_lecturer_list)
            var result_insert_lecturer = preparedStatement_student.executeQuery()
            println("\nDozent wurde erfolgreich in die Datenbanktabelle: Dozent eingetragen!")
        }catch(exception: Exception){
            println("\nEin Fehler ist aufgetreten, der Dozent konnte nicht in Datenbanktabelle: Dozent eingefügt werden!")
            println("Message: ${exception.message}")
        }

    }

    fun closeConnection(){
        connection!!.close()
    }

}