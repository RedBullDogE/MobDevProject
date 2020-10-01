package ua.kpi.comsys.iv7213.tasks

import kotlin.math.ceil
import kotlin.random.Random

/**
 * Laboratory work #2
 *
 * Part 1
 *
 * Author: Andrew Krivonos
 * Group: IV-72
 */
fun main() {

    val MAX_POINT = 10
    val LAB_WORK_NUMBER = 9
    val PASSING_POINT = 60

    // Given string with format "Student1 - Group1; Student2 - Group2; ..."
    val studentsStr =
        "Бортнік Василь - ІВ-72; Чередніченко Владислав - ІВ-73; Гуменюк Олександр - ІВ-71; Корнійчук Ольга - ІВ-71; Киба Олег - ІВ-72; Капінус Артем - ІВ-73; Овчарова Юстіна - ІВ-72; Науменко Павло - ІВ-73; Трудов Антон - ІВ-71; Музика Олександр - ІВ-71; Давиденко Костянтин - ІВ-73; Андрющенко Данило - ІВ-71; Тимко Андрій - ІВ-72; Феофанов Іван - ІВ-71; Гончар Юрій - ІВ-73"

    // Task 1
    // Create dictionary:
    // - key is a group name
    // - value is sorted array with students

    val studentsGroups: MutableMap<String, MutableList<String>> = mutableMapOf()

    // Add your code here

    val studentArr = studentsStr.split("; ").toTypedArray()


    studentArr.forEach {
        val temp = it.split(" - ")

        if (!studentsGroups.containsKey(temp[1])) {
            studentsGroups[temp[1]] = mutableListOf(temp[0])
        } else {
            studentsGroups[temp[1]]?.add(temp[0])
        }
    }

    studentsGroups.forEach {
        it.value.sort()
    }

    // TASK 1 RESULT
    println("\nTASK #1")
    println(studentsGroups)

    // Given array with expected max points

    // var points: IntArray = intArrayOf(5, 8, 15, 15, 13, 10, 10, 10, 15)

    // Task 2
    // Create dictionary:
    // - key is a group name
    // - value is dictionary:
    //   - key is student
    //   - value is array with points (fill it with random values, use function `randomValue(maxValue: Int) -> Int` )

    fun randomValue(maxValue: Int): Int {
        return when (Random.nextInt(0, 6)) {
            1 -> (ceil(maxValue * 0.7)).toInt()
            2 -> (ceil(maxValue * 0.9)).toInt()
            3, 4, 5 -> maxValue
            else -> 0
        }
    }

    val studentPoints: MutableMap<String, MutableMap<String, IntArray>> = mutableMapOf()

    // Add your code here

    fun generatePoints(): IntArray {
        return IntArray(LAB_WORK_NUMBER) { randomValue(MAX_POINT) }
    }

    studentsGroups.forEach { group ->
        val students: MutableMap<String, IntArray> = mutableMapOf()
        group.value.forEach { students[it] = generatePoints() }
        studentPoints[group.key] = students
    }


    println("\nTASK #2")
    for (group in studentPoints) {
        println(group.key)
        for (student in group.value) {
            println("\t${student.key}: ${student.value.contentToString()}")
        }
    }



    // Task 3
    // Create dictionary:
    // - key is a group name
    // - value is dictionary:
    //   - key is student
    //   - value is sum of student's points

    val sumPoints: MutableMap<String, MutableMap<String, Int>> = mutableMapOf()

    // Add your code here

    studentPoints.forEach { group ->
        val students: MutableMap<String, Int> = mutableMapOf()
        group.value.forEach { students[it.key] = it.value.sum() }
        sumPoints[group.key] = students
    }

    println("\nTASK #3")
    for (group in sumPoints) {
        println(group.key)
        for (student in group.value) {
            println("\t${student.key}: ${student.value}")
        }
    }


    // Task 4
    // Create dictionary:
    // - key is group name
    // - value is average of all students points

    val groupAvg: MutableMap<String, Float> = mutableMapOf()

    // Add your code here

    sumPoints.forEach { group ->
        val averageMark: Float = group.value.values.sum() / group.value.values.size.toFloat()
        groupAvg[group.key] = averageMark
    }

    println("\nTASK #4")
    println(groupAvg)

    // Task 5
    // Create dictionary:
    // - key is group name
    // - value is array of students that have >= 60 points

    val passedPerGroup: MutableMap<String, MutableList<String>> = mutableMapOf()

    sumPoints.forEach { group ->
        val passedStudents: MutableList<String> = mutableListOf()
        group.value.forEach { if (it.value >= PASSING_POINT) passedStudents.add(it.key) }
        passedPerGroup[group.key] = passedStudents
    }


    // Add your code here
    println("\nTASK #5")
    println(passedPerGroup)

    // Example of output. Your results will differ because random is used to fill points.
    //
    //["ІВ-72": ["Бортнік Василь", "Киба Олег", "Овчарова Юстіна", "Тимко Андрій"], "ІВ-73": ["Гончар Юрій", "Давиденко Костянтин", "Капінус Артем", "Науменко Павло", "Чередніченко Владислав"], "ІВ-71": ["Андрющенко Данило", "Гуменюк Олександр", "Корнійчук Ольга", "Музика Олександр", "Трудов Антон", "Феофанов Іван"]]
    //
    //["ІВ-73": ["Капінус Артем": [5, 8, 15, 14, 10, 9, 7, 10, 11], "Чередніченко Владислав": [5, 6, 15, 11, 13, 9, 0, 7, 15], "Давиденко Костянтин": [5, 8, 15, 0, 10, 0, 7, 0, 0], "Науменко Павло": [0, 8, 0, 15, 13, 10, 0, 0, 0], "Гончар Юрій": [5, 0, 11, 15, 10, 10, 10, 9, 15]], "ІВ-72": ["Киба Олег": [0, 8, 15, 11, 13, 10, 10, 9, 0], "Овчарова Юстіна": [4, 8, 15, 15, 13, 10, 7, 10, 15], "Тимко Андрій": [5, 6, 14, 15, 13, 10, 10, 10, 0], "Бортнік Василь": [5, 8, 0, 15, 13, 10, 10, 10, 0]], "ІВ-71": ["Музика Олександр": [4, 8, 15, 0, 12, 10, 10, 10, 15], "Трудов Антон": [5, 0, 0, 14, 12, 10, 0, 9, 0], "Феофанов Іван": [5, 8, 14, 11, 12, 0, 10, 10, 15], "Корнійчук Ольга": [0, 8, 11, 14, 12, 7, 7, 10, 14], "Гуменюк Олександр": [5, 8, 14, 15, 10, 0, 7, 10, 15], "Андрющенко Данило": [4, 8, 15, 15, 13, 0, 10, 7, 0]]]
    //
    //["ІВ-73": ["Чередніченко Владислав": 81, "Гончар Юрій": 85, "Давиденко Костянтин": 45, "Капінус Артем": 89, "Науменко Павло": 46], "ІВ-72": ["Овчарова Юстіна": 97, "Тимко Андрій": 83, "Бортнік Василь": 71, "Киба Олег": 76], "ІВ-71": ["Музика Олександр": 84, "Корнійчук Ольга": 83, "Феофанов Іван": 85, "Гуменюк Олександр": 84, "Андрющенко Данило": 72, "Трудов Антон": 50]]
    //
    //["ІВ-72": 81.75, "ІВ-73": 69.2, "ІВ-71": 76.333336]
    //
    //["ІВ-73": ["Чередніченко Владислав", "Гончар Юрій", "Капінус Артем"], "ІВ-71": ["Гуменюк Олександр", "Корнійчук Ольга", "Андрющенко Данило", "Феофанов Іван", "Музика Олександр"], "ІВ-72": ["Бортнік Василь", "Тимко Андрій", "Овчарова Юстіна", "Киба Олег"]]
}
