package com.rohitthebest.multiselectinrecyclerview.util

import kotlin.random.Random

class Functions {

    companion object{


        /**
         * this function can convert an integer into  a string of a given base/radix
         * this function does not assure that you will find its logic in mathematics
         * it considers that the ordered digits are 0-9 then a-z then A-Z then !@#$%^&
         * @author Mohit kumar
         * @param radix
         */
        fun Long.toStringM(radix: Int = 0): String {

            val values = arrayOf(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "a",
                "b",
                "c",
                "d",
                "e",
                "f",
                "g",
                "h",
                "i",
                "j",
                "k",
                "l",
                "m",
                "n",
                "o",
                "p",
                "q",
                "r",
                "s",
                "t",
                "u",
                "v",
                "w",
                "x",
                "y",
                "z",
                "A",
                "B",
                "C",
                "D",
                "E",
                "F",
                "G",
                "H",
                "I",
                "J",
                "K",
                "L",
                "M",
                "N",
                "O",
                "P",
                "Q",
                "R",
                "S",
                "T",
                "U",
                "V",
                "W",
                "X",
                "Y",
                "Z",
                "!",
                "@",
                "#",
                "$",
                "%",
                "^",
                "&"
            )
            var str = ""
            var d = this
            var r: Int

            if (radix in 1..69) {

                if (d <= 0) {
                    return d.toString()
                }

                while (d != 0L) {

                    r = (d % radix).toInt()
                    d /= radix
                    str = values[r] + str
                }

                return str
            }

            return d.toString()
        }

        fun generateKey(appendString: String = ""): String {

            return "${System.currentTimeMillis().toStringM(69)}_${
                Random.nextLong(
                    100,
                    9223372036854775
                ).toStringM(69)
            }_$appendString"
        }

    }
}