package net.nariman.codewithdeepseek

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.KeyStroke
import javax.swing.SwingUtilities
import kotlin.concurrent.thread

class ChatPanel : JPanel() {
    private val chatHistoryArea = JTextArea(15, 30).apply {
        isEditable = false
    }
    private val userInputArea = JTextArea(15, 30).apply {
        lineWrap = true
        wrapStyleWord = true

        inputMap.put(KeyStroke.getKeyStroke("shift ENTER"), "addNewLine")
        actionMap.put("addNewLine", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                this@apply.insert("\n", caretPosition)
            }
        })

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "sendMessage")
        actionMap.put("sendMessage", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                sendMessage()
            }
        })
    }
    private val sendButton = JButton("Send")
    private val deepseekModel = DeepseekModel()

    init {
        layout = BorderLayout()
        add(JScrollPane(chatHistoryArea), BorderLayout.CENTER)

        val bottomPanel = JPanel(BorderLayout()).apply {
            add(JScrollPane(userInputArea), BorderLayout.CENTER)
            add(sendButton, BorderLayout.SOUTH)
        }

        add(bottomPanel, BorderLayout.SOUTH)

        sendButton.addActionListener { sendMessage() }
    }

    private fun sendMessage() {
        val userMessage = userInputArea.text.trim()
        if (userMessage.isBlank()) {
            return
        }

        appendToChat("User: $userMessage")
        userInputArea.text = ""

        thread {
            val deepseekResponse = deepseekModel.chat(userMessage)
            SwingUtilities.invokeLater {
                appendToChat("Deepseek: $deepseekResponse")
            }
        }
    }

    private fun appendToChat(message: String) {
        chatHistoryArea.append("$message\n")
    }
}