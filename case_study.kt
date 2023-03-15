fun main(){  
    // Uncomment if you want to programmatically initialize the value
    // var patientCount = 10
    // var mainWaitingTime: Array<Double> = arrayOf(0.0, 0.0)
    // var docAverage: Array<Double> = arrayOf(3.0, 4.0)
    // var passedTime: Array<Double> = arrayOf(0.0, 2.0)
    // and comment bellow till //---- to disable the input functions

    var mainWaitingTime: Array<Double> = arrayOf()
    var docAverage: Array<Double> = arrayOf()
    var passedTime: Array<Double> = arrayOf()

    println()
    print("how many doctors available? ")
    val availableDoctors = readLine()
    if (availableDoctors != null){
        for(i in 1..((availableDoctors.toInt()))) {
        print("what is the avarage consultation time of doctor $i? ")
        val a = readLine()
        if (a != null){
            docAverage = docAverage.plus(a.toDouble())
        } else {
            docAverage = docAverage.plus(0.0)
        }
        mainWaitingTime = mainWaitingTime.plus(0.0)
    }

    for(i in 1..(availableDoctors.toInt())) {
        print("is there any patient currently meeting doctor $i? y/n ")
        val a = readLine()
        if (a == "y" || a == "yes"){
            print("how long the consultation has been going (in minutes)? ")
            val b = readLine()
            if (b != null){
                passedTime = passedTime.plus(b.toDouble())
            } else {
                passedTime = passedTime.plus(0.0)
            }
        } else {
            passedTime = passedTime.plus(0.0)
        }
    }

    print("how many patients ahead of you? ")
    var patientCount = readLine()
    if (patientCount == null){
        patientCount = "5"
    }

    //-----
    println()
    initiateWaitingtime(docAverage = docAverage, patientCount = patientCount.toInt(), mainWaitingTime = mainWaitingTime, passedTime = passedTime)
    println()
    }
}

fun initiateWaitingtime(docAverage: Array<Double>, patientCount: Int, mainWaitingTime: Array<Double>, passedTime: Array<Double>) {
    var eachPatientWaitingTime: Array<Double> = arrayOf()
    var newWaitingTime: Array<Double> = Array(mainWaitingTime.size) { i -> 
        mainWaitingTime[i] + (docAverage[i] - passedTime[i])
    }

    var patientsMeetingDoctor = 0
    var patientsDontHaveToWait = 0
    for (index in passedTime.indices){
        if(passedTime[index] != 0.0){
            patientsMeetingDoctor++
            println("patient ${patientsMeetingDoctor} is currently meeting the doctor")
        } else {
            patientsDontHaveToWait++
            eachPatientWaitingTime = eachPatientWaitingTime.plus(0.0)
        }
    }
 
    eachPatientWaitingTime = eachPatientWaitingTime.plus(newWaitingTime.min())
    
    for (i in 1..(patientCount-patientsMeetingDoctor)) {
        newWaitingTime = addNewQueue(newWaitingTime = newWaitingTime, docAverage = docAverage)
        eachPatientWaitingTime = eachPatientWaitingTime.plus(newWaitingTime.min())
    }
    
    var additionText = ""
    for(index in 0..(eachPatientWaitingTime.size-patientsDontHaveToWait-1)){
        var time = eachPatientWaitingTime[index]
        if (time < 0){
            time = 0.0
            additionText = "(i) might be longer than estimated time"
        }
        println("patient ${index+1+patientsMeetingDoctor} waiting time is $time minute(s) $additionText")
    }
}

fun addNewQueue(newWaitingTime: Array<Double>, docAverage: Array<Double>): Array<Double>{
    var index = newWaitingTime.withIndex().minBy { (_, f) -> f }.index
    newWaitingTime[index] += docAverage[index]
    return newWaitingTime
}