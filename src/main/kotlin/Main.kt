import java.lang.NumberFormatException

fun main(){
    var database_1: Database = Database("localhost", 3306, "main_user", "f-*", "Person Management");
    var dataManager: DataManager = DataManager()
    var eingabe_benutzer = 1

    database_1.createConnection()
    database_1.initializeAllStudents(dataManager)
    database_1.initializeAllLecturers(dataManager)

    do{
        println("Menü\n__________________________________")
        println("1.- Zeige alle Studenten an")
        println("2.- Zeige alle Dozenten an")
        println("3.- Suche Student")
        println("4.- Suche Dozent")
        println("0.- Beenden")

        println("\nEingabe: ")

        try{
            eingabe_benutzer = readln().toInt()
        }catch(e: NumberFormatException){
            eingabe_benutzer = 500
        }

        when(eingabe_benutzer){
            0 -> println("\nDas Programm wird jetzt beendet ..")
            1 -> dataManager.printAllStudents()
            2 -> dataManager.printAllLecturers()
            else -> {
                println("\n\nDu hast eine falsche Eingabe getätigt, bitte versuche es erneut!\n");
            }
        }
    }while(eingabe_benutzer != 0)

    database_1.closeConnection()
}