package ua.kpi.comsys.iv7213.tasks

import kotlin.math.absoluteValue

enum class Direction {
    LATITUDE, LONGITUDE
}

/**
 * Laboratory work #2
 *
 * Part 2
 *
 * Author: Andrew Krivonos
 * Group: IV-72
 *
 * 7213 mod 2 = 1 => option 2
 */
@ExperimentalUnsignedTypes
class CoordinateAK(val direction: Direction) {
    private var deg: Int = 0
        set(value) {
            if (direction === Direction.LATITUDE) {
                if (-90 <= value && value <= 90)
                    field = value
                else
                    throw IllegalArgumentException("For Latitude direction degrees value should be in range [-90; 90]")
            } else {
                if (-180 <= value && value <= 180)
                    field = value
                else
                    throw IllegalArgumentException("For Longitude direction degrees value should be in range [-180; 180]")
            }
        }
    private var min: UInt = 0u
        set(value) {
            field =
                if (value < 60u) value else throw IllegalArgumentException("Minutes value should be in range [0; 59]")
        }
    private var sec: UInt = 0u
        set(value) {
            field =
                if (value < 60u) value else throw IllegalArgumentException("Minutes value should be in range [0; 59]")
        }

    constructor(direction: Direction, deg: Int, min: UInt, sec: UInt) : this(direction) {
        if (direction === Direction.LATITUDE) {
            if (-90 <= deg && deg <= 90)
                this.deg = deg
            else
                throw IllegalArgumentException("For Latitude direction degrees value should be in range [-90; 90]")
        } else {
            if (-180 <= deg && deg <= 180)
                this.deg = deg
            else
                throw IllegalArgumentException("For Longitude direction degrees value should be in range [-180; 180]")
        }

        if (min > 59u)
            throw IllegalArgumentException("Minutes value should be in range [0; 59]")
        if (sec > 59u)
            throw IllegalArgumentException("Seconds value should be in range [0; 59]")

        this.min = min
        this.sec = sec
    }

    fun showInfo(): String {
        val direction: String = if (this.direction == Direction.LATITUDE) {
            if (this.deg >= 0) "N" else "S"
        } else {
            if (this.deg >= 0) "E" else "W"
        }

        return "${this.deg}°${this.min}′${this.sec}″ $direction"
    }

    fun showFormatInfo(): String {
        val sign = this.deg >= 0
        var formatDeg =
            this.deg.absoluteValue + (this.min.toFloat() / 60) + (this.sec.toFloat() / 3600)
        formatDeg = if (sign) formatDeg else -formatDeg

        val direction: String = if (this.direction == Direction.LATITUDE) {
            if (this.deg >= 0) "N" else "S"
        } else {
            if (this.deg >= 0) "E" else "W"
        }

        return "$formatDeg°″ $direction"

    }

    fun calcMidpoint(coords: CoordinateAK): CoordinateAK? {
        if (coords.direction != this.direction) {
            return null
        }

        return CoordinateAK(
            coords.direction,
            (coords.deg + this.deg) / 2,
            (coords.min + this.min).toUInt() / 2u,
            (coords.sec + this.sec).toUInt() / 2u
        )
    }
}

@ExperimentalUnsignedTypes
fun main() {
    val coord1 = CoordinateAK(Direction.LONGITUDE, 50, 20u, 30u)
    val coord2 = CoordinateAK(Direction.LONGITUDE, -20, 40u, 40u)
    val coord3 = CoordinateAK(Direction.LATITUDE, -20, 40u, 40u)

    println("Coordinate A (longitude) - method 1: ${coord1.showInfo()}")
    println("Coordinate A (longitude) - method 2: ${coord1.showFormatInfo()}")
    println("Coordinate B (longitude) - method 1: ${coord2.showInfo()}")
    println("Coordinate B (longitude) - method 2: ${coord2.showFormatInfo()}")
    println("Coordinate C (latitude) - method 1: ${coord3.showInfo()}")
    println("Coordinate C (latitude) - method 2: ${coord3.showFormatInfo()}")

    println("\nMidpoint AB: ${coord1.calcMidpoint(coord2)?.showInfo()}")
    println("Midpoint AC: ${coord1.calcMidpoint(coord3)?.showInfo()}")
}