class DataManager{
    var students: MutableList<Student> = mutableListOf()
    var lecturers: MutableList<Lecturer> = mutableListOf()

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

}