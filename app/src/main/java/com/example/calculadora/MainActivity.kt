package com.example.calculadora

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadora.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val equalsButton = binding.equals

        binding.one.setOnClickListener {
            binding.visor.text = "${binding.visor.text}1"
        }

        binding.two.setOnClickListener {
            binding.visor.text = "${binding.visor.text}2"
        }

        binding.three.setOnClickListener {
            binding.visor.text = "${binding.visor.text}3"
        }

        binding.four.setOnClickListener {
            binding.visor.text = "${binding.visor.text}4"
        }

        binding.five.setOnClickListener {
            binding.visor.text = "${binding.visor.text}5"
        }

        binding.six.setOnClickListener {
            binding.visor.text = "${binding.visor.text}6"
        }

        binding.seven.setOnClickListener {
            binding.visor.text = "${binding.visor.text}7"
        }

        binding.eight.setOnClickListener {
            binding.visor.text = "${binding.visor.text}8"
        }

        binding.nine.setOnClickListener {
            binding.visor.text = "${binding.visor.text}9"
        }

        binding.zero.setOnClickListener {
            binding.visor.text = "${binding.visor.text}0"
        }

        binding.comma.setOnClickListener {
            binding.visor.text = "${binding.visor.text}."
        }

        binding.plus.setOnClickListener {
            binding.visor.text = "${binding.visor.text}+"
        }

        binding.minus.setOnClickListener {
            binding.visor.text = "${binding.visor.text}-"
        }

        binding.times.setOnClickListener {
            binding.visor.text = "${binding.visor.text}x"
        }

        binding.forwardSlash.setOnClickListener {
            binding.visor.text = "${binding.visor.text}/"
        }

        binding.percent.setOnClickListener {
            binding.visor.text = "${binding.visor.text}%"
        }

        binding.ce.setOnClickListener {
            binding.visor.text = ""
        }

        binding.backspace.setOnClickListener {
            val expression = binding.visor.text.toString()
            if (expression.isNotEmpty()) {
                binding.visor.text = expression.substring(0, expression.length - 1)
            }
        }

        binding.plusMinus.setOnClickListener {
            var t = binding.visor.text.toString().trim()
            if (t.isEmpty()) return@setOnClickListener

            if (t.startsWith("-(") && t.endsWith(")") && hasOuterParens(t.substring(1))) {
                t = t.substring(2, t.length - 1)
            }
            else if (t.startsWith("-") && !t.startsWith("-(")) {
                t = t.substring(1)
            }
            else {
                var body = t
                if (hasOuterParens(body)) {
                    body = body.substring(1, body.length - 1)
                }
                t = "-($body)"
            }

            binding.visor.text = t
        }

        equalsButton.setOnClickListener {
            val expression = binding.visor.text.toString()
            binding.visor.text = evaluateExpression(expression).toString()
        }

    }
}

private fun evaluateExpression(expression: String): Double {
    return try {
        var expr = expression
            .replace('×', '*')
            .replace('÷', '/')
            .replace(',', '.')
        expr = Regex("""(\d+(\.\d+)?)%""").replace(expr) { m ->
            "(${m.groupValues[1]}/100)"
        }

        expr = expr.replace("--", "+")

        val result = ExpressionBuilder(expr).build().evaluate()
        result
    } catch (_: Exception) {
        Double.NaN
    }
}

fun hasOuterParens(s: String): Boolean {
    if (s.length < 2 || s.first() != '(' || s.last() != ')') return false
    var depth = 0
    for (i in s.indices) {
        when (s[i]) {
            '(' -> depth++
            ')' -> depth--
        }
        if (depth == 0 && i < s.lastIndex) return false
    }
    return depth == 0
}
