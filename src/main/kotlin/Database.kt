import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

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

    fun initializeAllStudents(datamanager: DataManager){
        var sql = "SELECT * from Student"
        var preparedStatement: PreparedStatement = connection!!.prepareStatement(sql)

        try{
            var result: ResultSet = preparedStatement.executeQuery()

            while(result.next()){
                var map_person = getPersonDataById(result.getString("person_id").toInt())

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
                datamanager.students.add(new_student)
            }
        }catch(e: Exception){
            println("Failed on fetching data from Result (Table: Person)")
            println(e.message)
        }
    }

    fun initializeAllLecturers(datamanager: DataManager){
        var sql = "SELECT * from Lecturer"
        var preparedStatement: PreparedStatement = connection!!.prepareStatement(sql)

        try{
            var result: ResultSet = preparedStatement.executeQuery()

            while(result.next()){
                var map_person = getPersonDataById(result.getString("person_id").toInt())

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
                datamanager.lecturers.add(new_lecturer)
            }
        }catch(e: Exception){
            println("Failed on fetching data from Result (Table: Person)")
            println(e.message)
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

    fun insertStudent(name: String, last_name: String, address: String, postcode: Int, city: String, gender: String, birthday: String, telephone_number: String, email: String, modul: String){
        var sql_person = "Insert into Person values(1, $name, $last_name, $a)"
        var preparedStatement_person: PreparedStatement = connection!!.prepareStatement(sql_person)
    }

    fun closeConnection(){
        connection!!.close()
    }

}