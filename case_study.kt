fun main(){  
    var patientCount = 10
    // var eachPatientWaitingTime: Array<Double> = arrayOf()
    var mainWaitingTime: Array<Double> = arrayOf(0.0, 0.0)
    var docAverage: Array<Double> = arrayOf(3.0, 4.0)
    var passedTime: Array<Double> = arrayOf(0.0, 2.0)

    initiateWaitingtime(docAverage = docAverage, patientCount = patientCount, mainWaitingTime = mainWaitingTime, passedTime = passedTime)
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
    
    for(index in 0..(eachPatientWaitingTime.size-patientsDontHaveToWait-1)){
        println("patient ${index+1+patientsMeetingDoctor} waiting time is ${eachPatientWaitingTime[index]} minute(s)")
    }
}

fun addNewQueue(newWaitingTime: Array<Double>, docAverage: Array<Double>): Array<Double>{
    var index = newWaitingTime.withIndex().minBy { (_, f) -> f }.index
    newWaitingTime[index] += docAverage[index]
    return newWaitingTime
}