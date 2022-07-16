package edu.nextstep.camp.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.nextstep.camp.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var resultText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button0.setOnClickListener { onClickNumber(0) }
        binding.button1.setOnClickListener { onClickNumber(1) }
        binding.button2.setOnClickListener { onClickNumber(2) }
        binding.button3.setOnClickListener { onClickNumber(3) }
        binding.button4.setOnClickListener { onClickNumber(4) }
        binding.button5.setOnClickListener { onClickNumber(5) }
        binding.button6.setOnClickListener { onClickNumber(6) }
        binding.button7.setOnClickListener { onClickNumber(7) }
        binding.button8.setOnClickListener { onClickNumber(8) }
        binding.button9.setOnClickListener { onClickNumber(9) }

        binding.buttonPlus.setOnClickListener { onClickOperator("+") }
        binding.buttonMinus.setOnClickListener { onClickOperator("-") }
        binding.buttonDivide.setOnClickListener { onClickOperator("/") }
        binding.buttonMultiply.setOnClickListener { onClickOperator("*") }
    }

    private fun onClickNumber(number: Int) {
        resultText += number
        binding.textView.text = resultText
    }

    private fun onClickOperator(operator: String) {
        resultText += " $operator"
        binding.textView.text = resultText
    }
}
