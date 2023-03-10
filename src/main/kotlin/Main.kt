import java.lang.NumberFormatException
import kotlin.system.exitProcess

fun main(){
    var database_1: Database = Database("localhost", 3306, "main_user", "f-*", "Person Management");
    var dataManager: DataManager = DataManager()
    var eingabe_benutzer = 1

    try{
        database_1.createConnection()

    }catch(e: Exception){
        println("Datenbankverbindung konnte nicht hergestellt werden!")
        exitProcess(0)
    }

    dataManager.initializeAllStudents(database_1)
    dataManager.initializeAllLecturers(database_1)

    do{
        println("\n\nMenü\n__________________________________")
        println("1.- Zeige alle Studenten an")
        println("2.- Zeige alle Dozenten an")
        println("3.- Student einfügen")
        println("4.- Dozent einfügen")
        println("5.- Suche Student")
        println("6.- Suche Dozent")
        println("0.- Beenden")
        println("\nEingabe: ")

        try{
            eingabe_benutzer = readln().toInt()
        }catch(e: NumberFormatException){
            eingabe_benutzer = 500
        }

        when(eingabe_benutzer){
            0 -> println("\nDas Programm wird jetzt beendet ..")
            1 -> dataManager.printAllStudents(database_1)
            2 -> dataManager.printAllLecturers(database_1)
            3 -> dataManager.createStudent(dataManager, database_1)
            4 -> dataManager.createLecturer(dataManager, database_1)
            5 -> dataManager.findStudent()
            6 -> dataManager.findLecturer()
            else -> {
                println("\n\nDu hast eine falsche Eingabe getätigt, bitte versuche es erneut!\n");
            }
        }
    }while(eingabe_benutzer != 0)

    database_1.closeConnection()
}