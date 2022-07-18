package edu.nextstep.camp.calculator.domain

import java.util.*

class Calculator(private val delimiter: Char) {
    private val _expressionStack = Stack<String>()
    val expression: String
        get() = _expressionStack.joinToString(delimiter.toString())

    fun addOperand(number: String) {
        if (_expressionStack.isNotEmpty() && _expressionStack.last().toIntOrNull() != null) {
            _expressionStack.push(_expressionStack.pop() + number)
        } else {
            _expressionStack.push(number)
        }
    }

    fun addOperator(operator: String) {
        if (_expressionStack.isEmpty()) return
        if (_expressionStack.last().toIntOrNull() == null) {
            _expressionStack.pop()
        }
        _expressionStack.push(operator)
    }

    fun delete() {
        if (_expressionStack.isEmpty()) return
        val value = _expressionStack.pop()
        if (value.toIntOrNull() == null) return
        if (value.toInt() >= SMALLEST_OF_TWO_DIGITS) {
            _expressionStack.push(value.dropLast(1))
        }
    }


    fun evaluate(onError: (() -> Unit)? = null) {
        if (_expressionStack.isEmpty() || _expressionStack.last().toIntOrNull() == null) {
            onError?.invoke()
            return
        }

        val expression = _expressionStack.joinToString(delimiter.toString())
        require(expression.isNotBlank()) { IllegalArgumentException(IS_NULL_OR_BLANK) }

        val inputList = Splitter.splitByDelimiter(expression, delimiter)
        require(inputList.size % EVEN_COMPARISON_NUMBER == RESULT_WHEN_ODD_NUMBER) {
            IllegalArgumentException(NOT_MATCH_OPERATORS_AND_OPERANDS)
        }

        // 첫숫자는 바로 계산하기 위해 저장한다.
        var output = Operand.of(inputList.first())
        for (index in NUMBER_OF_EXCLUDING_THE_FIRST_INDEX until inputList.size step SIZE_OF_CALCULATION_UNIT) {
            output =
                Operator.of(inputList[index])
                    .calculate(output, Operand.of(inputList[index + INDEX_OF_NUMBER]))
        }

        _expressionStack.clear()
        _expressionStack.push(output.value.toString())
    }


    companion object {
        private const val EVEN_COMPARISON_NUMBER = 2
        private const val RESULT_WHEN_ODD_NUMBER = 1

        private const val NUMBER_OF_EXCLUDING_THE_FIRST_INDEX = 1
        private const val SIZE_OF_CALCULATION_UNIT = 2

        private const val SMALLEST_OF_TWO_DIGITS = 10

        private const val INDEX_OF_NUMBER = 1

        private const val IS_NULL_OR_BLANK = "인풋이 null이거나 blank입니다."
        private const val NOT_MATCH_OPERATORS_AND_OPERANDS = "연산자와 피연산자 갯수가 맞지 않습니다."
    }
}